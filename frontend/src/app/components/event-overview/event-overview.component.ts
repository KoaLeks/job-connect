import {Component, Input, OnInit} from '@angular/core';
import {EventService} from '../../services/event.service';
import {AuthService} from '../../services/auth.service';
import {DetailedEvent} from '../../dtos/detailed-event';
import {Task} from '../../dtos/task';
import {FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-event-overview',
  templateUrl: './event-overview.component.html',
  styleUrls: ['./event-overview.component.scss']
})
export class EventOverviewComponent implements OnInit {
  events: DetailedEvent[] = [];
  foundEvents: DetailedEvent[] = [];
  eventSearchForm;
  error: boolean = false;
  errorMessage: string = '';

  constructor(public authService: AuthService, private eventService: EventService, private formBuilder: FormBuilder) {
    this.eventSearchForm = this.formBuilder.group(
      {
        title: ''
      }
    );
  }

  ngOnInit(): void {
    this.loadEvents();
  }

  private loadEvents() {
    this.eventService.getEvents().subscribe(
      (events: DetailedEvent[]) => {
        this.events = events;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }
  searchEvent(event: DetailedEvent) {
    this.eventService.searchEvent(event).subscribe(
      (events: DetailedEvent[]) => {
        this.foundEvents = events;
        console.log(this.foundEvents.length);
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
}
