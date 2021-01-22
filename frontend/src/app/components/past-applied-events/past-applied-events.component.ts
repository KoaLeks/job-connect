import {Component, OnInit} from '@angular/core';
import {DetailedEvent} from '../../dtos/detailed-event';
import {AuthService} from '../../services/auth.service';
import {ApplicationService} from '../../services/application.service';
import {Task} from '../../dtos/task';

@Component({
  selector: 'app-past-applied-events',
  templateUrl: './past-applied-events.component.html',
  styleUrls: ['./past-applied-events.component.scss']
})
export class PastAppliedEventsComponent implements OnInit {
  events: DetailedEvent[] = [];
  error: boolean = false;
  errorMessage: string = '';
  pastAppliedEvents: number = 0;

  constructor(public authService: AuthService, private applicationService: ApplicationService) {
  }

  private getStatus(tasks: Task[]) {
    for (const task of tasks) {
      for (const emp of task.employees) {
        if (emp.employee.superSimpleProfileDto.email === this.authService.getEmail()) {
          return emp.accepted;
        }
      }
    }
    return null;
  }

  private getTaskDescription(tasks: Task[]) {
    for (const task of tasks) {
      for (const emp of task.employees) {
        if (emp.employee.superSimpleProfileDto.email === this.authService.getEmail()) {
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

  addOnePastAppliedEvent() {
    this.pastAppliedEvents++;
  }
}
