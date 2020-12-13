import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Notification} from '../dtos/notification';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private profileBaseUri: string = this.globals.backendUri + '/notifications';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  public getAllNotifications() {
    return this.httpClient.get<Notification[]>(this.profileBaseUri);
  }
}
