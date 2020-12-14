import {Component, Input, OnInit} from '@angular/core';
import {SimpleNotification} from '../../dtos/simple-notification';
import {AuthService} from '../../services/auth.service';
import {ApplicationStatus} from '../../dtos/application-status';
import {ApplicationService} from '../../services/application.service';
import {NotificationService} from '../../services/notification.service';

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.scss']
})
export class NotificationListComponent implements OnInit {

  @Input() notifications: SimpleNotification[];

  constructor(private authService: AuthService, private applicationService: ApplicationService,
              private notificationService: NotificationService) { }

  ngOnInit(): void {
  }

  accept(notification: SimpleNotification) {
    const acceptApplication = new ApplicationStatus(notification.taskId, notification.sender.id, notification.id, true);
    this.applicationService.changeApplicationStatus(acceptApplication).subscribe();
    this.removeNotfication(notification.id);
  }

  decline(notification: SimpleNotification) {
    console.log(JSON.stringify(notification));
    const declineApplication = new ApplicationStatus(notification.taskId, notification.sender.id, notification.id, false);
    this.applicationService.changeApplicationStatus(declineApplication).subscribe();
    this.removeNotfication(notification.id);
  }

  deleteNotification(id: number) {
    this.notificationService.deleteNotification(id).subscribe();
    this.removeNotfication(id);
  }

  removeNotfication(id: number) {
    const index = this.notifications.findIndex(notification => notification.id === id);
    this.notifications.splice(index, 1);
  }

  public trackId(index: number, notification: any) {
    return notification.id;
  }

}
