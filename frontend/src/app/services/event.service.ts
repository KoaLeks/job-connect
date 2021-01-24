import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';
import {Event} from '../dtos/event';
import {DetailedEvent} from '../dtos/detailed-event';
import {SearchEvent} from '../dtos/search-event';
import {EventOverview} from '../dtos/event-overview';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private eventBaseUri: string = this.globals.backendUri + '/events';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Persists event to the backend
   * @param event to persist
   */
  createEvent(event: Event): Observable<Event> {
    console.log('Create event: ' + JSON.stringify(event));
    return this.httpClient.post<Event>(this.eventBaseUri, event);
  }

  /**
   * Get event details by ID
   * @param id of the event to find
   */
  getEventDetails(id: number): Observable<DetailedEvent> {
    console.log('Get event details for: ' + id);
    return this.httpClient.get<DetailedEvent>(this.eventBaseUri + '/' + id + '/details');
  }

  /**
   * Gets all events from the backend
   */
  getEvents(): Observable<EventOverview[]> {
    console.log('Get all events');
    return this.httpClient.get<EventOverview[]>(this.eventBaseUri);
  }

  getEventsOfTokenSub(): Observable<DetailedEvent[]> {
    console.log('Get all Events of Token sub');
    return this.httpClient.get<DetailedEvent[]>(this.eventBaseUri + '/my');
  }

  /**
   * searches events by given criteria
   * @param event search criterias for events
   */
  searchEvent(event: SearchEvent): Observable<EventOverview[]> {
    console.log('Search events for: ' + JSON.stringify(event));
    const params = new HttpParams()
      .set('title', event.title)
      .set('interestAreaId', String(event.interestAreaId))
      .set('employerId', String(event.employerId))
      .set('payment', String(event.payment))
      .set('start', event.start === '' ? '2000-01-01' : event.start)
      .set('end', event.end === '' ? '2099-12-31' : event.end)
      .set('state', event.state)
      .set('onlyAvailableTasks', String(event.onlyAvailableTasks))
      .set('userId', event.userId === false ? String(0) : String(event.userId));
    return this.httpClient.get<EventOverview[]>(this.eventBaseUri, {params});
  }

  /**
   * Deletes Event and related data
   * @param id of Event to delete
   */
  deleteEvent(id: number) {
   console.log('Delete event with id: ' + id);
   return this.httpClient.delete(this.eventBaseUri + '/' + id);
  }
}
