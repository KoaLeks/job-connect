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

@Component({
  selector: 'app-edit-employee',
  templateUrl: './edit-employee.component.html',
  styleUrls: ['./edit-employee.component.scss']
})
export class EditEmployeeComponent implements OnInit {
  error: boolean = false;
  errorMessage: string = '';
  editForm: FormGroup;
  submitted: boolean;
  profile: any;
  employee: EditEmployee;
  genderOptions = Object.values(Gender);
  selectedPicture = null;
  picture;
  hasPicture = false;
  @ViewChild('pictureUpload') // needed for resetting fileUpload button
  inputImage: ElementRef; // needed for resetting fileUpload button
  interests: Interest[];
  changePassword: boolean = false;
  timeCreationForm;
  times: TimeDto[] = [];
  toggleStartEnd: boolean = false;
  timeMap = new Map<string, TimeDto[]>();
  nightShift: boolean = false;

  constructor(private authService: AuthService, private router: Router, private formBuilder: FormBuilder,
              private employeeService: EmployeeService, private interestService: InterestService,
              private updateHeaderService: UpdateHeaderService) {
    this.editForm = this.formBuilder.group({
      email: ['', [Validators.required]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      publicInfo: [''],
      gender: ['', [Validators.required]],
      picture: null,
      birthDate: [null, [Validators.required]]
    }, {validators: [this.isAdult('birthDate')]});
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
    if (this.authService.getUserRole() !== 'EMPLOYEE') {
      this.router.navigate(['edit-profile']);
    }
    this.load(); // loads the employee after getting the list of interests
  }

  /**
   * Loads interests and then calls function to load the employee
   */
  load() {
    this.interestService.getInterests().subscribe(
      (interests: Interest[]) => {
        console.log(interests);
        this.interests = interests;
        this.loadEmployeeDetails();
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }

  /**
   * Get profile details to edit them
   */
  loadEmployeeDetails() {
    this.employeeService.getEmployeeByEmail(this.authService.getTokenIdentifier()).subscribe(
      (employee: EditEmployee) => {
        this.employee = employee;
        this.editForm.controls['email'].setValue(employee.profileDto.email);
        this.editForm.controls['firstName'].setValue(employee.profileDto.firstName);
        this.editForm.controls['lastName'].setValue(employee.profileDto.lastName);
        this.editForm.controls['publicInfo'].setValue(employee.profileDto.publicInfo);
        this.editForm.controls['gender'].setValue(employee.gender);
        this.editForm.controls['birthDate'].setValue(employee.birthDate.toString().substr(0, 10));
        this.filterShowTime(employee.times);
        // converts bytesArray to Base64
        this.arrayBufferToBase64(employee.profileDto.picture);
        if (employee.profileDto.picture != null) {
          this.picture = 'data:image/png;base64,' + this.picture;
          this.hasPicture = true;
        }
        console.log(this.employee);

        if (this.employee.interestDtos !== undefined && this.employee.interestDtos.length > 0) {
          for (let i = 0; i < this.employee.interestDtos.length; i++) {
            for (let j = 0; j < this.interests.length; j++) {
              if (this.employee.interestDtos[i].id === this.interests[j].id) {
                const checkbox = document.getElementById(this.interests[j].id.toString()) as HTMLInputElement;
                checkbox.checked = true;
                break;
              }
            }
          }
        }
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }

  /**
   * Check if the form is valid and call the service to update the employee
   */
  update() {
    this.submitted = true;
    if (this.editForm.valid) {
      if (this.selectedPicture != null && typeof this.selectedPicture !== 'object') {
        // image has valid format (png or jpg)
        if (this.selectedPicture.startsWith('data:image/png;base64') || this.selectedPicture.startsWith('data:image/jpeg;base64')) {
          this.selectedPicture = this.selectedPicture.split(',');

          this.employee = new EditEmployee(
            new ProfileDto(null, this.editForm.controls.firstName.value, this.editForm.controls.lastName.value,
              this.editForm.controls.email.value, null, this.editForm.controls.publicInfo.value,
              this.selectedPicture[1]), this.employee.interestDtos, this.editForm.controls.gender.value,
            new Date(this.editForm.controls.birthDate.value), this.times);
          this.hasPicture = true;
          // image has invalid format
        } else {
          this.employee = new EditEmployee(
            new ProfileDto(null, this.editForm.controls.firstName.value, this.editForm.controls.lastName.value,
              this.editForm.controls.email.value, null, this.editForm.controls.publicInfo.value,
              null), this.employee.interestDtos, this.editForm.controls.gender.value,
            new Date(this.editForm.controls.birthDate.value), this.times);
          this.hasPicture = false;
        }
      } else {
        if (this.picture != null) {
          const samePic = this.picture.split(',');
          this.employee = new EditEmployee(
            new ProfileDto(null, this.editForm.controls.firstName.value, this.editForm.controls.lastName.value,
              this.editForm.controls.email.value, null, this.editForm.controls.publicInfo.value,
              samePic[1]), this.employee.interestDtos, this.editForm.controls.gender.value,
            new Date(this.editForm.controls.birthDate.value), this.times);
        } else {
          this.employee = new EditEmployee(
            new ProfileDto(null, this.editForm.controls.firstName.value, this.editForm.controls.lastName.value,
              this.editForm.controls.email.value, null, this.editForm.controls.publicInfo.value,
              null), this.employee.interestDtos, this.editForm.controls.gender.value,
            new Date(this.editForm.controls.birthDate.value), this.times);
          this.hasPicture = false;
        }
      }

      console.log('employee before sending:');
      console.log(this.employee);

      this.employeeService.updateEmployee(this.employee).subscribe(
        (id) => {
          console.log('User profile updated successfully id: ' + id);
          // this.router.navigate(['/']);
          this.inputImage.nativeElement.value = ''; // resets fileUpload button
          this.load();
          this.updateHeaderService.updateProfile.next(true);
          this.times = [];
        },
        error => {
          this.error = true;
          this.errorMessage = error.error;
        });
    } else {
      console.log('Invalid input');
    }
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  onFileSelected(event) {
    console.log(event);
    // checks if files size is smaller than 5MB
    if (event.target.files[0].size <= 5242880) {
      const file = event.target.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.selectedPicture = reader.result.toString();
      };
      reader.readAsDataURL(file);
    } else {
      this.error = true;
      this.errorMessage = 'Das Bild darf maximal 5 MB groß sein.';
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
    this.picture = null;
  }

  toggleInterest(event, id: number) {
    if (event.target.checked) {
      // if the interest checkbox has been checked => add it to users interests
      for (const i of this.interests) {
        if (i.id === id) {
          this.employee.interestDtos.push(i);
          break;
        }
      }
    } else {
      // if the interest checkbox has been unchecked => remove it from users interests
      for (let i = 0; i < this.employee.interestDtos.length; i++) {
        if (this.employee.interestDtos[i].id === id) {
          this.employee.interestDtos.splice(i, 1);
          break;
        }
      }
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
      newEndDateString = newEndDate.getUTCFullYear() + '-' +
        ('0' + (newEndDate.getUTCMonth() + 1)).slice(-2) + '-'
        + ('0' + newEndDate.getUTCDate()).slice(-2);
      timeEndBuild = newEndDateString + 'T' + end;
    } else {
      timeEndBuild = date + 'T' + end;
    }
    // format: 2021-09-10T00:00:00
    timeStartBuild = date + 'T' + start;
    const timeDtoToSave: TimeDto = new TimeDto(null, timeStartBuild, timeEndBuild, time.booleanDate, true);
    this.times.push(timeDtoToSave);
    if (time.booleanDate) {
      if (!this.nightShift) {
        const newDate = new Date(date);
        for (let i = 0; i < 51; i++) { // saves this day each week for one year in database
          newDate.setDate(newDate.getDate() + 7);
          let newDateString: string;
          newDateString = newDate.getUTCFullYear() + '-' +
            ('0' + (newDate.getUTCMonth() + 1)).slice(-2) + '-'
            + ('0' + newDate.getUTCDate()).slice(-2);
          const newTimeStartBuild: string = newDateString + 'T' + start;
          const newTimeEndBuild: string = newDateString + 'T' + end;
          const repeatedTimeDto: TimeDto = new TimeDto(null, newTimeStartBuild, newTimeEndBuild, time.booleanDate, false);
          this.times.push(repeatedTimeDto);
        }
      } else {
        const newDate = new Date(newEndDate);
        for (let i = 0; i < 51; i++) { // saves this day each week for one year in database
          newDate.setDate(newDate.getDate() + 7);
          let newDateString: string;
          newDateString = newDate.getUTCFullYear() + '-' +
            ('0' + (newDate.getUTCMonth() + 1)).slice(-2) + '-'
            + ('0' + newDate.getUTCDate()).slice(-2);
          const newTimeStartBuild: string = newDateString + 'T' + start;
          const newTimeEndBuild: string = newDateString + 'T' + end;
          const repeatedTimeDto: TimeDto = new TimeDto(null, newTimeStartBuild, newTimeEndBuild, time.booleanDate, false);
          this.times.push(repeatedTimeDto);
        }
      }
    }
    this.timeCreationForm.reset();
    const checkbox = document.getElementById('fullDayCheck') as HTMLInputElement;
    checkbox.checked = false;
    this.toggleStartEnd = false;
    this.nightShift = false;
  }

  deleteTime(time: TimeDto) {
    const index = this.times.indexOf(time);
    if (index !== -1) {
      if (time.booleanDate) {
        this.times.splice(index, 52);
      } else {
        this.times.splice(index, 1);
      }
    }
  }

  toggleStartEndMethod() {
    this.toggleStartEnd = !this.toggleStartEnd;
    if (this.toggleStartEnd) {
      this.timeCreationForm.controls['timeStart'].setValue('00:00');
      this.timeCreationForm.controls['timeEnd'].setValue('23:59');
    } else {
      this.timeCreationForm.controls['timeStart'].setValue('');
      this.timeCreationForm.controls['timeEnd'].setValue('');
    }
  }

  toggleNightShift() {
    this.nightShift = !this.nightShift;
  }

  addNightShift() {
    if (this.nightShift) {
      this.timeCreationForm.controls['timeEnd'].setValue('03:00');
    } else {
      this.timeCreationForm.controls['timeEnd'].setValue('');
    }
  }

  filterShowTime(showTime) {

    const mondayArray: TimeDto[] = [];
    const tuesdayArray: TimeDto[] = [];
    const wednesdayArray: TimeDto[] = [];
    const thursdayArray: TimeDto[] = [];
    const fridayArray: TimeDto[] = [];
    const saturdayArray: TimeDto[] = [];
    const sundayArray: TimeDto[] = [];

    this.timeMap.set('monday', mondayArray);
    this.timeMap.set('tuesday', tuesdayArray);
    this.timeMap.set('wednesday', wednesdayArray);
    this.timeMap.set('thursday', thursdayArray);
    this.timeMap.set('friday', fridayArray);
    this.timeMap.set('saturday', saturdayArray);
    this.timeMap.set('sunday', sundayArray);

    for (const time of showTime) {
      // time is type of TimeDto
      const endDate = new Date(time.end);
      const startDate = new Date(time.start);
      const now = new Date();
      if (endDate > now) {
        switch (startDate.getDay()) {
          case 0:
            sundayArray.push(time);
            break;
          case 1:
            mondayArray.push(time);
            break;
          case 2:
            tuesdayArray.push(time);
            break;
          case 3:
            wednesdayArray.push(time);
            break;
          case 4:
            thursdayArray.push(time);
            break;
          case 5:
            fridayArray.push(time);
            break;
          case 6:
            saturdayArray.push(time);
            break;
          default:
            break;
        }
      }
    }
  }
}
