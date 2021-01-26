import {Injectable} from '@angular/core';
import {Globals} from '../global/globals';
import {HttpClient, HttpParams} from '@angular/common/http';
import {EditEmployee} from '../dtos/edit-employee';
import {Observable} from 'rxjs';
import {SimpleEmployee} from '../dtos/simple-employee';
import {DetailedEvent} from '../dtos/detailed-event';
import {FilterEmployees} from '../dtos/filter-employees';
import {FilterEmployeesSmart} from '../dtos/filter-employees-smart';
import {filter} from 'rxjs/operators';
import {SuperSimpleEmployee} from '../dtos/SuperSimpleEmployee';

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
    // console.log('Get employee by email');
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
  findAll(): Observable<SuperSimpleEmployee[]> {
    console.log('Get all employees');
    return this.httpClient.get<SuperSimpleEmployee[]>(this.employeeBaseUri + 's');
  }

  /**
   * Deletes an employee
   */
  deleteEmployee() {
    console.log('Delete profile');
    return this.httpClient.delete(this.employeeBaseUri);
  }

  /**
   * Filter Employees
   */
  filterEmployees(filterEmployees: FilterEmployees): Observable<SuperSimpleEmployee[]> {
    console.log('Filter employee');
    if (filterEmployees.interests === null ) { filterEmployees.interests = []; }
    let interestAreas = '';
    filterEmployees.interests.forEach(x => interestAreas += x.id + ',');
    interestAreas = interestAreas.substring(0, (interestAreas.length - 1));
    let params = new HttpParams();
    if (filterEmployees.interests.length !== 0) { params = params.set('interestAreas', interestAreas); }
    if (filterEmployees.time !== null &&  filterEmployees.time !== '' && filterEmployees.date !== null && filterEmployees.date !== '') {
      params = params.set('startTimes', filterEmployees.date + 'T' + filterEmployees.time); }
    return this.httpClient.get<SuperSimpleEmployee[]>(this.employeeBaseUri + '/filter', {params});
  }

  filterEmployeesSmart(filterEmployees: FilterEmployeesSmart): Observable<SuperSimpleEmployee[]> {
    console.log('Filter employees via Events');
    if (filterEmployees.events === null ) { filterEmployees.events = []; }
    let events = '';
    filterEmployees.events.forEach(x => events += x.id + ',');
    events = events.substring(0, (events.length - 1));
    const params = new HttpParams().set('eventIds', events);
    return this.httpClient.get<SuperSimpleEmployee[]>(this.employeeBaseUri + '/filter/smart', {params});
  }
}
