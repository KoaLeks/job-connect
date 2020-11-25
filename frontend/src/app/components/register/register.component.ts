import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {Employee} from '../../dtos/employee';
import {Employer} from '../../dtos/employer';
import {ProfileService} from '../../services/profile.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  @Input() isEmployee: boolean;
  registerForm: FormGroup;
  // After first submission attempt, form validation will start
  submitted: boolean = false;
  // Error flag
  error: boolean = false;
  errorMessage: string = '';


  // tslint:disable-next-line:max-line-length
  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router, private profileService: ProfileService) {
    this.registerForm = this.formBuilder.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      companyName: ['', [Validators.required]],
      companyDescription: ['']
    });
  }

  ngOnInit(): void {
  }

  /**
   * Form validation will start after the method is called, additionally a createProfile request will be sent
   */
  registerUser(): void {
    this.submitted = true;
    if (this.registerForm.valid && this.isEmployee) {
      const newEmployee: Employee = new Employee(this.registerForm.controls.firstName.value, this.registerForm.controls.lastName.value,
        this.registerForm.controls.email.value, this.registerForm.controls.password.value, '');
      this.createProfile(newEmployee);
    } else if (this.registerForm.valid && !this.isEmployee) {
      // tslint:disable-next-line:max-line-length
      const newEmployer: Employer = new Employer(this.registerForm.controls.companyName.value, this.registerForm.controls.companyDescription.value,
        this.registerForm.controls.firstName.value, this.registerForm.controls.lastName.value,
        this.registerForm.controls.email.value, this.registerForm.controls.password.value, '');
      this.createProfile(newEmployer);
    } else {
      console.log('Invalid input');
    }
  }

  /**
   * Send profile data to the profileService. If the authentication was successfully, the user will be forwarded to the home page
   * @param user profile data from the register form
   */
  createProfile(profile: any): void {
    console.log('Try to register profile: ' + profile.email);
    this.profileService.createProfile(profile).subscribe(
      () => {
        console.log('Successfully created profile: ' + profile.email);
        // @ts-ignore
        $('#registerModal').modal('hide');
        this.router.navigate(['']);
      },
        error => {
          console.log('Could not create user due to:');
          console.log(error);
          this.error = true;
          if (typeof error.error === 'object') {
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
