import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {EmployerService} from '../../services/employer.service';
import {ProfileDto} from '../../dtos/profile-dto';
import {EditEmployer} from '../../dtos/edit-employer';
import {UpdateHeaderService} from '../../services/update-header.service';
import {Alert, AlertService} from '../../alert';

@Component({
  selector: 'app-edit-employer',
  templateUrl: './edit-employer.component.html',
  styleUrls: ['./edit-employer.component.scss']
})
export class EditEmployerComponent implements OnInit {
  editForm: FormGroup;
  submitted: boolean;
  profile: any;
  selectedPicture = null;
  picture;
  hasPicture = false;
  @ViewChild('pictureUpload') // needed for resetting fileUpload button
  inputImage: ElementRef; // needed for resetting fileUpload button
  changePassword: boolean = false;

  constructor(public authService: AuthService, private router: Router, private formBuilder: FormBuilder,
              private employerService: EmployerService, private updateHeaderService: UpdateHeaderService,
              private alertService: AlertService) {
    this.editForm = this.formBuilder.group({
      email: ['', [Validators.required]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      companyName: ['', [Validators.required]],
      companyDescription: [''],
      publicInfo: [''],
      picture: null
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
        this.editForm.controls['firstName'].setValue(profile.profileDto.firstName);
        this.editForm.controls['lastName'].setValue(profile.profileDto.lastName);
        this.editForm.controls['companyName'].setValue(profile.companyName);
        this.editForm.controls['companyDescription'].setValue(profile.description);
        this.editForm.controls['publicInfo'].setValue(profile.profileDto.publicInfo);
        // converts bytesArray to Base64
        this.arrayBufferToBase64(profile.profileDto.picture);
        if (profile.profileDto.picture != null) {
          this.picture = 'data:image/png;base64,' + this.picture;
          this.hasPicture = true;
        }
        // console.log(profile);
      }
    );
  }

  /**
   * Check if the form is valid and call the service to update the employer
   */
  update() {
    this.alertService.clear();
    this.submitted = true;
    if (this.editForm.valid) {
      let employer;
      if (this.selectedPicture != null && typeof this.selectedPicture !== 'object') {
        // image has valid format (png or jpg)
        if (this.selectedPicture.startsWith('data:image/png;base64') || this.selectedPicture.startsWith('data:image/jpeg;base64')) {
          this.selectedPicture = this.selectedPicture.split(',');

          employer = new EditEmployer(new ProfileDto(null, this.editForm.controls.firstName.value, this.editForm.controls.lastName.value,
            this.editForm.controls.email.value, null, this.editForm.controls.publicInfo.value,
            this.selectedPicture[1]),
            this.editForm.controls.companyName.value, this.editForm.controls.companyDescription.value);
          this.hasPicture = true;
// image has invalid format
        } else {
          employer = new EditEmployer(new ProfileDto(null, this.editForm.controls.firstName.value, this.editForm.controls.lastName.value,
            this.editForm.controls.email.value, null, this.editForm.controls.publicInfo.value,
            null),
            this.editForm.controls.companyName.value, this.editForm.controls.companyDescription.value);
          this.hasPicture = false;
        }
      } else {
        if (this.picture != null) {
          const samePic = this.picture.split(',');
          employer = new EditEmployer(new ProfileDto(null, this.editForm.controls.firstName.value, this.editForm.controls.lastName.value,
            this.editForm.controls.email.value, null, this.editForm.controls.publicInfo.value,
            samePic[1]),
            this.editForm.controls.companyName.value, this.editForm.controls.companyDescription.value);
        } else {
          employer = new EditEmployer(new ProfileDto(null, this.editForm.controls.firstName.value, this.editForm.controls.lastName.value,
            this.editForm.controls.email.value, null, this.editForm.controls.publicInfo.value,
            null),
            this.editForm.controls.companyName.value, this.editForm.controls.companyDescription.value);
          this.hasPicture = false;
        }
      }

      this.employerService.updateEmployer(employer).subscribe(
        () => {
          // console.log('User profile updated successfully');
          // this.router.navigate(['/']);
          this.inputImage.nativeElement.value = ''; // resets fileUpload button
          this.loadEmployerDetails();
          this.updateHeaderService.updateProfile.next(true);
        });
    }
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
    this.selectedPicture = null;
    this.hasPicture = false;
    this.picture = null;
  }


}
