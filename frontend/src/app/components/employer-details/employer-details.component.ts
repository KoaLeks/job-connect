import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {SimpleEmployer} from '../../dtos/simple-employer';
import {EmployerService} from '../../services/employer.service';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-employer-details',
  templateUrl: './employer-details.component.html',
  styleUrls: ['./employer-details.component.scss']
})
export class EmployerDetailsComponent implements OnInit {
  id: number;
  employer: SimpleEmployer;
  hasPicture: boolean;
  picture;
  loggedInEmployer = false;
  loggedInEmployee = false;
  loggedIn = false;

  constructor(private route: ActivatedRoute, private employerService: EmployerService, public authService: AuthService) {
    this.route.params.subscribe(params => {
      this.id = params.id;
    });
  }

  ngOnInit(): void {
    if (this.authService.isLoggedIn() && this.authService.getUserRole() === 'EMPLOYER') {
      this.loggedInEmployer = true;
      this.loggedIn = true;
    }
    if (this.authService.isLoggedIn() && this.authService.getUserRole() === 'EMPLOYEE') {
      this.loggedInEmployee = true;
      this.loggedIn = true;
    }
    this.getEmployerDetails();
  }

  private getEmployerDetails() {
    this.employerService.getEmployerById(this.id).subscribe(
      (simpleEmployer: SimpleEmployer) => {
        this.employer = simpleEmployer;
        // profile picture
        this.arrayBufferToBase64(this.employer.simpleProfileDto.picture);
        if (this.employer.simpleProfileDto.picture != null) {
          this.picture = 'data:image/png;base64,' + this.picture;
          this.hasPicture = true;
        }
      }
    );
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
}
