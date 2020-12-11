import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {EmployeeService} from '../../services/employee.service';
import {SimpleEmployee} from '../../dtos/simple-employee';

@Component({
  selector: 'app-employee-overview',
  templateUrl: './employee-overview.component.html',
  styleUrls: ['./employee-overview.component.scss']
})
export class EmployeeOverviewComponent implements OnInit {
  employees: SimpleEmployee[] = [];
  error: boolean = false;
  errorMessage: string = '';

  // Pagination
  page = 1;
  pageSize = 10;
  collectionSize;
  pageEmployees: SimpleEmployee[];

  constructor(private authService: AuthService, private router: Router, private employeeService: EmployeeService) {
  }

  ngOnInit(): void {
    if (this.authService.getUserRole() !== 'EMPLOYER') {
      this.router.navigate(['']);
    }

    this.loadEmployees();
  }

  private loadEmployees() {
    this.employeeService.findAll().subscribe(
      (employees: SimpleEmployee[]) => {
        this.employees = employees;
        this.collectionSize = this.employees.length;
        this.refreshEmployees();
      }, error => {
        this.defaultServiceErrorHandling(error);
      });
  }

  public refreshEmployees() {
    this.pageEmployees = this.employees
      .map((employee, i) => ({id: i + 1, ...employee}))
      .slice((this.page - 1) * this.pageSize, (this.page - 1) * this.pageSize + this.pageSize);
    console.log('refresh');
  }

  private defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (typeof error.error === 'object') {
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error;
    }
  }

  public arrayBufferToBase64(buffer): String {
    let binary = '';
    const bytes = new Uint8Array(buffer);
    const len = bytes.byteLength;
    for (let i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    return window.btoa(binary);
  }
}
