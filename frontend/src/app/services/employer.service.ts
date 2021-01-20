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
   */
  getEmployerByEmail() {
    console.log('Get employer by email');
    return this.httpClient.get<any>(this.employerBaseUri);
  }

  /**
   * Loads an employer from the backend using the id
   * @param id to look for
   */
  getEmployerById(id: number) {
    console.log('Get employer by id ' + id);
    return this.httpClient.get<SimpleEmployer>(this.employerBaseUri + '/' + id + '/details');
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

  /**
   * Deletes an employer
   */
  deleteEmployer() {
    console.log('Delete profile');
    return this.httpClient.delete(this.employerBaseUri);
  }
}
