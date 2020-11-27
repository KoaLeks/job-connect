import {Address} from './Address';

export class Event {
  constructor(
    public start: string,
    public end: string,
    public description: string,
    public employer: string,
    public address: Address,
    public tasks: string) {
  }
}
