import {Component, OnInit} from '@angular/core';
import {DetailedEvent} from '../../dtos/detailed-event';
import {AuthService} from '../../services/auth.service';
import {Task} from '../../dtos/task';
import {ApplicationService} from '../../services/application.service';
import {Router} from '@angular/router';
import {AlertService} from '../../alert';
import {EmployeeService} from '../../services/employee.service';

@Component({
  selector: 'app-event-applied',
  templateUrl: './event-applied.component.html',
  styleUrls: ['./event-applied.component.scss']
})
export class EventAppliedComponent implements OnInit {
  events: DetailedEvent[] = [];
  futureEvents: DetailedEvent[] = [];
  error: boolean = false;
  errorMessage: string = '';

  constructor(public authService: AuthService, private applicationService: ApplicationService,
              private router: Router, private alertService: AlertService, private employeeService: EmployeeService) {
  }

  private getStatus(tasks: Task[]) {
    for (const task of tasks) {
      for (const emp of task.employees) {
        if (emp.employee.superSimpleProfileDto.email === this.authService.getTokenIdentifier()) {
          return emp.accepted;
        }
      }
    }
    return null;
  }

  private getTaskDescription(tasks: Task[]) {
    for (const task of tasks) {
      for (const emp of task.employees) {
        if (emp.employee.superSimpleProfileDto.email === this.authService.getTokenIdentifier()) {
          return task.description;
        }
      }
    }
    return null;
  }

  private getTask(tasks: Task[]) {
    for (const task of tasks) {
      for (const emp of task.employees) {
        if (emp.employee.superSimpleProfileDto.email === this.authService.getTokenIdentifier()) {
          return task;
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
        for (const event of events) {
          if (this.checkDateInFuture(event.start)) {
            this.futureEvents.push(event);
          }
        }
      }
    );
  }

  checkDateInFuture(date) {
    return new Date(date) >= new Date();
  }
  checkForThreeDaysBeforeStart(date) {
    const currDate = new Date();
    currDate.setDate(currDate.getDate() + 3);
    return new Date(date) <= currDate;
  }
  maximumDate(date) {
    const newDate = new Date(date);
    newDate.setDate(newDate.getDate() - 3);
    return newDate;
  }
  deleteApplication(id: number) {
    this.applicationService.getApplicationsForEvent(id).subscribe(
      (applications) => {
        for (const application of applications) {
          if (application.sender.email === this.authService.getTokenIdentifier()) {
            this.applicationService.deleteApplication(application.id).subscribe(
              () => {
                this.alertService.success('Bewerbung erfolgreich zurückgezogen', {autoClose: true});
                // this.router.navigate(['events']);
                this.applicationService.getAppliedEvents().subscribe(
                  (events: DetailedEvent[]) => {
                    this.events = events;
                  }
                );
              }
            );
          }
        }
      }
    );
  }
  deleteJob(id: number) {
    this.applicationService.deleteJob(id).subscribe(
      () => {
        this.alertService.success('Stelle erfolgreich gekündigt', {autoClose: true});
        this.applicationService.getAppliedEvents().subscribe(
          (events: DetailedEvent[]) => {
            this.events = events;
          }
        );
      }
    );
  }
}
