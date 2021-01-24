import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Employee} from '../dtos/employee';
import {Employer} from '../dtos/employer';
import {EditPassword} from '../dtos/edit-password';
import {ContactMessage} from '../dtos/contact-message';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private profileBaseUri: string = this.globals.backendUri + '/profiles';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

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

  /**
   * Updates a users password
   * @param passwords contains the email address, an old and a new password
   */
  updatePassword(passwords: EditPassword): Observable<Number> {
    console.log('Update user password');
    return this.httpClient.put<Number>(this.profileBaseUri + '/updatePassword', passwords);
  }

  /**
   * Tells the backend to send a specified email to the employee/r
   * @param contactMessage containing id of the receiver profile, subject and message of the mail
   */
  contact(contactMessage: ContactMessage) {
    console.log('Send contact message to employee');
    return this.httpClient.post<ContactMessage>(this.profileBaseUri + '/contact', contactMessage);
  }
}
