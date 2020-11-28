import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {InterestArea} from '../dtos/interestArea';

@Injectable({
  providedIn: 'root'
})
export class InterestAreaService {

  private interestAreaBaseUri: string = this.globals.backendUri + '/interestareas';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Loads all interestAreas from the backend
   */
  getInterestAreas(): Observable<InterestArea[]> {
    return this.httpClient.get<InterestArea[]>(this.interestAreaBaseUri);
  }
}
