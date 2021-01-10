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

@Component({
  selector: 'app-event-overview',
  templateUrl: './event-overview.component.html',
  styleUrls: ['./event-overview.component.scss']
})
export class EventOverviewComponent implements OnInit {
  events: DetailedEvent[] = [];
  foundEvents: DetailedEvent[] = [];
  eventSearchForm;

  interestAreas: InterestArea[];
  employers: SimpleEmployer[];

  states: string[] = ['Burgenland', 'Kärnten', 'Niederösterreich', 'Oberösterreich', 'Salzburg', 'Steiermark', 'Tirol', 'Vorarlberg',
                      'Wien'];
  paymentValue: number = 0;
  search: boolean = false;
  error: boolean = false;
  errorMessage: string = '';

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
    console.log(this.authService.isLoggedIn());
    this.loadResources();
  }
  private loadResources() {
    this.eventService.getEvents().subscribe(
      (events: DetailedEvent[]) => {
        this.events = events;
      }, error => {
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
      this.employeeService.getEmployeeByEmail(this.authService.getTokenIdentifier()).subscribe(
        (profile: EditEmployee) => {
          event.userId = profile.profileDto.id;
          this.eventService.searchEvent(event).subscribe(
            (events: DetailedEvent[]) => {
              this.foundEvents = events;
              this.search = true;
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
          this.foundEvents = events;
          this.search = true;
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
      sum += task.employees.length;
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
}
