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

  constructor() { }

  emitUpdatedNotification(notification: SimpleNotification) {
    this.updateSeenNotifications.emit(notification);
  }

  emitNewLoggedInUser(bool: boolean) {
    this.newLoggedInUser.emit(bool);
  }
}
