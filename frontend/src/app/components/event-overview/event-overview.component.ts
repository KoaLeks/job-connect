import { Component, OnInit } from '@angular/core';
import {EventService} from '../../services/event.service';
import {AuthService} from '../../services/auth.service';
import {DetailedEvent} from '../../dtos/detailed-event';
import {Task} from '../../dtos/task';
import {FormBuilder} from '@angular/forms';
import {SearchEvent} from '../../dtos/search-event';
import {InterestAreaService} from '../../services/interestArea.service';
import {InterestArea} from '../../dtos/interestArea';
import {EmployerService} from '../../services/employer.service';
import {SimpleEmployer} from '../../dtos/simple-employer';
import {EmployeeService} from '../../services/employee.service';
import {EditEmployee} from '../../dtos/edit-employee';

@Component({
  selector: 'app-event-overview',
  templateUrl: './event-overview.component.html',
  styleUrls: ['./event-overview.component.scss']
})
export class EventOverviewComponent implements OnInit {
  events: DetailedEvent[] = [];
  eventSearchForm;
  countEvents: DetailedEvent[] = [];

  interestAreas: InterestArea[];
  employers: SimpleEmployer[];

  states: string[] = ['Burgenland', 'Kärnten', 'Niederösterreich', 'Oberösterreich', 'Salzburg', 'Steiermark', 'Tirol', 'Vorarlberg',
                      'Wien'];
  paymentValue: number = 0;
  search: boolean = false;
  error: boolean = false;
  errorMessage: string = '';
  loggedInEmployee: boolean;
  loggedInEmployer: boolean;
  notLoggedIn: boolean;
  employerEvents: DetailedEvent[] = [];
  uniqueDateArray: string[] = [];
  uniqueDateArrayEmployer: string[] = [];

  currProfile: EditEmployee;

  constructor(public authService: AuthService, private eventService: EventService, private interestAreaService: InterestAreaService,
              private employerService: EmployerService, private employeeService: EmployeeService, private formBuilder: FormBuilder) {
    this.eventSearchForm = this.formBuilder.group(
      {
        title: '',
        interestAreaId: '',
        employerId: '',
        payment: '',
        start: '',
        end: '',
        state: '',
        onlyAvailableTasks: false,
        userId: ''
      }
    );
  }

  ngOnInit(): void {
    this.loadResources();
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
  private loadResources() {
    this.eventService.getEvents().subscribe(
      (events: DetailedEvent[]) => {
        this.events = events;
        for (const event of events) {
          if (this.loggedInEmployer && this.authService.getTokenIdentifier() === event.employer.simpleProfileDto.email
            && this.checkDateInFuture(event.end)) {
            this.employerEvents.push(event);
          }
        }
        this.sortEventsByDate();
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
    this.interestAreaService.getInterestAreas().subscribe(
      (areas: InterestArea[]) => {
        this.interestAreas = areas;
      }, error => {
        this.defaultServiceErrorHandling(error);
      }
    );
    this.employerService.getEmployers().subscribe(
      (employers: SimpleEmployer[]) => {
        this.employers = employers;
      }, error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }
  searchEvent(event: SearchEvent) {
    if (event.userId) {
      this.employeeService.getEmployeeByEmail().subscribe(
        (profile: EditEmployee) => {
          event.userId = profile.profileDto.id;
          this.eventService.searchEvent(event).subscribe(
            (events: DetailedEvent[]) => {
              this.events = events;
              this.countEvents = [];
              for (const e of events) {
                if (this.checkDateInFuture(e.start)) {
                  this.countEvents.push(e);
                }
              }
              this.sortEventsByDate();
              this.search = true;
              this.employerEvents = [];
              for (const e of this.events) {
                if (this.loggedInEmployer && this.authService.getTokenIdentifier() === e.employer.simpleProfileDto.email
                  && this.checkDateInFuture(e.end)) {
                  this.employerEvents.push(e);
                }
              }
              this.sortEventsByDate();
            }, error => {
              this.error = true;
              this.errorMessage = error.error;
            }
          );
        }, (error) => {
          this.defaultServiceErrorHandling(error);
        }
      );
    } else {
      this.eventService.searchEvent(event).subscribe(
        (events: DetailedEvent[]) => {
          this.events = events;
          this.countEvents = [];
          for (const e of events) {
            if (this.checkDateInFuture(e.start)) {
              this.countEvents.push(e);
            }
          }
          this.sortEventsByDate();
          this.search = true;
          this.employerEvents = [];
          for (const e of this.events) {
            if (this.loggedInEmployer && this.authService.getTokenIdentifier() === e.employer.simpleProfileDto.email
              && this.checkDateInFuture(e.end)) {
              this.employerEvents.push(e);
            }
          }
          this.sortEventsByDate();
        }, error => {
          this.defaultServiceErrorHandling(error);
        }
      );
    }
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
  getSliderValue(event) {
    this.paymentValue = event.target.value;
  }

  // sorts Events by Date by calculating the number of milliseconds between January 1, 1970 and 'event.start'
  private sortEventsByDate() {
    this.uniqueDateArray = [];
    this.uniqueDateArrayEmployer = [];
    const dateArray: string[] = [];
    const dateArrayEmployer: string[] = [];

    for (const event of this.events) {
      event.sortHelper = Date.parse(event.start); // returns the number of milliseconds between January 1, 1970 and 'event.start'
      dateArray.push(event.start);
    }

    for (const event of this.employerEvents) {
      dateArrayEmployer.push(event.start.split('T')[0]);
    }

    for (const date of dateArray) {
      if (this.uniqueDateArray.indexOf(date.split('T')[0]) === -1) {
        if (new Date() <= new Date(date)) { // only show future events
          this.uniqueDateArray.push(date.split('T')[0]);
        }
      }
    }

    for (const date of dateArrayEmployer) {
      if (this.uniqueDateArrayEmployer.indexOf(date) === -1) {
          this.uniqueDateArrayEmployer.push(date);
      }
    }

    this.events.sort((a, b) => (a.sortHelper > b.sortHelper ? 1 : -1));
    this.employerEvents.sort((a, b) => (a.sortHelper > b.sortHelper ? 1 : -1));
    this.uniqueDateArray.sort((a, b) => (a > b ? 1 : -1));
    this.uniqueDateArrayEmployer.sort((a, b) => (a > b ? 1 : -1));
  }

  checkDateInFuture(date) {
    return new Date(date) >= new Date();
  }
  resetForm() {
    this.eventSearchForm = this.formBuilder.group(
      {
        title: '',
        interestAreaId: '',
        employerId: '',
        payment: 0,
        start: '',
        end: '',
        state: '',
        onlyAvailableTasks: false,
        userId: ''
      }
    );
    this.paymentValue = 0;
  }
}
