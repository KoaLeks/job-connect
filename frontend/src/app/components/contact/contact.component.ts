import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ContactMessage} from '../../dtos/contact-message';
import {EmployeeService} from '../../services/employee.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent implements OnInit {
  contactForm: FormGroup;
  submitted: boolean = false;


  constructor(private formBuilder: FormBuilder, private employeeService: EmployeeService) {
    this.contactForm = this.formBuilder.group({
      subject: ['', [Validators.required]],
      message: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
  }

  sendMessage(): void {
    this.submitted = true;
    if (this.contactForm.valid) {
      const contactMessage: ContactMessage = new ContactMessage(97, this.contactForm.controls.subject.value,
        this.contactForm.controls.message.value);
      this.employeeService.contact(contactMessage);
    }
  }

  /**
   * Clears Form
   */
  clearForm(): void {
    this.submitted = false;
    this.contactForm.reset();
  }

}
