import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ContactMessage} from '../../dtos/contact-message';
import {EmployeeService} from '../../services/employee.service';
import {AlertService} from '../../alert';
import {AuthService} from '../../services/auth.service';
import {Employee} from '../../dtos/employee';
import {SimpleEmployee} from '../../dtos/simple-employee';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent implements OnInit {
  @Input() employee: SimpleEmployee;
  contactForm: FormGroup;
  submitted: boolean = false;
  pattern = '[a-zA-ZÖöÜüÄä]+([ ]|[a-zA-ZÖöÜüÄä]|[0-9]|[.]|[,]|[(]|[)]|[-]|[/]|[^\'\u0027])*';

  constructor(private formBuilder: FormBuilder, private employeeService: EmployeeService, private alertService: AlertService,
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
      this.clearForm();
      const contactMessage: ContactMessage = new ContactMessage(this.employee.simpleProfileDto.id, this.contactForm.controls.subject.value,
        this.contactForm.controls.message.value);
      this.employeeService.contact(contactMessage).subscribe(
        () => {
          console.log('mail sent');
        }, error => {
          console.log('mail not sent');
        }
      );
    }
  }

  /**
   * Clears Form
   */
  clearForm(): void {
    this.submitted = false;
    this.contactForm.controls.subject.setValue('Frage bezüglich ...');
    this.contactForm.controls.message.setValue('Sehr geehrte Frau/geehrter Herr ... \n\n...\n\nFür Rückfragen wenden Sie sich bitte an '
      + this.authService.getTokenIdentifier());
  }

}
