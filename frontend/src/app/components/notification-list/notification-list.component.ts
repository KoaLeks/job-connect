import {Component, Input, OnInit} from '@angular/core';
import {SimpleNotification} from '../../dtos/simple-notification';
import {AuthService} from '../../services/auth.service';
import {ApplicationStatus} from '../../dtos/application-status';
import {ApplicationService} from '../../services/application.service';

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.scss']
})
export class NotificationListComponent implements OnInit {

  @Input() notifications: SimpleNotification[];

  constructor(private authService: AuthService, private applicationService: ApplicationService) { }

  ngOnInit(): void {
  }

  // TODO save task id in SimpleNotification dto
  accept(notification: SimpleNotification) {
    console.log(JSON.stringify(notification));
    this.applicationService.changeApplicationStatus(new ApplicationStatus(1, 41, true));
  }

  decline(notification: SimpleNotification) {
    console.log(JSON.stringify(notification));
    this.applicationService.changeApplicationStatus(new ApplicationStatus(1, 41, false));
  }
}
