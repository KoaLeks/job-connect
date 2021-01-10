import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {EmployeeService} from '../../services/employee.service';
import {SimpleEmployee} from '../../dtos/simple-employee';

@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.scss']
})
export class EmployeeDetailsComponent implements OnInit {
  id: number;
  employee: SimpleEmployee;
  hasPicture: boolean;
  picture;
  age: Number;
  interestAreasDist: Set<String> = new Set<String>();

  constructor(private route: ActivatedRoute, private employeeService: EmployeeService) {
    this.route.params.subscribe(params => {
      this.id = params.id;
    });
  }

  ngOnInit(): void {
    this.getEmployeeDetails();
  }

  private getEmployeeDetails() {
    this.employeeService.getEmployeeById(this.id).subscribe(
      (simpleEmployee: SimpleEmployee) => {
        this.employee = simpleEmployee;
        // profile picture
        this.arrayBufferToBase64(this.employee.simpleProfileDto.picture);
        if (this.employee.simpleProfileDto.picture != null) {
          this.picture = 'data:image/png;base64,' + this.picture;
          this.hasPicture = true;
        }
        // age
        const timeDiff = Math.abs(Date.now() - new Date(this.employee.birthDate).getTime());
        this.age = Math.floor((timeDiff / (1000 * 3600 * 24)) / 365.25);
        // distinct interest areas
        if (this.employee.interestDtos !== null ) {
          for (let i = 0; i < this.employee.interestDtos.length; i++) {
            if (this.employee.interestDtos[i].simpleInterestAreaDto !== null) {
              this.interestAreasDist.add(this.employee.interestDtos[i].simpleInterestAreaDto.area);
            }
          }
        }
      }
    );
  }

  arrayBufferToBase64(buffer) {
    let binary = '';
    const bytes = new Uint8Array(buffer);
    const len = bytes.byteLength;
    for (let i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    this.picture = window.btoa(binary);
  }

}
