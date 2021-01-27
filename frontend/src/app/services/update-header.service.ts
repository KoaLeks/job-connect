import {EventEmitter, Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {SimpleNotification} from '../dtos/simple-notification';

@Injectable({
  providedIn: 'root'
})

export class UpdateHeaderService {
  public updateProfile: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public updateSeenNotifications: EventEmitter<SimpleNotification> = new EventEmitter<SimpleNotification>();
  public newLoggedInUser: EventEmitter<boolean> = new EventEmitter<boolean>();
  public removeEventNotifications: EventEmitter<number> = new EventEmitter<number>();

  constructor() { }

  /**
   * Emit id of the deleted event
   * @param eventId id of the deleted event
   */
  emitDeletedEvent(eventId: number) {
    this.removeEventNotifications.emit(eventId);
  }

  /**
   * Emit updated notification
   * @param notification of the task
   */
  emitUpdatedNotification(notification: SimpleNotification) {
    this.updateSeenNotifications.emit(notification);
  }

  /**
   * Emit whether a new user is logged in
   * @param bool: true if a new user is logged in
   */
  emitNewLoggedInUser(bool: boolean) {
    this.newLoggedInUser.emit(bool);
  }
}
