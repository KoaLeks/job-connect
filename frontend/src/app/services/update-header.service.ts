import {EventEmitter, Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {SimpleNotification} from '../dtos/simple-notification';

@Injectable({
  providedIn: 'root'
})

export class UpdateHeaderService {
  public updateProfile: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public updateSeenNotifications: EventEmitter<SimpleNotification> = new EventEmitter<SimpleNotification>();

  constructor() { }

  emitUpdatedNotification(notification: SimpleNotification) {
    this.updateSeenNotifications.emit(notification);
  }
}
