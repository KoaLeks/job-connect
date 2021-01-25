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

  emitDeletedEvent(eventId: number) {
    this.removeEventNotifications.emit(eventId);
  }

  emitUpdatedNotification(notification: SimpleNotification) {
    this.updateSeenNotifications.emit(notification);
  }

  emitNewLoggedInUser(bool: boolean) {
    this.newLoggedInUser.emit(bool);
  }
}
