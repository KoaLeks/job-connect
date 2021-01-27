import {Injectable} from '@angular/core';
import {Globals} from '../global/globals';
import {HttpClient} from '@angular/common/http';
import {Application} from '../dtos/application';
import {ApplicationStatus} from '../dtos/application-status';
import {SimpleNotification} from '../dtos/simple-notification';
import {Observable} from 'rxjs';
import {DetailedEvent} from '../dtos/detailed-event';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {
  private applicationBaseUri: string = this.globals.backendUri + '/applications';
  constructor(private httpClient: HttpClient, private globals: Globals) { }

  /**
   * Send application for specific task to an employer
   * @application message and task
   */
  applyTask(application: Application) {
    console.log('PUT application for Task ' + JSON.stringify(application));
    return this.httpClient.put<Application>(this.applicationBaseUri + '/apply', application);
  }

  /**
   * Accept/Decline Application
   * @param applicationStatus updated application status
   */
  changeApplicationStatus(applicationStatus: ApplicationStatus) {
    console.log('POST applicationStatus ' + JSON.stringify(applicationStatus));
    return this.httpClient.post(this.applicationBaseUri + '/changeStatus', applicationStatus);
  }

  /**
   * @param id of event
   * @return all applications for one event
   */
  getApplicationsForEvent(id: number) {
    console.log('GET applications for event with id=' + id);
    return this.httpClient.get<SimpleNotification[]>(this.applicationBaseUri + '/events/' + id);
  }

  /**
   * @return all events one employee applied to
   */
  getAppliedEvents(): Observable<DetailedEvent[]> {
    console.log('GET events where employee applied for');
    return this.httpClient.get<DetailedEvent[]>(this.applicationBaseUri + '/applied');
  }

  /**
   * Deletes one application
   * @param id of the application to delete
   */
  deleteApplication(id: number) {
    console.log('DELETE awaiting application');
    return this.httpClient.delete(this.applicationBaseUri + '/' + id);
  }

  /**
   * Removes an accepted employee from a task
   * @param id of the task
   */
  deleteJob(id: number) {
    console.log('Delete accepted application');
    return this.httpClient.delete(this.applicationBaseUri + '/task/' + id);
  }
}
