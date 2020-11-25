import { Injectable } from '@angular/core';
import {Message} from '../dtos/message';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';

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
    return this.httpClient.post<Message>(this.profileBaseUri, profile);
  }
}
