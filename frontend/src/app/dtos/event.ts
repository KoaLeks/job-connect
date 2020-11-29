import {Address} from './address';

export class Event {
  constructor(
    public id: number,
    public start: string,
    public end: string,
    public description: string,
    public employer: string,
    public address: Address) {
  }
}
