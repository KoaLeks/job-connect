import {Component, Input, OnInit} from '@angular/core';
import {SimpleNotification} from '../../dtos/simple-notification';
import {ApplicationService} from '../../services/application.service';
import {Task} from '../../dtos/task';
import {ApplicationStatus} from '../../dtos/application-status';
import {AuthService} from '../../services/auth.service';
import {NotificationService} from '../../services/notification.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-application-list',
  templateUrl: './application-list.component.html',
  styleUrls: ['./application-list.component.scss']
})
export class ApplicationListComponent implements OnInit {

  @Input() eventId: number;
  @Input() tasks: Task[];
  applications: SimpleNotification[] = []; // saves non-favorite applications
  favorites: SimpleNotification[] = []; // saves favorite applications
  hasApplications: boolean;
  hasApplicationsFav: boolean;

  constructor(private authService: AuthService, private applicationService: ApplicationService,
              private notificationService: NotificationService, public router: Router) {
  }

  ngOnInit(): void {
    this.applicationService.getApplicationsForEvent(this.eventId).subscribe(
      (applications) => {
        // this.applications = applications;
        console.log(applications);

        for (let i = 0; i < applications.length; i++) {
          if (applications[i].favorite === true) {
            this.favorites.push(applications[i]);
          } else {
            this.applications.push(applications[i]);
          }
        }

        // sort applications & favorites by task
        // this.applications.sort((a, b) => (a.taskId > b.taskId) ? 1 : -1);
        // this.favorites.sort((a, b) => (a.taskId > b.taskId) ? 1 : -1);
      }
    );
    this.tasks.sort(function(a, b) {
      if (a.description < b.description) { return -1; }
      if (a.description > b.description) { return 1; }
      return 0;
    });
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

  likeApplicant(notification: SimpleNotification) {
    console.log('fav: ' + notification.favorite);
    this.notificationService.changeFavorite(notification).subscribe(
      (n) => {
        notification.favorite = n.favorite;
        console.log('new fav: ' + notification.favorite);
        if (notification.favorite) {
          this.addToFavorites(notification);
        } else {
          this.removeFromFavorites(notification);
        }
      }
    );
  }

  private addToFavorites(notification: SimpleNotification) {
    this.favorites.push(notification);
    const index = this.applications.indexOf(notification);
    this.applications.splice(index, 1);
  }

  private removeFromFavorites(notification: SimpleNotification) {
    this.applications.splice(0, 0, notification); // alternative to arr.push (pushes to index 0 instead of last place)
    const index = this.favorites.indexOf(notification);
    this.favorites.splice(index, 1);
  }

  // checks whether task has application or not; if yes: show task and its applications, if not, dont show it at all
  filterApplications(task: number) {
    let counter = 0;
    if (this.applications) {
      for (let i = 0; i < this.applications.length; i++) {
        if (this.applications[i].taskId === task) {
          counter++;
        }
      }
    }
    this.hasApplications = (counter !== 0);
  }

  // same as filterApplications, but for favorites[]
  filterApplicationsFavs(task: number) {
    let counter = 0;
    if (this.favorites) {
      for (let i = 0; i < this.favorites.length; i++) {
        if (this.favorites[i].taskId === task) {
          counter++;
        }
      }
    }
    this.hasApplicationsFav = (counter !== 0);
  }
}
