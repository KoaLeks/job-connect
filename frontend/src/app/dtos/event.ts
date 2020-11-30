import {Address} from './address';
import {Task} from './task';

export class Event {
  constructor(
    public id: number,
    public start: string,
    public end: string,
    public description: string,
    public employer: string,
    public address: Address,
    public tasks: Task[]) {
  }
}
