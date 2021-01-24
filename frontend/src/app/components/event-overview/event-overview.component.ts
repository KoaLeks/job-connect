import {Component, OnInit} from '@angular/core';
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
import {SuperSimpleEmployer} from '../../dtos/super-simple-employer';
import {EventOverview} from '../../dtos/event-overview';

@Component({
  selector: 'app-event-overview',
  templateUrl: './event-overview.component.html',
  styleUrls: ['./event-overview.component.scss']
})
export class EventOverviewComponent implements OnInit {
  events: EventOverview[] = [];

  // pagination
  currentPage = 1;
  pageSize = 5;
  uniqueDateSetPage: Set<String>;
  collectionSize;
  pageEvents: EventOverview[];
  eventSearchForm;
  countEvents: EventOverview[] = [];

  interestAreas: InterestArea[];
  employers: SuperSimpleEmployer[];

  states: string[] = ['Burgenland', 'Kärnten', 'Niederösterreich', 'Oberösterreich', 'Salzburg', 'Steiermark', 'Tirol', 'Vorarlberg',
    'Wien'];
  paymentValue: number = 0;
  search: boolean = false;
  error: boolean = false;
  errorMessage: string = '';
  loggedInEmployee: boolean;
  loggedInEmployer: boolean;
  notLoggedIn: boolean;
  employerEvents: EventOverview[] = [];

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
    if (this.authService.isLoggedIn() && this.authService.userIsEmployee()) {
      this.loggedInEmployee = true;
    }
    if (this.authService.isLoggedIn() && this.authService.userIsEmployer()) {
      this.loggedInEmployer = true;
    }
    if (!this.authService.isLoggedIn()) {
      this.notLoggedIn = true;
    }
  }

  private loadResources() {
    this.eventService.getEvents().subscribe(
      (events: EventOverview[]) => {
        this.events = events;
        for (const event of events) {
          if (this.loggedInEmployer && this.authService.getTokenIdentifier() === event.employer.superSimpleProfileDto.email
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
      (employers: SuperSimpleEmployer[]) => {
        this.employers = employers;
      }, error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  searchEvent(event: SearchEvent) {
    this.currentPage = 1;
    if (event.userId) {
      this.employeeService.getEmployeeByEmail().subscribe(
        (profile: EditEmployee) => {
          event.userId = profile.profileDto.id;
          this.eventService.searchEvent(event).subscribe(
            (events: EventOverview[]) => {
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
                if (this.loggedInEmployer && this.authService.getTokenIdentifier() === e.employer.superSimpleProfileDto.email
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
        (events: EventOverview[]) => {
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
            if (this.loggedInEmployer && this.authService.getTokenIdentifier() === e.employer.superSimpleProfileDto.email
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
    if (this.loggedInEmployer) {
      for (const event of this.employerEvents) {
        event.sortHelper = Date.parse(event.start);
      }
      this.employerEvents.sort((a, b) => (a.sortHelper > b.sortHelper ? 1 : -1));
    } else {
      for (const event of this.events) {
        event.sortHelper = Date.parse(event.start); // returns the number of milliseconds between January 1, 1970 and 'event.start'
      }
      this.events.sort((a, b) => (a.sortHelper > b.sortHelper ? 1 : -1));
    }

    this.refreshEvents();
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

  /**
   * Changes the currently shown events and dates (pagination).
   *
   * Note:
   *  - If the user is an employer they only see their own upcoming and running events.
   *  - Else the user sees all events which haven't started yet.
   */
  refreshEvents() {
    this.uniqueDateSetPage = new Set<String>();

    if (this.loggedInEmployer) {
      this.collectionSize = this.employerEvents.length;
      this.pageEvents = this.employerEvents
        .map((event, i) => ({id: i + 1, ...event}))
        .slice((this.currentPage - 1) * this.pageSize, (this.currentPage - 1) * this.pageSize + this.pageSize);

      for (const event of this.pageEvents) {
        if (new Date(event.end) > new Date()) {
          this.uniqueDateSetPage.add(event.start.split('T')[0]);
        }
      }
    } else {
      // get all events which have not started yet
      const upcomingEvents = [];
      for (const event of this.events) {
        if (new Date(event.start) > new Date()) {
          upcomingEvents.push(event);
        }
      }

      this.collectionSize = upcomingEvents.length;
      this.pageEvents = upcomingEvents
        .map((event, i) => ({id: i + 1, ...event}))
        .slice((this.currentPage - 1) * this.pageSize, (this.currentPage - 1) * this.pageSize + this.pageSize);

      for (const event of this.pageEvents) {
        if (new Date(event.start) > new Date()) {
          this.uniqueDateSetPage.add(event.start.split('T')[0]);
        }
      }
    }
  }
}
