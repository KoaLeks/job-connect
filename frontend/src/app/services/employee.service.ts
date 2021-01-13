import {Injectable} from '@angular/core';
import {Globals} from '../global/globals';
import {HttpClient} from '@angular/common/http';
import {EditEmployee} from '../dtos/edit-employee';
import {Observable} from 'rxjs';
import {SimpleEmployee} from '../dtos/simple-employee';
import {ContactMessage} from '../dtos/contact-message';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private employeeBaseUri: string = this.globals.backendUri + '/profiles/employee';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Loads an employee from the backend using the email
   */
  getEmployeeByEmail() {
    console.log('Get employee by email ');
    return this.httpClient.get<EditEmployee>(this.employeeBaseUri);
  }

  /**
   * Loads an employee from the backend using the id
   * @param id to look for
   */
  getEmployeeById(id: Number) {
    console.log('Get employee by id ' + id);
    return this.httpClient.get<SimpleEmployee>(this.employeeBaseUri + '/' + id + '/details');
  }


  /**
   * Updates an employee with the given details
   * @param employee to update
   */
  updateEmployee(employee: EditEmployee): Observable<Number> {
    console.log('Update employee profile {}', employee);
    return this.httpClient.put<Number>(this.employeeBaseUri, JSON.parse(JSON.stringify(employee).replace('profileDto', 'editProfileDto')));
  }

  /**
   * Get all employees from the backend
   */
  findAll(): Observable<SimpleEmployee[]> {
    console.log('Get all employees');
    return this.httpClient.get<SimpleEmployee[]>(this.employeeBaseUri + 's');
  }
}
