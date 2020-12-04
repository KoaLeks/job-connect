import {Address} from './address';
import {Task} from './task';
import {Employer} from './employer';

export class Event {
  constructor(
    public id: number,
    public start: string,
    public end: string,
    public title: string,
    public description: string,
    public employer: Employer,
    public address: Address,
    public tasks: Task[]) {
  }
}
