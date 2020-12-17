import { Component, OnInit } from '@angular/core';
import {Event} from '../../dtos/event';
import {EventService} from '../../services/event.service';
import {Router} from '@angular/router';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-event-overview',
  templateUrl: './event-overview.component.html',
  styleUrls: ['./event-overview.component.scss']
})
export class EventOverviewComponent implements OnInit {
  events: Event[] = [];
  error: boolean = false;
  errorMessage: string = '';

  constructor(public authService: AuthService, private eventService: EventService) {
  }

  ngOnInit(): void {
    this.loadEvents();
  }

  private loadEvents() {
    this.eventService.getEvents().subscribe(
      (events: Event[]) => {
        this.events = events;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
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
