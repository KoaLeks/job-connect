import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {EmployeeService} from '../../services/employee.service';
import {EditEmployee} from '../../dtos/edit-employee';
import {Gender} from '../../dtos/gender.enum';
import {InterestService} from '../../services/interest.service';
import {Interest} from '../../dtos/interest';
import {ProfileDto} from '../../dtos/profile-dto';
import {UpdateHeaderService} from '../../services/update-header.service';
import {TimeDto} from '../../dtos/TimeDto';
import {InterestArea} from '../../dtos/interestArea';
import {InterestAreaService} from '../../services/interestArea.service';
import {AlertService} from '../../alert';

@Component({
  selector: 'app-edit-employee',
  templateUrl: './edit-employee.component.html',
  styleUrls: ['./edit-employee.component.scss']
})
export class EditEmployeeComponent implements OnInit {
  editForm: FormGroup;
  submitted: boolean;
  profile: any;
  employee: EditEmployee;
  genderOptions = Object.values(Gender);
  pattern = '[a-zA-ZÖöÜüÄä]+([ ]|[a-zA-ZÖöÜüÄä])*';
  selectedPicture = null;
  picture;
  hasPicture = false;
  @ViewChild('pictureUpload') // needed for resetting fileUpload button
  inputImage: ElementRef; // needed for resetting fileUpload button
  timeCreationForm;
  times: TimeDto[] = []; // for database entries
  newTimes: TimeDto[] = []; // for newly added entries
  toggleStartEnd: boolean = false;
  nightShift: boolean = false;
  toggleStartEndNightShift: boolean = false;
  mondayArray: TimeDto[] = [];
  tuesdayArray: TimeDto[] = [];
  wednesdayArray: TimeDto[] = [];
  thursdayArray: TimeDto[] = [];
  fridayArray: TimeDto[] = [];
  saturdayArray: TimeDto[] = [];
  sundayArray: TimeDto[] = [];
  newTimes1: TimeDto[] = [];
  ref_id: number = 0;
  interestForm;
  interestAreas: InterestArea[];
  employeeInterests: Interest[] = [];

