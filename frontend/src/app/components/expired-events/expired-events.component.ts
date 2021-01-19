import {Component, OnInit} from '@angular/core';
import {DetailedEvent} from '../../dtos/detailed-event';
import {AuthService} from '../../services/auth.service';
import {EventService} from '../../services/event.service';
import {Task} from '../../dtos/task';

@Component({
  selector: 'app-expired-events',
  templateUrl: './expired-events.component.html',
  styleUrls: ['./expired-events.component.scss']
})
export class ExpiredEventsComponent implements OnInit {
  error: boolean = false;
  errorMessage: string = '';
  loggedInEmployer: boolean;
  employerEvents: DetailedEvent[] = [];
  uniqueDateArrayEmployer: string[] = [];

  constructor(public authService: AuthService, private eventService: EventService) {
  }

  ngOnInit(): void {
    this.loadEvents();
    if (this.authService.isLoggedIn() && this.authService.getUserRole() === 'EMPLOYER') {
      this.loggedInEmployer = true;
    }
  }

  private loadEvents() {
    this.eventService.getEvents().subscribe(
      (events: DetailedEvent[]) => {
        for (const event of events) {
          if (this.loggedInEmployer && this.authService.getTokenIdentifier() === event.employer.simpleProfileDto.email
            && !this.checkDateInFuture(event.end)) {
            this.employerEvents.push(event);
          }
        }
        this.sortEventsByDate();
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  private getAmountOfFreeJobs(tasks: Task[]) {
    let sum = 0;
    for (const task of tasks) {
      sum += task.employeeCount;
    }
    return sum;
  }

  private getAmountOfTakenJobs(tasks: Task[]) {
    let sum = 0;
    for (const task of tasks) {
      for (const employee of task.employees) {
        if (employee.accepted === true) {
          sum += 1;
        }
      }
    }
    return sum;
  }

  private defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (typeof error.error === 'object') {
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error;
    }
  }

  // sorts Events by Date by calculating the number of milliseconds between January 1, 1970 and 'event.start'
  private sortEventsByDate() {
    const dateArrayEmployer: string[] = [];

    for (const event of this.employerEvents) {
      event.sortHelper = Date.parse(event.start); // returns the number of milliseconds between January 1, 1970 and 'event.start'
      dateArrayEmployer.push(event.start.split('T')[0]);
    }

    for (const date of dateArrayEmployer) {
      if (this.uniqueDateArrayEmployer.indexOf(date) === -1) {
          this.uniqueDateArrayEmployer.push(date);
      }
    }

    this.employerEvents.sort((a, b) => (a.sortHelper > b.sortHelper ? 1 : -1));
    this.uniqueDateArrayEmployer.sort((a, b) => (a > b ? 1 : -1));
  }

  checkDateInFuture(date) {
    return new Date(date) >= new Date();
  }

}
