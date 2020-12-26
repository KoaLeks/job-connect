import {Component, Input, OnInit} from '@angular/core';
import {SimpleNotification} from '../../dtos/simple-notification';
import {ApplicationService} from '../../services/application.service';
import {Task} from '../../dtos/task';
import {ApplicationStatus} from '../../dtos/application-status';
import {AuthService} from '../../services/auth.service';
import {NotificationService} from '../../services/notification.service';

@Component({
  selector: 'app-application-list',
  templateUrl: './application-list.component.html',
  styleUrls: ['./application-list.component.scss']
})
export class ApplicationListComponent implements OnInit {

  @Input() eventId: number;
  @Input() tasks: Task[];
  applications: SimpleNotification[];

  constructor(private authService: AuthService, private applicationService: ApplicationService,
              private notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.applicationService.getApplicationsForEvent(this.eventId).subscribe(
      (applications) => {
        this.applications = applications;
      }
    );
  }

  getTaskDescription(id: number) {
    return this.tasks.find(task => task.id === id).description;
  }

  accept(notification: SimpleNotification) {
    const acceptApplication = new ApplicationStatus(notification.taskId, notification.sender.id, notification.id, true);
    this.applicationService.changeApplicationStatus(acceptApplication).subscribe();
    this.removeNotification(notification.id);
  }

  decline(notification: SimpleNotification) {
    console.log(JSON.stringify(notification));
    const declineApplication = new ApplicationStatus(notification.taskId, notification.sender.id, notification.id, false);
    this.applicationService.changeApplicationStatus(declineApplication).subscribe();
    this.removeNotification(notification.id);
  }

  deleteNotification(id: number) {
    this.notificationService.deleteNotification(id).subscribe();
    this.removeNotification(id);
  }

  updateSeenStatus(notification: SimpleNotification) {
    if (!notification.seen) {
      notification.seen = true;
      this.notificationService.updateNotification(notification).subscribe();
    }
  }

  removeNotification(id: number) {
    const index = this.applications.findIndex(notification => notification.id === id);
    this.applications.splice(index, 1);
  }
}
