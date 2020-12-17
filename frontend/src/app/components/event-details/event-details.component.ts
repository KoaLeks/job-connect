import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {EventService} from '../../services/event.service';
import {Event} from '../../dtos/event';
import {Employer} from '../../dtos/employer';
import {Address} from '../../dtos/address';
import {Task} from '../../dtos/task';
import {InterestArea} from '../../dtos/interestArea';
import {EmployerService} from '../../services/employer.service';
import {DetailedEvent} from '../../dtos/detailed-event';
import {FormBuilder, Validators} from '@angular/forms';
import {Application} from '../../dtos/application';
import {ApplicationService} from '../../services/application.service';
import {EmployeeService} from '../../services/employee.service';
import {AuthService} from '../../services/auth.service';
import {Employee} from '../../dtos/employee';
import { ProfileDto} from '../../dtos/profile-dto';
import {EditEmployee} from '../../dtos/edit-employee';

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
  loggedInEmployee: boolean;
  employee: any;
  applyTaskForm;

  constructor(private authService: AuthService, private route: ActivatedRoute, private employerService: EmployerService,
              private eventService: EventService, private formBuilder: FormBuilder, private applicationService: ApplicationService,
              private employeeService: EmployeeService) {
    this.route.params.subscribe(params => {
      this.id = params.id;
    });
    this.applyTaskForm = this.formBuilder.group({
      applicationText: [null, Validators.required],
      inputTask: [null]
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
      this.employeeService.getEmployeeByEmail(this.authService.getTokenIdentifier()).subscribe(
        (profile: EditEmployee) => {
          this.employee = profile.profileDto;
        });
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
        // converts bytesArray to Base64
        this.arrayBufferToBase64(detailedEvent.employer.simpleProfileDto.picture);
        if (detailedEvent.employer.simpleProfileDto.picture != null) {
          this.picture = 'data:image/png;base64,' + this.picture;
          this.hasPicture = true;
        } else {
          this.picture = null;
          this.hasPicture = false;
        }
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
      const task = this.eventDetails.tasks.find ((t: Task) => t.id.toString() === n.toString());
      this.applyTaskForm.controls['applicationText'].setValue('Sehr geehrte Damen und Herren, \n\rhiermit bewerbe ich mich für die Stelle "'
        +  task.description + '" für das Event ' + this.eventDetails.title + '\n\rMit freundlichen Grüßen\n'
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
}
