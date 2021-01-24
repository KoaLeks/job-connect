import {SimpleEmployer} from './simple-employer';
import {Address} from './address';
import {Task} from './task';
import {SuperSimpleEmployer} from './super-simple-employer';

export class EventOverview {
  constructor(
    public id: number,
    public start: string,
    public end: string,
    public title: string,
    public description: string,
    public employer: SuperSimpleEmployer,
    public address: Address,
    public tasks: Task[],
    public sortHelper: number
  ) {}
}
