import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {EditEmployer} from '../dtos/edit-employer';

@Injectable({
  providedIn: 'root'
})
export class EmployerService {

  private employerBaseUri: string = this.globals.backendUri + '/profiles/employer';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Loads an employer from the backend using the email
   * @param email to look for
   */
  getEmployerByEmail(email: String) {
    console.log('Get employer by email ' + email);
    return this.httpClient.get<any>(this.employerBaseUri + '/' + email);
  }

  /**
   * Updates an employer with the given details
   * @param employer to update
   */
  updateEmployer(employer: EditEmployer) {
    console.log('Update employer profile');
    return this.httpClient.put(this.employerBaseUri, employer);
  }
}
