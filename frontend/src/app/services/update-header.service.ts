import { Injectable } from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class UpdateHeaderService {
  public updateProfile: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor() { }
}
