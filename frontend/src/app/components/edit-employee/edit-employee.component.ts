import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {EmployeeService} from '../../services/employee.service';
import {EditEmployee} from '../../dtos/edit-employee';
import {ProfileDto} from '../../dtos/profile-dto';
import {Gender} from '../../dtos/gender.enum';

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

  constructor(private authService: AuthService, private router: Router, private formBuilder: FormBuilder,
              private employeeService: EmployeeService) {
    this.editForm = this.formBuilder.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.minLength(8)]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      publicInfo: [''],
      gender: ['', [Validators.required]],
      picture: null
    });
  }

  ngOnInit(): void {
    if (this.authService.getUserRole() !== 'EMPLOYEE') {
      this.router.navigate(['edit-profile']);
    }
    this.loadEmployeeDetails();
  }

  /**
   * Get profile details to edit them
   */
  loadEmployeeDetails() {
    this.employeeService.getEmployeeByEmail(this.authService.getTokenIdentifier()).subscribe(
      (profile: any) => {
        this.profile = profile;
        this.editForm.controls['email'].setValue(profile.profileDto.email);
        this.editForm.controls['firstName'].setValue(profile.profileDto.firstName);
        this.editForm.controls['lastName'].setValue(profile.profileDto.lastName);
        this.editForm.controls['publicInfo'].setValue(profile.profileDto.publicInfo);
        this.editForm.controls['gender'].setValue(profile.gender);

        // converts bytesArray to Base64
        this.arrayBufferToBase64(profile.profileDto.picture);
        if (profile.profileDto.picture != null) {
          this.picture = 'data:image/png;base64,' + this.picture;
          this.hasPicture = true;
        }
        console.log(profile);
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

          this.employee = new EditEmployee(new ProfileDto(null, this.editForm.controls.firstName.value,
            this.editForm.controls.lastName.value,
            this.editForm.controls.email.value, this.editForm.controls.password.value, this.editForm.controls.publicInfo.value,
            this.selectedPicture[1]), this.editForm.controls.gender.value);
          this.hasPicture = true;
          // image has invalid format
        } else {
          this.employee = new EditEmployee(new ProfileDto(null, this.editForm.controls.firstName.value,
            this.editForm.controls.lastName.value,
            this.editForm.controls.email.value, this.editForm.controls.password.value, this.editForm.controls.publicInfo.value,
            null), this.editForm.controls.gender.value);
          this.hasPicture = false;
        }
      } else {
        if (this.picture != null) {
          const samePic = this.picture.split(',');
          this.employee = new EditEmployee(new ProfileDto(null, this.editForm.controls.firstName.value,
            this.editForm.controls.lastName.value,
            this.editForm.controls.email.value, this.editForm.controls.password.value, this.editForm.controls.publicInfo.value,
            samePic[1]), this.editForm.controls.gender.value);
        } else {
          this.employee = new EditEmployee(new ProfileDto(null, this.editForm.controls.firstName.value,
            this.editForm.controls.lastName.value,
            this.editForm.controls.email.value, this.editForm.controls.password.value, this.editForm.controls.publicInfo.value,
            null), this.editForm.controls.gender.value);
          this.hasPicture = false;
        }
      }

      this.employeeService.updateEmployee(this.employee).subscribe(
        (id) => {
          console.log('User profile updated successfully id: ' + id);
          // this.router.navigate(['/']);
          this.inputImage.nativeElement.value = ''; // resets fileUpload button
          this.loadEmployeeDetails();
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
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = () => {
      this.selectedPicture = reader.result.toString();
    };
    reader.readAsDataURL(file);
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
}
