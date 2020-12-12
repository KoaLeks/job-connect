import {Injectable} from '@angular/core';
import {Globals} from '../global/globals';
import {HttpClient} from '@angular/common/http';
import {EditEmployee} from '../dtos/edit-employee';
import {Observable} from 'rxjs';
import {Application} from '../dtos/application';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {
  private employeeBaseUri: string = this.globals.backendUri + '/applications';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  applyTask(application: Application) {
    console.log('PUT application for Task ' + application);
    return this.httpClient.put<Application>(this.employeeBaseUri + '/apply', application);;
  }
}
