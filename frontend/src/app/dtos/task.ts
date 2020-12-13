import {InterestArea} from './interestArea';
import {SimpleEmployee} from './simple-employee';

export class Task {
  constructor(
    public id: number,
    public description: string,
    public employeeCount: number,
    public paymentHourly: number,
    public eventId: number,
    public employees: SimpleEmployee[],
    public interestArea: InterestArea) {
  }
}
