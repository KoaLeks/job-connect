import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';
import {Address} from '../dtos/address';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private addressBaseUri: string = this.globals.backendUri + '/addresses';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Persists address to the backend
   * @param address to persist
   */
  createAddress(address: Address): Observable<Address> {
    console.log('Create address: ' + JSON.stringify(address));
    return this.httpClient.post<Address>(this.addressBaseUri, address);
  }

  /**
   * Updates an address in the backend
   * @param address to update
   */
  updateAddress(address: Address): Observable<Address> {
    console.log('Update address: ' + JSON.stringify(address));
    return this.httpClient.put<Address>(this.addressBaseUri, address);
  }
}
