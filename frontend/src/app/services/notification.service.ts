import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {SimpleNotification} from '../dtos/simple-notification';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private notificationBaseUri: string = this.globals.backendUri + '/notifications';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getNotifications(): Observable<SimpleNotification[]> {
    console.log('GET notifications');
    return this.httpClient.get<SimpleNotification[]>(this.notificationBaseUri);
  }

  deleteNotification(id: number) {
    console.log('Delete notification with id: ' + id);
    return this.httpClient.delete(this.notificationBaseUri + '/' + id);
  }
}
