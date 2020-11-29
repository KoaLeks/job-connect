import {Injectable} from '@angular/core';
import {Globals} from '../global/globals';
import {HttpClient} from '@angular/common/http';
import {EditEmployee} from '../dtos/edit-employee';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private employeeBaseUri: string = this.globals.backendUri + '/profiles/employee';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Loads an employee from the backend using the email
   * @param email to look for
   */
  getEmployeeByEmail(email: String) {
    console.log('Get employee by email ' + email);
    return this.httpClient.get<any>(this.employeeBaseUri + '/' + email);
  }

  /**
   * Updates an employee with the given details
   * @param employee to update
   */
  updateEmployee(employee: EditEmployee): Observable<Number> {
    console.log('Update employee profile');
    return this.httpClient.put<Number>(this.employeeBaseUri, employee);
  }
}
