import { Component, OnInit } from '@angular/core';
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
  applyTaskForm;

  constructor(private route: ActivatedRoute, private employerService: EmployerService,
              private eventService: EventService, private formBuilder: FormBuilder, private applicationService: ApplicationService) {
    this.route.params.subscribe(params => {
      this.id = params.id;
    });
    this.applyTaskForm = this.formBuilder.group({
      applicationText: ['Sehr geehrte Damen und Herren, \n\r hiermit bewerbe ich mich für die Stelle.', Validators.required],
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
        }
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      });
  }

  apply(value: any) {
    const application = new Application(value.inputTask, value.applicationText);
    this.applicationService.applyTask(application).subscribe(() => {
        this.applyTaskForm.reset();
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }
}
