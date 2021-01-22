import {SuperSimpleEmployee} from './SuperSimpleEmployee';

export class EmployeeTasks {
  constructor(
    public id: number,
    public employee: SuperSimpleEmployee,
    public taskId: number,
    public accepted: boolean
  ) {}
}
