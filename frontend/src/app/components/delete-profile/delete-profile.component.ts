import {Component, OnInit} from '@angular/core';
import {EmployerService} from '../../services/employer.service';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {AlertService} from '../../alert';

@Component({
  selector: 'app-delete-profile',
  templateUrl: './delete-profile.component.html',
  styleUrls: ['./delete-profile.component.scss']
})
export class DeleteProfileComponent implements OnInit {

  constructor(private employerService: EmployerService, public authService: AuthService, private router: Router,
              private alertService: AlertService) {
  }

  ngOnInit(): void {
  }

  deleteProfile() {
    this.employerService.deleteEmployer().subscribe(
      () => {
        console.log('profile deleted');
        this.authService.logoutUser();
        this.router.navigate(['']);
        this.alertService.success('Profil erfolgreich gelöscht', {autoClose: true});
      }, error => {
        console.log('profile not deleted');
      }
    );
  }

}
