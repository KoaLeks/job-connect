import {Gender} from './gender_old';

export class Employee {
  constructor(
    public firstName: string,
    public lastName: string,
    public email: string,
    public password: string,
    public publicInfo: string,
    public gender: Gender
  ) {}
}
