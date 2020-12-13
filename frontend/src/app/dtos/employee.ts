import {Gender} from './gender.enum';
import {TimeDto} from './TimeDto';

export class Employee {
  constructor(
    public firstName: string,
    public lastName: string,
    public email: string,
    public password: string,
    public publicInfo: string,
    public gender: Gender,
    public birthDate: Date,
    public times: TimeDto[]
  ) {}
}
