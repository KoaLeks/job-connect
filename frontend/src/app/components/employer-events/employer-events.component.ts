import {Component, OnInit} from '@angular/core';
import {Task} from '../../dtos/task';
import {DetailedEvent} from '../../dtos/detailed-event';
import {EventService} from '../../services/event.service';
import {ActivatedRoute} from '@angular/router';
import {EmployerService} from '../../services/employer.service';
import {SimpleEmployer} from '../../dtos/simple-employer';

@Component({
  selector: 'app-employer-events',
  templateUrl: './employer-events.component.html',
  styleUrls: ['./employer-events.component.scss']
})
export class EmployerEventsComponent implements OnInit {
  uniqueDateArray: string[] = [];
  events: DetailedEvent[] = [];
  employerId; // is a String
  employer: SimpleEmployer;

  constructor(private eventService: EventService, private route: ActivatedRoute, private employerService: EmployerService) {
  }

  ngOnInit(): void {
    this.employerId = this.route.snapshot.paramMap.get('id'); // gets ID from URL
    this.employerService.getEmployerById(this.employerId).subscribe(
      (employer) => {
        this.employer = employer;
      });
    this.eventService.getEvents().subscribe(
      (events: DetailedEvent[]) => {
        for (const e of events) {
          if ((e.employer.simpleProfileDto.id).toString() === this.employerId) {
            this.events.push(e);
          }
        }
        this.sortEventsByDate();
      });
  }

  // sorts Events by Date by calculating the number of milliseconds between January 1, 1970 and 'event.start'
  private sortEventsByDate() {
    this.uniqueDateArray = [];
    const dateArray: string[] = [];

    for (const event of this.events) {
      event.sortHelper = Date.parse(event.start); // returns the number of milliseconds between January 1, 1970 and 'event.start'
      dateArray.push(event.start);
    }

    for (const date of dateArray) {
      if (this.uniqueDateArray.indexOf(date.split('T')[0]) === -1) {
        if (new Date() <= new Date(date)) { // only show future events
          this.uniqueDateArray.push(date.split('T')[0]);
        }
      }
    }

    this.events.sort((a, b) => (a.sortHelper > b.sortHelper ? 1 : -1));
    this.uniqueDateArray.sort((a, b) => (a > b ? 1 : -1));
  }

  checkDateInFuture(date) {
    return new Date(date) >= new Date();
  }

  getAmountOfFreeJobs(tasks: Task[]) {
    let sum = 0;
    for (const task of tasks) {
      sum += task.employeeCount;
    }
    return sum;
  }

  getAmountOfTakenJobs(tasks: Task[]) {
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
}
