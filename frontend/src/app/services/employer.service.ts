import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class EmployerService {

  private employerBaseUri: string = this.globals.backendUri + '/profiles/employer';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  getEmployerByEmail(email: String) {
    console.log('Get employer by email ' + email);
    return this.httpClient.get<any>(this.employerBaseUri + '/' + email);
  }
}
