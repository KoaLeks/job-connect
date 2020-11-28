import { Injectable } from '@angular/core';
import {Message} from '../dtos/message';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Employee} from '../dtos/employee';
import {Employer} from '../dtos/employer';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private profileBaseUri: string = this.globals.backendUri + '/profiles';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  /**
   * Persists profile to the backend
   * @param profile to persist
   */
  createProfile(profile: any): Observable<any> {
    console.log('Create user with email ' + profile.email);
    if (profile instanceof Employee) {
      return this.httpClient.post<Employee>(this.profileBaseUri + '/employee', profile);
    } else if (profile instanceof Employer) {
      return this.httpClient.post<Employer>(this.profileBaseUri + '/employer', profile);
    } else {
      console.error('profile object is neither an instance of employee nor employer.');
    }
  }
}
