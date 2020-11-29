import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';
import {Event} from '../dtos/event';

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

  getEvents(): Observable<Event[]> {
    return this.httpClient.get<Event[]>(this.eventBaseUri);
  }
}
