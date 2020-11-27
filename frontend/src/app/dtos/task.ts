import {Event} from './event';

export class Task {
  constructor(
    public description: string,
    public employeeCount: number,
    public paymentHourly: number,
    public event: Event,
    public employees: string,
    public interestArea: string) {
  }
}
