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

  /**
   * Gets all notifications from the backend
   */
  getNotifications(): Observable<SimpleNotification[]> {
    console.log('GET notifications');
    return this.httpClient.get<SimpleNotification[]>(this.notificationBaseUri);
  }

  /**
   * Deletes the notification with the given id
   * @param id of the notification to delete
   */
  deleteNotification(id: number) {
    console.log('Delete notification with id: ' + id);
    return this.httpClient.delete(this.notificationBaseUri + '/' + id);
  }

  /**
   * Updates the notification
   * @param notification with the updated values
   */
  updateNotification(notification: SimpleNotification) {
    console.log('Update notification: {}', notification);
    return this.httpClient.put<SimpleNotification>(this.notificationBaseUri + '/' + notification.id, notification);
  }

  /**
   * Changes the favourite boolean param of a notification
   * @param notification with the updated favourite value
   */
  changeFavorite(notification: SimpleNotification) {
    console.log('change notification.favorite: {}', notification.favorite);
    return this.httpClient.put<SimpleNotification>(this.notificationBaseUri + '/changeFavorite', notification);
  }
}
