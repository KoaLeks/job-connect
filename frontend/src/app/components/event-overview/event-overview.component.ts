import { Component, OnInit } from '@angular/core';
import {EventService} from '../../services/event.service';
import {AuthService} from '../../services/auth.service';
import {DetailedEvent} from '../../dtos/detailed-event';
import {Task} from '../../dtos/task';

@Component({
  selector: 'app-event-overview',
  templateUrl: './event-overview.component.html',
  styleUrls: ['./event-overview.component.scss']
})
export class EventOverviewComponent implements OnInit {
  events: DetailedEvent[] = [];
  error: boolean = false;
  errorMessage: string = '';
  loggedInEmployee: boolean;
  loggedInEmployer: boolean;
  notLoggedIn: boolean;
  employerEvents: DetailedEvent[] = [];
  uniqueDateArray: string[] = [];
  uniqueDateArrayEmployer: string[] = [];

  constructor(public authService: AuthService, private eventService: EventService) {
  }

  ngOnInit(): void {
    this.loadEvents();
    if (this.authService.isLoggedIn() && this.authService.getUserRole() === 'EMPLOYEE') {
      this.loggedInEmployee = true;
    }
    if (this.authService.isLoggedIn() && this.authService.getUserRole() === 'EMPLOYER') {
      this.loggedInEmployer = true;
    }
    if (!this.authService.isLoggedIn()) {
      this.notLoggedIn = true;
    }
  }

  private loadEvents() {
    this.eventService.getEvents().subscribe(
      (events: DetailedEvent[]) => {
        this.events = events;
        this.getEmployerEvents();
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
  private getEmployerEvents() {
    if (this.events !== null && this.events.length !== 0) {
      for (const event of this.events) {
        if (this.loggedInEmployer && this.authService.getTokenIdentifier() === event.employer.simpleProfileDto.email) {
          this.employerEvents.push(event);
        }
      }
    }
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
    const dateArray: string[] = [];
    const dateArrayEmployer: string[] = [];

    for (const event of this.events) {
      event.sortHelper = Date.parse(event.start); // returns the number of milliseconds between January 1, 1970 and 'event.start'
      dateArray.push(event.start.split('T')[0]);
    }

    for (const event of this.employerEvents) {
      dateArrayEmployer.push(event.start.split('T')[0]);
    }

    for (const date of dateArray) {
      if (this.uniqueDateArray.indexOf(date) === -1) {
        if (new Date() <= new Date(date)) { // only show future events
          this.uniqueDateArray.push(date);
        }
      }
    }

    for (const date of dateArrayEmployer) {
      if (this.uniqueDateArrayEmployer.indexOf(date) === -1) {
          this.uniqueDateArrayEmployer.push(date);
      }
    }

    this.events.sort((a, b) => (a.sortHelper > b.sortHelper ? 1 : -1));
    this.uniqueDateArray.sort((a, b) => (a > b ? 1 : -1));
    this.uniqueDateArrayEmployer.sort((a, b) => (a > b ? 1 : -1));
  }

  checkDateInFuture(date) {
    return new Date(date) >= new Date();
  }
}
