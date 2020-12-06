import {Gender} from './gender.enum';

export class Employee {
  constructor(
    public firstName: string,
    public lastName: string,
    public email: string,
    public password: string,
    public publicInfo: string,
    public gender: Gender,
    public birthDate: Date
  ) {}
}
