import { Component, OnInit } from '@angular/core';
import {DetailedEvent} from '../../dtos/detailed-event';
import {AuthService} from '../../services/auth.service';
import {EventService} from '../../services/event.service';
import {Task} from '../../dtos/task';
import {ApplicationService} from '../../services/application.service';

@Component({
  selector: 'app-event-applied',
  templateUrl: './event-applied.component.html',
  styleUrls: ['./event-applied.component.scss']
})
export class EventAppliedComponent implements OnInit {
  events: DetailedEvent[] = [];
  error: boolean = false;
  errorMessage: string = '';

  constructor(public authService: AuthService, private applicationService: ApplicationService) {
  }

  private getStatus(tasks: Task[]) {
    for (const task of tasks) {
      for (const emp of task.employees) {
        if (emp.employee.simpleProfileDto.email === this.authService.getEmail()) {
          return emp.accepted;
        }
      }
    }
    return null;
  }

  private getTaskDescription(tasks: Task[]) {
    for (const task of tasks) {
      for (const emp of task.employees) {
        if (emp.employee.simpleProfileDto.email === this.authService.getEmail()) {
          return task.description;
        }
      }
    }
    return null;
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

  ngOnInit(): void {
    this.applicationService.getAppliedEvents().subscribe(
      (events: DetailedEvent[]) => {
        this.events = events;
      }
    );
  }

  checkDateInFuture(date) {
    return new Date(date) >= new Date();
  }

}
