import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {EmployerService} from '../../services/employer.service';
import {ProfileDto} from '../../dtos/profile-dto';
import {EditEmployer} from '../../dtos/edit-employer';
import {UpdateHeaderService} from '../../services/update-header.service';
import {AlertService} from '../../alert';

@Component({
  selector: 'app-edit-employer',
  templateUrl: './edit-employer.component.html',
  styleUrls: ['./edit-employer.component.scss']
})
export class EditEmployerComponent implements OnInit {
  editForm: FormGroup;
  submitted: boolean;
  employer: EditEmployer;
  selectedPicture = null;
  picture;
  hasPicture = false;
  @ViewChild('pictureUpload') // needed for resetting fileUpload button
  inputImage: ElementRef; // needed for resetting fileUpload button

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
    this.loadEmployerDetails();
  }

  /**
   * Get profile details to edit them
   */
  loadEmployerDetails() {
    this.employerService.getEmployerByEmail().subscribe(
      (employer: EditEmployer) => {
        this.employer = employer;
        this.editForm.controls['email'].setValue(employer.profileDto.email);
        this.editForm.controls['firstName'].setValue(employer.profileDto.firstName);
        this.editForm.controls['lastName'].setValue(employer.profileDto.lastName);
        this.editForm.controls['companyName'].setValue(employer.companyName);
        this.editForm.controls['companyDescription'].setValue(employer.description);
        this.editForm.controls['publicInfo'].setValue(employer.profileDto.publicInfo);
        // converts bytesArray to Base64
        this.arrayBufferToBase64(employer.profileDto.picture);
        if (employer.profileDto.picture != null) {
          this.picture = 'data:image/png;base64,' + this.picture;
          this.hasPicture = true;
        }
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
      const employer = this.createEmployer();
      if (this.selectedPicture != null && typeof this.selectedPicture !== 'object') {
        // image has valid format (png or jpg)
        if (this.selectedPicture.startsWith('data:image/png;base64') || this.selectedPicture.startsWith('data:image/jpeg;base64')) {
          this.selectedPicture = this.selectedPicture.split(',');
          employer.profileDto.picture = this.selectedPicture[1];
          this.hasPicture = true;
          // image has invalid format
        } else {
          this.hasPicture = false;
        }
      } else {
        if (this.picture != null) {
          const samePic = this.picture.split(',');
          employer.profileDto.picture = samePic[1];
        } else {
          this.hasPicture = false;
        }
      }

      this.employerService.updateEmployer(employer).subscribe(
        () => {
          console.log('User profile updated successfully');
          this.inputImage.nativeElement.value = ''; // resets fileUpload button
          this.loadEmployerDetails();
          this.updateHeaderService.updateProfile.next(true);
        });
    }
  }

  /**
   * Creates the employer from the inputs. Picture is set to null and set later depending on format and if it is valid
   */
  createEmployer(): EditEmployer {
    return new EditEmployer(
      this.employer.id,
      new ProfileDto(
        this.employer.id,
        this.editForm.controls.firstName.value,
        this.editForm.controls.lastName.value,
        this.editForm.controls.email.value,
        null,
        this.editForm.controls.publicInfo.value,
        null),
      this.editForm.controls.companyName.value,
      this.editForm.controls.companyDescription.value);
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
