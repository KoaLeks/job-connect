import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../../services/auth.service';
import {AuthRequest} from '../../dtos/auth-request';
import {UpdateHeaderService} from '../../services/update-header.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  @Input() isEmployee: boolean;
  loginForm: FormGroup;
  // After first submission attempt, form validation will start
  submitted: boolean = false;
  // Error flag
  error: boolean = false;
  errorMessage: string = '';

    constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router,
                private updateHeaderService: UpdateHeaderService) {
        this.loginForm = this.formBuilder.group({
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, Validators.minLength(8)]]
        });
    }

  /**
   * Clears Form
   */
  clearForm(): void {
    this.submitted = false;
    this.loginForm.reset();
    this.vanishError();
  }

  /**
   * Form validation will start after the method is called, additionally an AuthRequest will be sent
   */
  loginUser() {
    this.submitted = true;
    // removes previous errors
    this.vanishError();
    if (this.loginForm.valid) {
      const authRequest: AuthRequest = new AuthRequest(this.loginForm.controls.email.value, this.loginForm.controls.password.value);
      this.authenticateUser(authRequest);
    } else {
      console.log('Invalid input');
    }
  }

  /**
   * Send authentication data to the authService. If the authentication was successfully, the user will be forwarded to the message page
   * @param authRequest authentication data from the user login form
   */
  authenticateUser(authRequest: AuthRequest) {
    console.log('Try to authenticate user: ' + authRequest.email);
    this.authService.loginUser(authRequest).subscribe(
      () => {
        console.log('Successfully logged in user: ' + authRequest.email);
        // @ts-ignore
        $('#loginModal').modal('hide');
        this.updateHeaderService.updateProfile.next(true);
        this.updateHeaderService.emitNewLoggedInUser(true);
        this.vanishError();
        this.router.navigate(['/']);
        this.clearForm();
      },
      error => {
        console.log('Could not log in due to:');
        console.log(error);
        this.error = true;
        if (error.status === 401) {
          this.errorMessage = 'E-Mail und Passwort stimmen nicht überein';
        } else {
          if (typeof error.error === 'object') {
            this.errorMessage = error.error.error;
          } else {
            this.errorMessage = error.error;
          }
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

  ngOnInit() {
  }

}
