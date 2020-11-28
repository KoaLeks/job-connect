import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {FormBuilder} from '@angular/forms';
import {AddressService} from '../../services/address.service';
import {EventService} from '../../services/event.service';
import {TaskService} from '../../services/task.service';
import {Task} from '../../dtos/task';
import {Event} from '../../dtos/event';
import {Address} from '../../dtos/address';

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

  constructor(public authService: AuthService, private formBuilder: FormBuilder, private addressService: AddressService,
              private eventService: EventService, private taskService: TaskService) {
    this.addressCreationForm = this.formBuilder.group({
      city: '',
      state: '',
      zip: '',
      addressLine: '',
      additional: ''
    });
    this.eventCreationForm = this.formBuilder.group({
      id: null,
      start: '',
      end: '',
      description: '',
      address: null,
      employer: null,
      tasks: null
    });
    this.taskCreationForm = this.formBuilder.group({
      description: '',
      employeeCount: '',
      paymentHourly: '',
      event: null,
      employees: null,
      interestArea: null
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
        // TODO set Employer in event
        this.eventService.createEvent(event).subscribe(
          (e) => {
            for (const t of tasks) {
              t.event = {
                id: e.id,
                start: null,
                end: null,
                description: null,
                employer: null,
                address: null,
                tasks: null
              };
              this.taskService.createTask(t).subscribe(
                () => {}
              );
            }
          }
        );
      }
    );
  }

  addTask(task: Task) {
    this.tasks.push(task);
    this.taskCreationForm.reset();
  }

}
