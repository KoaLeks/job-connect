import {Injectable} from '@angular/core';
import {Globals} from '../global/globals';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private employeeBaseUri: string = this.globals.backendUri + '/profiles/employee';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  getEmployeeByEmail(email: String) {
    console.log('Get employee by email ' + email);
    return this.httpClient.get<any>(this.employeeBaseUri + '/' + email);
  }
}
