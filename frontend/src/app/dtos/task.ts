import {InterestArea} from './interestArea';
import {EmployeeTasks} from './employee-tasks';

export class Task {
  constructor(
    public id: number,
    public description: string,
    public employeeCount: number,
    public paymentHourly: number,
    public eventId: number,
    public employees: EmployeeTasks[],
    public interestArea: InterestArea) {
  }
}
