import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {FormBuilder, Validators} from '@angular/forms';
import {AddressService} from '../../services/address.service';
import {EventService} from '../../services/event.service';
import {TaskService} from '../../services/task.service';
import {Task} from '../../dtos/task';
import {Event} from '../../dtos/event';
import {Address} from '../../dtos/address';
import {InterestArea} from '../../dtos/interestArea';
import {InterestAreaService} from '../../services/interestArea.service';
import {EmployerService} from '../../services/employer.service';

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.scss']
})
export class CreateEventComponent implements OnInit {

  addressCreationForm;
  eventCreationForm;
  taskCreationForm;
  tasks: Task[] = [];
  interestAreas: InterestArea[];
  employerId: number;

  constructor(public authService: AuthService, private formBuilder: FormBuilder, private addressService: AddressService,
              private eventService: EventService, private taskService: TaskService,
              private interestAreaService: InterestAreaService,
              private employerService: EmployerService) {
    this.addressCreationForm = this.formBuilder.group({
      city: [null, Validators.required],
      state: [null, Validators.required],
      zip: [null, Validators.required],
      addressLine: [null, Validators.required],
      additional: [null]
    });
    this.eventCreationForm = this.formBuilder.group({
      id: [null],
      start: [null, Validators.required],
      end: [null, Validators.required],
      title: [null, Validators.required],
      description: [null, Validators.required],
      address: [null],
      employer: [null],
      tasks: [null]
    });
    this.taskCreationForm = this.formBuilder.group({
      description: [null, Validators.required],
      employeeCount: [null, Validators.required],
      paymentHourly: [null, Validators.required],
      event: [null],
      employees: [null],
      interestArea: [null]
    });
  }

  ngOnInit() {
  }


  /**
   * Saves new Event
   */
  createEvent(event: Event, address: Address, tasks: Task[]) {
    this.addressService.createAddress(address).subscribe(
      (a) => {
        event.address = {
          id: a.id,
          city: null,
          state: null,
          zip: null,
          addressLine: null,
          additional: null
        };

        // set Employer in event
        event.employer = {
          id: this.employerId,
          companyName: null,
          companyDescription: null,
          firstName: null,
          lastName: null,
          email: null,
          password: null,
          publicInfo: null
        };

        this.eventService.createEvent(event).subscribe(
          (e) => {
            for (const t of tasks) {
              t.event = {
                id: e.id,
                start: null,
                end: null,
                title: null,
                description: null,
                employer: null,
                address: null,
                tasks: null
              };
              this.taskService.createTask(t).subscribe(
                () => {
                }
              );
            }
          }
        );
      }
    );
  }

  getInterestAreas() {
    if (this.interestAreas === undefined || this.interestAreas.length === 0) {
      this.interestAreaService.getInterestAreas().subscribe(
        (interestAreas) => {
          this.interestAreas = interestAreas;
        }
      );
    }
  }

  addTask(task: Task) {
    task.interestArea = {
      id: this.taskCreationForm.value.interestArea,
      area: null,
      description: null,
      interests: null,
      tasks: null
    };
    this.tasks.push(task);
    console.log('task: ' + task.interestArea);
    console.log('task: ' + JSON.stringify(task));
    this.taskCreationForm.reset();
  }

  deleteTask(task: Task) {
    const index = this.tasks.indexOf(task);
    if (index !== -1) {
      this.tasks.splice(index, 1);
    }
  }

  /**
   * Get profile id
   */
  loadEmployerId() {
    this.employerService.getEmployerByEmail(this.authService.getTokenIdentifier()).subscribe(
      (profile) => {
        this.employerId = profile.profileDto.id;
        console.log(this.employerId);
      }
    );
  }

}
