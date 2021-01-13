import {Component, Input, OnInit} from '@angular/core';
import {SimpleNotification} from '../../dtos/simple-notification';
import {AuthService} from '../../services/auth.service';
import {ApplicationStatus} from '../../dtos/application-status';
import {ApplicationService} from '../../services/application.service';
import {NotificationService} from '../../services/notification.service';
import {UpdateHeaderService} from '../../services/update-header.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.scss']
})
export class NotificationListComponent implements OnInit {

  @Input() notifications: SimpleNotification[];

  constructor(private authService: AuthService, private applicationService: ApplicationService,
              private notificationService: NotificationService, private updateHeaderService: UpdateHeaderService,
              public router: Router) { }

  ngOnInit(): void {
  }

  deleteNotification(id: number) {
    this.notificationService.deleteNotification(id).subscribe();
    this.removeNotification(id);
  }

  removeNotification(id: number) {
    const index = this.notifications.findIndex(notification => notification.id === id);
    this.notifications.splice(index, 1);
  }

  updateSeenStatus(notification: SimpleNotification) {
    if (!notification.seen) {
      notification.seen = true;
      this.updateHeaderService.emitUpdatedNotification(notification);
    }
  }

  public trackId(index: number, notification: SimpleNotification) {
    return notification.id;
  }

  navigateToNewPage(eventId: number) {
    this.router.navigateByUrl('', { skipLocationChange: true }).then(() => {
      this.router.navigate(['/events', eventId, 'details']);
    });
  }
}
