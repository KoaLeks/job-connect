import {Event} from './event';
import {InterestArea} from './interestArea';

export class Task {
  constructor(
    public id: number,
    public description: string,
    public employeeCount: number,
    public paymentHourly: number,
    public event: Event,
    public employees: string, // TODO Change to correct dataTypes
    public interestArea: InterestArea) {
  }
}
