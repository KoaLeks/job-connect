import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {EmployerService} from '../../services/employer.service';
import {EmployeeService} from '../../services/employee.service';
import {UpdateHeaderService} from '../../services/update-header.service';
import {NotificationService} from '../../services/notification.service';
import {SimpleNotification} from '../../dtos/simple-notification';

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
  notifications: SimpleNotification[];
  count: number;

  constructor(public authService: AuthService, public employerService: EmployerService, public employeeService: EmployeeService,
              private updateHeaderService: UpdateHeaderService, private notificationService: NotificationService) {
    this.updateHeaderService.updateProfile.subscribe(value => {
      if (this.authService.isLoggedIn()) {
        this.loadPicture();
      }
    });
    this.updateHeaderService.updateSeenNotifications.subscribe(notification => {
      this.notifications.find(n => n.id === notification.id).seen = true;
      this.count = this.countNewNotifications(this.notifications);
      this.notificationService.updateNotification(notification).subscribe();
    });
    this.updateHeaderService.newLoggedInUser.subscribe(bool => {
      if (bool) {
        this.loadNotifications();
      }
    });
    this.updateHeaderService.removeEventNotifications.subscribe(() => {
      this.loadNotifications();
    });

  }

  ngOnInit() {
    if (this.authService.isLoggedIn()) {
      this.count = 0;
      this.loadPicture();
      this.loadNotifications();
    }
  }

  private loadNotifications() {
    this.notificationService.getNotifications().subscribe(
      (notifications: SimpleNotification[]) => {
        this.notifications = notifications;
        this.count = this.countNewNotifications(notifications);
      }
    );
  }

  private countNewNotifications(notifications: any[]) {
    let count = 0;
    notifications.forEach(
      notification => {
        if (!notification.seen) {
          count++;
        }
      });
    return count;
  }

  private loadPicture() {
    if (this.authService.userIsEmployer()) {
      this.employerService.getEmployerByEmail().subscribe(
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
        },
        error => {
          this.error = true;
          this.errorMessage = error.error;
        }
      );
    } else if (this.authService.userIsEmployee()) {
      this.employeeService.getEmployeeByEmail().subscribe(
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

}
