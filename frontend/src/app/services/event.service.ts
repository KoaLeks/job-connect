import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';
import {Event} from '../dtos/event';
import {DetailedEvent} from '../dtos/detailed-event';

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
   * Updates event in the backend
   * @param event to update
   */
  updateEvent(event: Event): Observable<Event> {
    console.log('Update event: ' + JSON.stringify(event));
    return this.httpClient.put<Event>(this.eventBaseUri, event);
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
  getEvents(): Observable<DetailedEvent[]> {
    return this.httpClient.get<DetailedEvent[]>(this.eventBaseUri);
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
