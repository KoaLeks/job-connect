import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {EmployerService} from '../../services/employer.service';
import {EmployeeService} from '../../services/employee.service';
import {UpdateHeaderService} from '../../services/update-header.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  isEmployee: boolean;
  picture: any;
  profile: any;
  hasPicture = false;
  error = false;
  errorMessage;

  constructor(public authService: AuthService, public employerService: EmployerService, public employeeService: EmployeeService,
              private updateHeaderService: UpdateHeaderService) {
    this.updateHeaderService.updateProfile.subscribe(value => {
      if (this.authService.isLoggedIn()) {
        this.loadPicture();
      }
    });
  }

  ngOnInit() {
    if (this.authService.isLoggedIn()) {
      this.loadPicture();
    }
  }

  private loadPicture() {
    if (this.authService.getUserRole() === 'EMPLOYER') {
      this.employerService.getEmployerByEmail(this.authService.getTokenIdentifier()).subscribe(
        (profile: any) => {
          this.profile = profile;
          // converts bytesArray to Base64
          this.arrayBufferToBase64(profile.profileDto.picture);
          if (profile.profileDto.picture != null) {
            this.picture = 'data:image/png;base64,' + this.picture;
            this.hasPicture = true;
          } else {
            this.hasPicture = false;
          }
          console.log(profile);
        },
        error => {
          this.error = true;
          this.errorMessage = error.error;
        }
      );
    } else if (this.authService.getUserRole() === 'EMPLOYEE') {
      this.employeeService.getEmployeeByEmail(this.authService.getTokenIdentifier()).subscribe(
        (profile: any) => {
          this.profile = profile;
          // converts bytesArray to Base64
          this.arrayBufferToBase64(profile.profileDto.picture);
          if (profile.profileDto.picture != null) {
            this.picture = 'data:image/png;base64,' + this.picture;
            this.hasPicture = true;
          } else {
            this.hasPicture = false;
          }
          console.log(profile);
        },
        error => {
          this.error = true;
          this.errorMessage = error.error;
        }
      );
    }
  }

  private arrayBufferToBase64(buffer) {
    let binary = '';
    const bytes = new Uint8Array(buffer);
    const len = bytes.byteLength;
    for (let i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    this.picture = window.btoa(binary);
  }

  private getNotifications() {

  }

}
