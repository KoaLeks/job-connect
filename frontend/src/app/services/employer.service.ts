import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {EditEmployer} from '../dtos/edit-employer';
import {Observable} from 'rxjs';
import {SimpleEmployer} from '../dtos/simple-employer';

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
   * Get all employers from backend
   */
  getEmployers(): Observable<SimpleEmployer[]> {
    console.log('Get all employers');
    return this.httpClient.get<SimpleEmployer[]>(this.employerBaseUri);
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
