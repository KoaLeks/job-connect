import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {Employee} from '../../dtos/employee';
import {Employer} from '../../dtos/employer';
import {ProfileService} from '../../services/profile.service';
import {Gender} from '../../dtos/gender.enum';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  @Input() isEmployee: boolean;
  registerFormEmployee: FormGroup;
  registerFormEmployer: FormGroup;
  // After first submission attempt, form validation will start
  submitted: boolean = false;
  // Error flag
  error: boolean = false;
  errorMessage: string = '';
  genderOptions = Object.values(Gender);

  // tslint:disable-next-line:max-line-length
  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router, private profileService: ProfileService) {
    this.registerFormEmployer = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      companyName: ['', [Validators.required]],
      companyDescription: [''],
      publicInfo: ['']
    }, {validator: this.mustMatch('password', 'confirmPassword')});
    this.registerFormEmployee = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      publicInfo: [''],
      gender: [null, [Validators.required]],
      birthDate: [null, [Validators.required]]
    }, {validators: [this.mustMatch('password', 'confirmPassword'),
      this.isAdult('birthDate')]});
  }

  ngOnInit(): void {
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

  mustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];

      if (matchingControl.errors && !matchingControl.errors.notSame) {
        // return if another validator has already found an error on the matchingControl
        return;
      }

      // set error on matchingControl if validation fails
      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({notSame: true});
      } else {
        matchingControl.setErrors(null);
      }
    };
  }

  /**
   * Resets Form
   */
  clearForm(): void {
    this.submitted = false;
    this.registerFormEmployer.reset();
    this.registerFormEmployee.reset();
  }

  /**
   * Form validation will start after the method is called, additionally a createProfile request will be sent
   */
  registerUser(): void {
    this.submitted = true;
    if (this.registerFormEmployee.valid && this.isEmployee) {
      const newEmployee: Employee = new Employee(this.registerFormEmployee.controls.firstName.value,
        this.registerFormEmployee.controls.lastName.value, this.registerFormEmployee.controls.email.value,
        this.registerFormEmployee.controls.password.value, this.registerFormEmployee.controls.publicInfo.value,
        this.registerFormEmployee.controls.gender.value, new Date(this.registerFormEmployee.controls.birthDate.value));
      this.createProfile(newEmployee);
    } else if (this.registerFormEmployer.valid && !this.isEmployee) {
      // tslint:disable-next-line:max-line-length
      const newEmployer: Employer = new Employer(null, this.registerFormEmployer.controls.companyName.value,
        this.registerFormEmployer.controls.companyDescription.value, this.registerFormEmployer.controls.firstName.value,
        this.registerFormEmployer.controls.lastName.value, this.registerFormEmployer.controls.email.value,
        this.registerFormEmployer.controls.password.value, this.registerFormEmployer.controls.publicInfo.value);
      this.createProfile(newEmployer);
    } else {
      console.log('Invalid input');
    }
  }

  /**
   * Send profile data to the profileService. If the authentication was successfully, the user will be forwarded to the home page
   * @param profile data of the register form
   */
  createProfile(profile: any): void {
    console.log('Try to register profile: ' + profile.email);
    // console.log(JSON.stringify(profile));

    this.profileService.createProfile(profile).subscribe(
      () => {
        console.log('Successfully created profile: ' + profile.email);
        // @ts-ignore
        $('#registerModal').modal('hide');
        this.clearForm();
        this.router.navigate(['']);
      },
        error => {
          console.log('Could not create user due to:');
          console.log(error);
          this.error = true;
          if (error.error !== null && typeof error.error === 'object') {
            this.errorMessage = error.error.error;
          } else {
            this.errorMessage = error.error;
          }
        }
    );
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }
}
