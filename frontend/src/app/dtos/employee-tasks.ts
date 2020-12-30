import {SimpleEmployee} from './simple-employee';

export class EmployeeTasks {
  constructor(
    public id: number,
    public employee: SimpleEmployee,
    public taskId: number,
    public accepted: boolean
  ) {}
}
