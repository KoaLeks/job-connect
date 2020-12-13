import {ProfileDto} from './profile-dto';

export class Notification {
  constructor(
    public message: string,
    public type: string,
    public seen: boolean,
    public event: Event
  ) {}
}
