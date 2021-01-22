import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {EmployeeService} from '../../services/employee.service';
import {SimpleEmployee} from '../../dtos/simple-employee';
import {SuperSimpleEmployee} from '../../dtos/SuperSimpleEmployee';

@Component({
  selector: 'app-employee-overview',
  templateUrl: './employee-overview.component.html',
  styleUrls: ['./employee-overview.component.scss']
})
export class EmployeeOverviewComponent implements OnInit {
  employees: SuperSimpleEmployee[] = [];
  error: boolean = false;
  errorMessage: string = '';

  // Pagination
  page = 1;
  pageSize = 10;
  collectionSize;
  pageEmployees: SuperSimpleEmployee[];

  constructor(private authService: AuthService, public router: Router, private employeeService: EmployeeService) {
  }

  ngOnInit(): void {
    this.loadEmployees();
  }

  private loadEmployees() {
    this.employeeService.findAll().subscribe(
      (employees: SuperSimpleEmployee[]) => {
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
    console.log(this.pageEmployees);
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

  public getInterestAreas(employee: SuperSimpleEmployee): String[] {
    const interestAreasDist: Set<String> = new Set<String>();
    if (employee.interestDtos !== null) {
      for (let i = 0; i < employee.interestDtos.length; i++) {
        if (employee.interestDtos[i].simpleInterestAreaDto !== null) {
          interestAreasDist.add(employee.interestDtos[i].simpleInterestAreaDto.area);
        }
      }
    }
    return Array.from(interestAreasDist);
  }
}