  constructor(public authService: AuthService, private router: Router, private formBuilder: FormBuilder,
              private employeeService: EmployeeService, private interestService: InterestService,
              private updateHeaderService: UpdateHeaderService, private interestAreaService: InterestAreaService,
              private alertService: AlertService) {
    this.editForm = this.formBuilder.group({
      email: ['', [Validators.required]],
      firstName: ['', [Validators.required, Validators.pattern(this.pattern)]],
      lastName: ['', [Validators.required, Validators.pattern(this.pattern)]],
      publicInfo: [''],
      gender: ['', [Validators.required]],
      picture: null,
      birthDate: [null, [Validators.required]]
    }, {validators: [this.isAdult('birthDate')]});
    this.interestForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      interestArea: ['']
    });
    this.timeCreationForm = this.formBuilder.group({
      date: [null, Validators.required],
      timeStart: [null, Validators.required],
      timeEnd: [null, Validators.required],
      booleanDate: false
    });

  }

  isAdult(controlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      if (control.errors && !control.errors.notAdult) {
        // return if another validator has already found an error on the matchingControl
        return;
      }

      const birthDate = new Date(control.value);
      const currentDate = new Date();
      let age = currentDate.getFullYear() - birthDate.getFullYear();

      if (currentDate.getMonth() < birthDate.getMonth()) {
        age--;
      }
      if (birthDate.getMonth() === currentDate.getMonth() && currentDate.getDate() < birthDate.getDate()) {
        age--;
      }
      if (age < 18) {
        control.setErrors({notAdult: true});
      } else {
        control.setErrors(null);
      }
    };
  }

  ngOnInit(): void {
    this.loadEmployeeDetails(); // loads the employee
    this.interestForm.reset();
  }

  /**
   * Get profile details to edit them
   */
  loadEmployeeDetails() {
    this.employeeService.getEmployeeByEmail().subscribe(
      (employee: EditEmployee) => {
        this.employee = employee;
        this.editForm.controls['email'].setValue(employee.profileDto.email);
        this.editForm.controls['firstName'].setValue(employee.profileDto.firstName);
        this.editForm.controls['lastName'].setValue(employee.profileDto.lastName);
        this.editForm.controls['publicInfo'].setValue(employee.profileDto.publicInfo);
        this.editForm.controls['gender'].setValue(employee.gender);
        this.editForm.controls['birthDate'].setValue(employee.birthDate.toString().substr(0, 10));
        this.times = employee.times;
        this.filterShowTime();
        // converts bytesArray to Base64
        this.arrayBufferToBase64(employee.profileDto.picture);
        if (employee.profileDto.picture != null) {
          this.picture = 'data:image/png;base64,' + this.picture;
          this.hasPicture = true;
        }

        if (this.employee.interestDtos !== undefined && this.employee.interestDtos.length > 0) {
          this.employeeInterests = this.employee.interestDtos;
        }
      }
    );
  }

  /**
   * Check if the form is valid and call the service to update the employee
   */
  update() {
    this.alertService.clear();
    this.submitted = true;
    if (this.editForm.valid) {
      this.employee = this.createEmployee();
      for (const time of this.times) {
        this.newTimes.push(time);
      }
      if (this.selectedPicture != null && typeof this.selectedPicture !== 'object') {
        // image has valid format (png or jpg)
        if (this.selectedPicture.startsWith('data:image/png;base64') || this.selectedPicture.startsWith('data:image/jpeg;base64')) {
          this.selectedPicture = this.selectedPicture.split(',');
          this.employee.profileDto.picture = this.selectedPicture[1];
          this.hasPicture = true;
          // image has invalid format
        } else {
          this.hasPicture = false;
        }
      } else {
        if (this.picture != null) {
          const samePic = this.picture.split(',');
          this.employee.profileDto.picture = samePic[1];
        } else {
          this.hasPicture = false;
        }
      }

      this.newTimes1 = [];
      this.employee.interestDtos = this.employeeInterests;
      this.employeeService.updateEmployee(this.employee).subscribe(
        (id) => {
          this.newTimes = [];
          console.log('User profile updated successfully id: ' + id);
          // this.router.navigate(['/']);
          this.inputImage.nativeElement.value = ''; // resets fileUpload button
          this.loadEmployeeDetails();
          this.updateHeaderService.updateProfile.next(true);
        },
        error => {
          this.newTimes = [];
          this.newTimes1 = [];
        });
    } else {
      console.log('Invalid input');
    }
  }

  /**
   * Creates the employee from the inputs. Picture is set to null and set later depending on format and if it is valid
   */
  createEmployee(): EditEmployee {
    return new EditEmployee(
      this.employee.id,
      new ProfileDto(
        this.employee.id,
        this.editForm.controls.firstName.value,
        this.editForm.controls.lastName.value,
        this.editForm.controls.email.value,
        null,
        this.editForm.controls.publicInfo.value,
        null),
      this.employee.interestDtos,
      this.editForm.controls.gender.value,
      new Date(this.editForm.controls.birthDate.value),
      this.newTimes);
  }

  onFileSelected(event) {
    console.log(event);
    // checks if files size is smaller than 5MB
    if (event.target.files[0].size <= 5242880) {
      const file = event.target.files[0];
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.selectedPicture = reader.result.toString();
        if (this.selectedPicture.startsWith('data:image/png;base64') || this.selectedPicture.startsWith('data:image/jpeg;base64')) {
          this.picture = this.selectedPicture;
          this.hasPicture = true;
        } else {
          this.selectedPicture = null;
          this.alertService.warn('Das Bild muss im JPEG oder PNG Format sein.', {autoClose: true});
        }
      };
    } else {
      this.alertService.warn('Das Bild darf maximal 5 MB groß sein.', {autoClose: true});
    }
  }

  arrayBufferToBase64(buffer) {
    let binary = '';
    const bytes = new Uint8Array(buffer);
    const len = bytes.byteLength;
    for (let i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    this.picture = window.btoa(binary);
  }

  deletePicture() {
    this.hasPicture = false;
    this.selectedPicture = null;
    this.picture = null;
  }

  loadInterestAreas() {
    if (this.interestAreas === undefined || this.interestAreas.length === 0) {
      this.interestAreaService.getInterestAreas().subscribe(
        (interestAreas) => {
          this.interestAreas = interestAreas;
        }
      );
    }
  }

  addInterest(interest: Interest) {
    interest.simpleInterestAreaDto = {
      id: this.interestForm.value.interestArea,
      area: null,
      description: null,
    };
    this.employeeInterests.push(interest);
    this.interestForm.reset();
    console.log('interests: ' + JSON.stringify(this.employeeInterests));
  }

  deleteInterest(interest: Interest) {
    const index = this.employeeInterests.indexOf(interest);
    if (index !== -1) {
      this.employeeInterests.splice(index, 1);
    }
  }

  addTime(time) {
    // build Time with date, start, end
    const date: string = time.date;
    const start: string = time.timeStart;
    const end: string = time.timeEnd;
    let timeStartBuild: string;
    let timeEndBuild: string;
    let newEndDate;
    if (this.nightShift) {
      newEndDate = new Date(date);
      newEndDate.setDate(newEndDate.getDate() + 1);
      let newEndDateString: string;
      newEndDateString = newEndDate.getFullYear() + '-' +
        ('0' + (newEndDate.getMonth() + 1)).slice(-2) + '-'
        + ('0' + newEndDate.getDate()).slice(-2);
      timeEndBuild = newEndDateString + 'T' + end;
    } else {
      timeEndBuild = date + 'T' + end;
    }
    // format: 2021-09-10T00:00:00
    timeStartBuild = date + 'T' + start;
    if (time.booleanDate) {
      // build a new timeEndBuild with plus 119 days
      const newFinalEndDate = new Date(timeEndBuild);
      // + 119 days calculates the final End Date for this weekly repeated time for one semester.
      newFinalEndDate.setDate(newFinalEndDate.getDate() + 119);
      const newFinalEndDateString = newFinalEndDate.getFullYear() + '-' +
        ('0' + (newFinalEndDate.getMonth() + 1)).slice(-2) + '-'
        + ('0' + newFinalEndDate.getDate()).slice(-2);
      const finalTimeEndBuild = newFinalEndDateString + 'T' + end;
      const timeDtoToSave: TimeDto =
        new TimeDto(null, timeStartBuild, timeEndBuild, time.booleanDate, true, finalTimeEndBuild, this.ref_id);
      this.newTimes.push(timeDtoToSave);
      this.newTimes1.push(timeDtoToSave);
    } else {
      const timeDtoToSave: TimeDto = new TimeDto(null, timeStartBuild, timeEndBuild, time.booleanDate, true, timeEndBuild, -1);
      this.newTimes.push(timeDtoToSave);
      this.newTimes1.push(timeDtoToSave);
    }
    if (time.booleanDate) {
      if (!this.nightShift) {
        const newDate = new Date(date);
        for (let i = 0; i < 17; i++) { // saves this day each week for one semester in database
          newDate.setDate(newDate.getDate() + 7);
          let newDateString: string;
          newDateString = newDate.getFullYear() + '-' +
            ('0' + (newDate.getMonth() + 1)).slice(-2) + '-'
            + ('0' + newDate.getDate()).slice(-2);
          const newTimeStartBuild: string = newDateString + 'T' + start;
          const newTimeEndBuild: string = newDateString + 'T' + end;
          const repeatedTimeDto: TimeDto =
            new TimeDto(null, newTimeStartBuild, newTimeEndBuild, false, false, newTimeEndBuild, this.ref_id);
          this.newTimes.push(repeatedTimeDto);
          this.newTimes1.push(repeatedTimeDto);
        }
      } else {
        const newStartDate = new Date(date);
        for (let i = 0; i < 17; i++) { // saves this day each week for one semester in database
          newStartDate.setDate(newStartDate.getDate() + 7);
          let newStartDateString: string;
          newStartDateString = newStartDate.getFullYear() + '-' +
            ('0' + (newStartDate.getMonth() + 1)).slice(-2) + '-'
            + ('0' + newStartDate.getDate()).slice(-2);
          newEndDate.setDate(newEndDate.getDate() + 7);
          let newEndDateString: string;
          newEndDateString = newEndDate.getFullYear() + '-' +
            ('0' + (newEndDate.getMonth() + 1)).slice(-2) + '-'
            + ('0' + newEndDate.getDate()).slice(-2);
          const newTimeStartBuild: string = newStartDateString + 'T' + start;
          const newTimeEndBuild: string = newEndDateString + 'T' + end;
          const repeatedTimeDto: TimeDto =
            new TimeDto(null, newTimeStartBuild, newTimeEndBuild, false, false, newTimeEndBuild, this.ref_id);
          this.newTimes.push(repeatedTimeDto);
          this.newTimes1.push(repeatedTimeDto);
        }
      }
      this.ref_id++;
    }
    this.timeCreationForm.reset();
    const checkbox = document.getElementById('fullDayCheck') as HTMLInputElement;
    checkbox.checked = false;
    const checkbox1 = document.getElementById('nightShift') as HTMLInputElement;
    checkbox1.checked = false;
    this.toggleStartEnd = false;
    this.nightShift = false;
    this.toggleStartEndNightShift = false;
  }

  deleteTimeFromOverview(time, timeArray) {
    const index = timeArray.indexOf(time);
    if (index !== -1) {
      timeArray.splice(index, 1);
      const index1 = this.times.indexOf(time);
      if (index !== -1) {
        this.times.splice(index1, 1);
      }
    }
  }

  deleteTime(time: TimeDto) {
    const index = this.newTimes.indexOf(time);
    const index1 = this.newTimes1.indexOf(time);
    if (index !== -1) {
      if (time.booleanDate) {
        this.newTimes.splice(index, 18);
        this.newTimes1.splice(index1, 18);
      } else {
        this.newTimes.splice(index, 1);
        this.newTimes1.splice(index1, 1);
      }
    }
  }

  toggleStartEndMethod() {
    this.toggleStartEnd = !this.toggleStartEnd;
    if (this.toggleStartEnd) {
      if (this.nightShift) {
        this.timeCreationForm.controls['timeStart'].setValue('00:00');
        this.timeCreationForm.controls['timeEnd'].setValue('03:00');
      } else {
        this.timeCreationForm.controls['timeStart'].setValue('00:00');
        this.timeCreationForm.controls['timeEnd'].setValue('23:59');
      }
    } else {
      this.timeCreationForm.controls['timeStart'].setValue('');
      if (this.nightShift) {
        this.timeCreationForm.controls['timeEnd'].setValue('03:00');
      } else {
        this.timeCreationForm.controls['timeEnd'].setValue('');
      }
    }
  }

  toggleNightShift() {
    this.nightShift = !this.nightShift;
    this.toggleStartEndNightShift = !this.toggleStartEndNightShift;
  }

  addNightShift() {
    if (this.nightShift) {
      this.timeCreationForm.controls['timeEnd'].setValue('03:00');
    } else {
      if (this.toggleStartEnd) {
        this.timeCreationForm.controls['timeEnd'].setValue('23:59');
      } else {
        this.timeCreationForm.controls['timeEnd'].setValue('');
      }
    }
  }

  filterShowTime() {

    this.mondayArray = [];
    this.tuesdayArray = [];
    this.wednesdayArray = [];
    this.thursdayArray = [];
    this.fridayArray = [];
    this.saturdayArray = [];
    this.sundayArray = [];

    for (const time of this.times) {
      // time is type of TimeDto
      const endDate = new Date(time.finalEndDate);
      const startDate = new Date(time.start);
      const now = new Date();
      if (endDate > now) {
        switch (startDate.getDay()) {
          case 0:
            this.sundayArray.push(time);
            break;
          case 1:
            this.mondayArray.push(time);
            break;
          case 2:
            this.tuesdayArray.push(time);
            break;
          case 3:
            this.wednesdayArray.push(time);
            break;
          case 4:
            this.thursdayArray.push(time);
            break;
          case 5:
            this.fridayArray.push(time);
            break;
          case 6:
            this.saturdayArray.push(time);
            break;
          default:
            break;
        }
      }
    }
  }
}
