import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {EditPassword} from '../../dtos/edit-password';
import {ProfileService} from '../../services/profile.service';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-edit-password',
  templateUrl: './edit-password.component.html',
  styleUrls: ['./edit-password.component.scss']
})
export class EditPasswordComponent implements OnInit {

  @Input() changePassword: boolean;
  @ViewChild('close') close: ElementRef;
  error: boolean = false;
  errorMessage: string = '';

  passwordUpdateForm: FormGroup;
  submittedPasswordForm: boolean = false;

  constructor(private formBuilder: FormBuilder, private profileService: ProfileService, private authService: AuthService) {
    this.passwordUpdateForm = this.formBuilder.group({
      currentPassword: ['', [Validators.required, Validators.minLength(8)]],
      newPassword: ['', [Validators.required, Validators.minLength(8)]],
      newPasswordConfirm: ['', [Validators.required, Validators.minLength(8)]],
    }, {validators: this.mustMatch('newPassword', 'newPasswordConfirm')});
  }

  ngOnInit(): void {
  }

  updatePassword() {
    this.submittedPasswordForm = true;
    if (this.passwordUpdateForm.valid) {
      const editPassword: EditPassword = new EditPassword(this.authService.getTokenIdentifier(),
        this.passwordUpdateForm.controls.currentPassword.value, this.passwordUpdateForm.controls.newPassword.value);
      console.log(editPassword);
      this.profileService.updatePassword(editPassword).subscribe(
      (id: Number) => {
        console.log('password updated successfully for ' + id);
        // close the modal
          this.close.nativeElement.click();
        }, error => {
          this.error = true;
          if (error.error !== null && typeof error.error === 'object') {
            this.errorMessage = error.error.error;
          } else {
            this.errorMessage = error.error;
          }
        }
      );
    }
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  /**
   * Clears Form
   */
  clearForm(): void {
    this.submittedPasswordForm = false;
    this.passwordUpdateForm.reset();
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
}
