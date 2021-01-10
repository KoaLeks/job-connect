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
  getEmployerByEmail() {
    console.log('Get employer by email ');
    return this.httpClient.get<any>(this.employerBaseUri);
  }

  /**
   * Updates an employer with the given details
   * @param employer to update
   */
  updateEmployer(employer: EditEmployer) {
    console.log('Update employer profile');
    return this.httpClient.put(this.employerBaseUri, JSON.parse(JSON.stringify(employer).replace('profileDto', 'editProfileDto')));
  }
}
