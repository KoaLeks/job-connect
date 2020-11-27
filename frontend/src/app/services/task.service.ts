import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import { Task } from '../dtos/task';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private taskBaseUri: string = this.globals.backendUri + '/tasks';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Persists tasks to the backend
   * @param task to persist
   */
  createTask(task: Task): Observable<Task> {
    console.log('Create task: ' + JSON.stringify(task));
    return this.httpClient.post<Task>(this.taskBaseUri, task);
  }
}
