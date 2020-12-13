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
  //   = new Event(-1, '2020-12-10', '2020-12-21', 'wow super titel',
  //   'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna ' +
  //   'aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea ' +
  //   'takimata ' +
  //   'sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor ' +
  //   'invidunt ' +
  //   'ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita ' +
  //   'kasd ' +
  //   'gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed' +
  //   ' diam ' +
  //   'nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo' +
  //   ' dolores ' +
  //   'et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.Duis autem vel eum iriure dolor in ' +
  //   'hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu f',
  //   new Employer(1, 'Nix is mit Fix', 'super tolle beschreibung hier', 'vorname', 'nachname',
  //     'a@a', '1111111', 'des is privat'),
  //   new Address(-1, 'Wien', 'Wien', 1020, 'teststrasse 8', '12/08'),
  //   [
  //     new Task(-1, 'wow', 22, 3, -1, '10',
  //       new InterestArea(-1, 'interest area hier', 'beschreibungbeschreibungbeschreibungbeschreibungbeschreibungbeschreibungbeschreibungbeschreibung', 'hab ka mehr', 'auch hier ka')),
  //     new Task(-1, 'wow', 22, 3, -1, '10',
  //       new InterestArea(-1, 'interest area hier', 'beschreibung', 'hab ka mehr', 'auch hier ka')),
  //     new Task(-1, 'wow', 22, 3, -1, '10',
  //       new InterestArea(-1, 'interest area hier', 'beschreibung', 'hab ka mehr', 'auch hier ka')),
  //     new Task(-1, 'wow', 22, 3, -1, '10',
  //       new InterestArea(-1, 'interest area hier', 'beschreibung', 'hab ka mehr', 'auch hier ka')),
  //     new Task(-1, 'wow', 22, 3, -1, '10',
  //       new InterestArea(-1, 'interest area hier', 'beschreibung', 'hab ka mehr', 'auch hier ka')),
  //     new Task(-1, 'wow', 22, 3, -1, '10',
  //       new InterestArea(-1, 'interest area hier', 'beschreibung', 'hab ka mehr', 'auch hier ka')),
  //     new Task(-1, 'wow', 22, 3, -1, '10',
  //       new InterestArea(-1, 'interest area hier', 'beschreibung', 'hab ka mehr', 'auch hier ka')),
  //     new Task(-1, 'wow', 22, 3, -1, '10',
  //       new InterestArea(-1, 'interest area hier', 'beschreibung', 'hab ka mehr', 'auch hier ka'))
  //   ]
  // );
  applyTaskForm;

  constructor(private route: ActivatedRoute, private employerService: EmployerService,
              private eventService: EventService, private formBuilder: FormBuilder, private applicationService: ApplicationService) {
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
