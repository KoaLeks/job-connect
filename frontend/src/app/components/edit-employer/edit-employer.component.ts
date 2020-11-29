import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {EmployerService} from '../../services/employer.service';
import {EditEmployee} from '../../dtos/edit-employee';
import {ProfileDto} from '../../dtos/profile-dto';
import {EditEmployer} from '../../dtos/edit-employer';

@Component({
  selector: 'app-edit-employer',
  templateUrl: './edit-employer.component.html',
  styleUrls: ['./edit-employer.component.scss']
})
export class EditEmployerComponent implements OnInit {
  error: boolean = false;
  errorMessage: string = '';
  editForm: FormGroup;
  submitted: boolean;
  profile: any;

  constructor(private authService: AuthService, private router: Router, private formBuilder: FormBuilder,
              private employerService: EmployerService) {
    this.editForm = this.formBuilder.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.minLength(8)]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      companyName: ['', [Validators.required]],
      companyDescription: [''],
      publicInfo: ['']
    });
  }

  ngOnInit(): void {
    if (this.authService.getUserRole() !== 'EMPLOYER') {
      this.router.navigate(['edit-profile']);
    }
    this.loadEmployerDetails();
  }

  /**
   * Get profile details to edit them
   */
  loadEmployerDetails() {
    this.employerService.getEmployerByEmail(this.authService.getTokenIdentifier()).subscribe(
      (profile: any) => {
        this.profile = profile;
        this.editForm.controls['email'].setValue(profile.profileDto.email);
        // this.editForm.controls['password'].setValue(profile.profileDto.email);
        this.editForm.controls['firstName'].setValue(profile.profileDto.firstName);
        this.editForm.controls['lastName'].setValue(profile.profileDto.lastName);
        this.editForm.controls['companyName'].setValue(profile.companyName);
        this.editForm.controls['companyDescription'].setValue(profile.description);
        this.editForm.controls['publicInfo'].setValue(profile.profileDto.publicInfo);

        console.log(profile);
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }

  /**
   * Check if the form is valid and call the service to update the employer
   */
  update() {
    this.submitted = true;
    if (this.editForm.valid) {
      const employer = new EditEmployer(new ProfileDto(this.editForm.controls.firstName.value, this.editForm.controls.lastName.value,
        this.editForm.controls.email.value, this.editForm.controls.password.value, this.editForm.controls.publicInfo.value),
        this.editForm.controls.companyName.value, this.editForm.controls.companyDescription.value);
      this.employerService.updateEmployer(employer).subscribe(
        () => {
          console.log('User profile updated successfully');
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

}
