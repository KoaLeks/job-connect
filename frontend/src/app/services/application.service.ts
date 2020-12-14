import {Injectable} from '@angular/core';
import {Globals} from '../global/globals';
import {HttpClient} from '@angular/common/http';
import {Application} from '../dtos/application';
import {ApplicationStatus} from '../dtos/application-status';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {
  private applicationBaseUri: string = this.globals.backendUri + '/applications';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  applyTask(application: Application) {
    console.log('PUT application for Task ' + JSON.stringify(application));
    return this.httpClient.put<Application>(this.applicationBaseUri + '/apply', application);;
  }

  changeApplicationStatus(applicationStatus: ApplicationStatus) {
    console.log('POST applicationStatus ' + JSON.stringify(applicationStatus));
    return this.httpClient.post(this.applicationBaseUri + '/changeStatus', applicationStatus);
  }

}
