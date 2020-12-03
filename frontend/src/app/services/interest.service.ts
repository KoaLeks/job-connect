import {Injectable} from '@angular/core';
import {Globals} from '../global/globals';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Interest} from '../dtos/interest';

@Injectable({
  providedIn: 'root'
})
export class InterestService {

  private interestBaseUri: string = this.globals.backendUri + '/interests';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  getInterests(): Observable<Interest[]> {
    return this.httpClient.get<Interest[]>(this.interestBaseUri);
  }
}
