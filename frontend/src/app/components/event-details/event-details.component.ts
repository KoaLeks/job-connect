import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {EventService} from '../../services/event.service';
import {Task} from '../../dtos/task';
import {EmployerService} from '../../services/employer.service';
import {DetailedEvent} from '../../dtos/detailed-event';
import {FormBuilder, Validators} from '@angular/forms';
import {Application} from '../../dtos/application';
import {ApplicationService} from '../../services/application.service';
import {EmployeeService} from '../../services/employee.service';
import {AuthService} from '../../services/auth.service';
import {EditEmployee} from '../../dtos/edit-employee';
import {AlertService} from '../../alert';
import {UpdateHeaderService} from '../../services/update-header.service';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.scss']
})
export class EventDetailsComponent implements OnInit {


  error = false;
  errorMessage: any;
  picture: any;
  hasPicture = false;
  id: number;
  eventDetails: DetailedEvent;
  loggedInEmployee = false;
  loggedInEmployer = false;
  employee: any;
  applied = false;
  appliedTask;
  appliedStatus;
  applyTaskForm;
  interestAreasDist: Set<String> = new Set<String>();
  freeJobs = false;

  constructor(public authService: AuthService, private route: ActivatedRoute, private employerService: EmployerService,
              private eventService: EventService, private formBuilder: FormBuilder, private applicationService: ApplicationService,
              private employeeService: EmployeeService, private router: Router, private alertService: AlertService,
              private updateHeaderService: UpdateHeaderService) {
    this.route.params.subscribe(params => {
      this.id = params.id;
    });
    this.applyTaskForm = this.formBuilder.group({
      applicationText: [null, Validators.required],
      inputTask: [null, Validators.required]
    });
  }

  arrayBufferToBase64(buffer) {
    let binary = '';
    const bytes = new Uint8Array(buffer);
    const len = bytes.byteLength;
    for (let i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    this.picture = window.btoa(binary);
  }

  ngOnInit(): void {
    this.getEventDetails();
    if (this.authService.isLoggedIn() && this.authService.getUserRole() === 'EMPLOYEE') {
      this.loggedInEmployee = true;
      this.employeeService.getEmployeeByEmail().subscribe(
        (profile: EditEmployee) => {
          this.employee = profile.profileDto;
        });
    }
    if (this.authService.isLoggedIn() && this.authService.getUserRole() === 'EMPLOYER') {
      this.loggedInEmployer = true;
    }
  }

  private setApplied() {
    if (this.loggedInEmployee) {
      for (const task of this.eventDetails.tasks) {
        for (const emp of task.employees) {
          if (emp.employee.superSimpleProfileDto.email === this.authService.getTokenIdentifier()) {
            this.applied = true;
            this.appliedStatus = emp.accepted;
            this.appliedTask = task.description;
          }
        }
      }
    }
  }

  private getNumberOfParticipants(task: Task) {
    // console.log(JSON.stringify(task.employees));
    let count = 0;
    task.employees.forEach(e => {
      if (e.accepted === true) {
        count++;
      }
    });
    return count;
  }

  private getEventDetails() {
    this.eventService.getEventDetails(this.id).subscribe(
      (detailedEvent: DetailedEvent) => {
        // console.log('Event ' + JSON.stringify(detailedEvent));
        this.eventDetails = detailedEvent;

        for (const task of this.eventDetails.tasks) {
          if (task.interestArea !== null) {
            this.interestAreasDist.add(task.interestArea.area);
          }
        }

        // converts bytesArray to Base64
        this.arrayBufferToBase64(detailedEvent.employer.simpleProfileDto.picture);
        if (detailedEvent.employer.simpleProfileDto.picture != null) {
          this.picture = 'data:image/png;base64,' + this.picture;
          this.hasPicture = true;
        } else {
          this.picture = null;
          this.hasPicture = false;
        }
        this.setApplied();
        this.setFreeJobs();
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      });
  }

  createApplication() {
    if (this.applyTaskForm.value.inputTask === null || this.applyTaskForm.value.inputTask === 'null') {
      this.applyTaskForm.controls['applicationText'].setValue('');
    } else {
      const n: number = this.applyTaskForm.value.inputTask;
      const task = this.eventDetails.tasks.find((t: Task) => t.id.toString() === n.toString());
      this.applyTaskForm.controls['applicationText'].setValue('Sehr geehrte Damen und Herren, \n\rhiermit bewerbe ich mich für die Stelle "'
        + task.description + '" für das Event ' + this.eventDetails.title + '\n\rMit freundlichen Grüßen\n'
        + this.employee.firstName + ' ' + this.employee.lastName);
    }
  }

  apply(value: any) {
    const application = new Application(value.inputTask, value.applicationText);
    this.applicationService.applyTask(application).subscribe(() => {
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
    this.applyTaskForm.reset();
  }

  deleteEvent() {
    this.eventService.deleteEvent(this.id).subscribe(
      () => {
        this.router.navigate(['events']);
        this.alertService.success('Event erfolgreich abgesagt', {autoClose: true});
        this.updateHeaderService.emitDeletedEvent(this.id);
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }

  checkDateInFuture(endDate) {
    return new Date(endDate) >= new Date();
  }

  checkDateIn24h(startDate) {
    const nowPlus24Hours = new Date();
    nowPlus24Hours.setDate(nowPlus24Hours.getDate() + 1);
    return new Date(startDate) <= nowPlus24Hours;
  }

  deleteApplication(id: number) {
    this.applicationService.getApplicationsForEvent(id).subscribe(
      (applications) => {
            for (const application of applications) {
              if (application.sender.email === this.authService.getTokenIdentifier()) {
                this.applicationService.deleteApplication(application.id).subscribe(
                  () => {
                    this.alertService.success('Bewerbung erfolgreich gelöscht', {autoClose: true});
                    this.router.navigate(['applied-events']);
                    this.updateHeaderService.emitDeletedEvent(this.id);
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
        this.router.navigate(['applied-events']);
        this.updateHeaderService.emitDeletedEvent(this.id);
      });
  }

  getStatus(tasks: Task[]) {
    for (const task of tasks) {
      for (const emp of task.employees) {
        if (emp.employee.superSimpleProfileDto.email === this.authService.getTokenIdentifier()) {
          return emp.accepted;
        }
      }
    }
  }

  getTaskId(tasks: Task[]) {
    for (const task of tasks) {
      for (const emp of task.employees) {
        if (emp.employee.superSimpleProfileDto.email === this.authService.getTokenIdentifier()) {
          return task.id;
        }
      }
    }
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

  getTaskDescription(tasks: Task[]) {
    for (const task of tasks) {
      for (const emp of task.employees) {
        if (emp.employee.superSimpleProfileDto.email === this.authService.getTokenIdentifier()) {
          return task.description;
        }
      }
    }
    return null;
  }

  private setFreeJobs() {
    for (const task of this.eventDetails.tasks) {
      if (this.getNumberOfParticipants(task) < task.employeeCount) {
        this.freeJobs = true;
      }
    }
  }
}
