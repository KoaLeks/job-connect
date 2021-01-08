import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ContactMessage} from '../../dtos/contact-message';
import {AlertService} from '../../alert';
import {AuthService} from '../../services/auth.service';
import {ProfileService} from '../../services/profile.service';
import {SimpleEmployee} from '../../dtos/simple-employee';
import {SimpleEmployer} from '../../dtos/simple-employer';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent implements OnInit {
  @Input() receiver: SimpleEmployee | SimpleEmployer;
  contactForm: FormGroup;
  submitted: boolean = false;
  pattern = '[a-zA-ZÖöÜüÄä]+([ ]|[a-zA-ZÖöÜüÄä]|[0-9]|[.]|[,]|[(]|[)]|[-]|[/]|[^\'\u0027])*';

  constructor(private formBuilder: FormBuilder, private profileService: ProfileService, private alertService: AlertService,
              private authService: AuthService) {
    this.contactForm = this.formBuilder.group({
      subject: ['Frage bezüglich ...', [Validators.required, Validators.pattern(this.pattern)]],
      message: ['Sehr geehrte Frau/geehrter Herr ... \n\n...\n\nFür Rückfragen wenden Sie sich bitte an '
      + this.authService.getTokenIdentifier(), [Validators.required, Validators.pattern(this.pattern)]]
    });
  }

  ngOnInit(): void {
  }

  sendMessage(): void {
    this.submitted = true;
    if (this.contactForm.valid) {
      this.alertService.success('Nachricht gesendet', {autoClose: true});
      const contactMessage: ContactMessage = new ContactMessage(this.receiver.simpleProfileDto.id, this.contactForm.controls.subject.value,
        this.contactForm.controls.message.value);
      this.profileService.contact(contactMessage).subscribe(
        () => {
          console.log('mail sent');
        }, error => {
          console.log('mail not sent');
        }
      );
      this.resetForm();
    }
  }

  /**
   * Resets Form
   */
  resetForm(): void {
    this.submitted = false;
    this.contactForm.controls.subject.setValue('Frage bezüglich ...');
    this.contactForm.controls.message.setValue('Sehr geehrte Frau/geehrter Herr ... \n\n...\n\nFür Rückfragen wenden Sie sich bitte an '
      + this.authService.getTokenIdentifier());
  }

  /**
   * Checks if the receiver is an employee
   */
  isEmployee(): boolean {
    return this.receiver instanceof SimpleEmployee;
  }
}
