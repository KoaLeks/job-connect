import {Event} from './event';
import {ProfileDto} from './profile-dto';
import {Task} from './task';

export class SimpleNotification {
  constructor(
    public id: number,
    public message: string,
    public type: string,
    public seen: boolean,
    public event: Event,
    public taskId: number,
    public recipient: ProfileDto,
    public sender: ProfileDto,
    public favorite: boolean
  ) {
  }
}
