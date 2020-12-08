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
    public tasks: Task[]
  ) {}
}
