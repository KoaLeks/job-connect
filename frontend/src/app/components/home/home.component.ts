import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {FormBuilder} from '@angular/forms';
import {AddressService} from '../../services/address.service';
import {EventService} from '../../services/event.service';
import {Address} from '../../dtos/Address';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  addressCreationForm;
  eventCreationForm;
  address_id: number;

  constructor(public authService: AuthService, private formBuilder: FormBuilder, private addressService: AddressService,
              private eventService: EventService) {
    this.addressCreationForm = this.formBuilder.group({
      city: '',
      state: '',
      zip: '',
      addressLine: '',
      additional: ''
    });
    this.eventCreationForm = this.formBuilder.group({
      start: '',
      end: '',
      description: '',
      address: null,
      employer: null,
      tasks: null
    });
  }

  ngOnInit() {
  }


  /**
   * Saves new Event
   */
  createEvent(event, address) {
    this.addressService.createAddress(address).subscribe(
      (a) => {
        this.address_id = a.id;

        event.address = {
          id: this.address_id,
          city: null,
          state: null,
          zip: null,
          addressLine: null,
          additional: null
        };

        this.eventService.createEvent(event).subscribe(
          () => {
            alert('successfully created!');
          }
        );

      }
    );
  }

}
