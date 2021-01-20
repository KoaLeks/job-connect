import {Address} from './address';
import {Task} from './task';
import {SimpleEmployer} from './simple-employer';

export class DetailedEvent {
  constructor(
    public id: number,
    public start: string,
    public end: string,
    public title: string,
    public description: string,
    public employer: SimpleEmployer,
    public address: Address,
    public tasks: Task[],
    public sortHelper: number // default value is null.
    // calling the method "sortEventsByDate()" will give sortHelper a value calculated by 'Date.parse(event.start)'.
    // Date.parse(event.start)' will return the number of milliseconds between January 1, 1970 and 'event.start'.
  ) {}
}
