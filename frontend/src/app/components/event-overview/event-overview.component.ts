import {Component, Input, OnInit} from '@angular/core';
import {EventService} from '../../services/event.service';
import {AuthService} from '../../services/auth.service';
import {DetailedEvent} from '../../dtos/detailed-event';
import {Task} from '../../dtos/task';
import {FormBuilder} from '@angular/forms';
import {SearchEvent} from '../../dtos/search-event';
import {InterestAreaService} from '../../services/interestArea.service';
import {InterestArea} from '../../dtos/interestArea';
import {EmployerService} from '../../services/employer.service';
import {Employer} from '../../dtos/employer';
import {SimpleEmployer} from '../../dtos/simple-employer';

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

  paymentValue: number = 0;
  search: boolean = false;
  error: boolean = false;
  errorMessage: string = '';

  constructor(public authService: AuthService, private eventService: EventService, private interestAreaService: InterestAreaService,
              private employerService: EmployerService, private formBuilder: FormBuilder) {
    this.eventSearchForm = this.formBuilder.group(
      {
        title: '',
        interestAreaId: '',
        employerId: '',
        payment: '',
        start: '',
        end: ''
      }
    );
  }

  ngOnInit(): void {
    this.loadResources();
  }
  private loadResources() {
    this.eventService.getEvents().subscribe(
      (events: DetailedEvent[]) => {
        this.events = events;
      }
    );
    this.interestAreaService.getInterestAreas().subscribe(
      (areas: InterestArea[]) => {
        this.interestAreas = areas;
      }
    );
    this.employerService.getEmployers().subscribe(
      (employers: SimpleEmployer[]) => {
        this.employers = employers;
      }
    );
  }
  searchEvent(event: SearchEvent) {
    this.eventService.searchEvent(event).subscribe(
      (events: DetailedEvent[]) => {
        this.foundEvents = events;
        console.log(this.foundEvents.length);
        this.search = true;
      }, error => {

      }
    );
  }
  private getAmountOfFreeJobs(tasks: Task[]) {
    let sum = 0;
    for (let task of tasks) {
      sum += task.employeeCount;
    }
    return sum;
  }
  private getAmountOfTakenJobs(tasks: Task[]) {
    let sum = 0;
    for (let task of tasks) {
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
