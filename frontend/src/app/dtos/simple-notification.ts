import {Event} from './event';
import {SimpleEmployee} from './simple-employee';

export class SimpleNotification {
  constructor(
    public message: string,
    public type: string,
    public seen: boolean,
    public event: Event,
    public employee: SimpleEmployee
  ) {
  }
}
