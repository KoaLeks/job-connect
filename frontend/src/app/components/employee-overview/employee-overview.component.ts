import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {EmployeeService} from '../../services/employee.service';
import {SimpleEmployee} from '../../dtos/simple-employee';
import {SuperSimpleEmployee} from '../../dtos/SuperSimpleEmployee';
import {InterestArea} from '../../dtos/interestArea';
import {InterestAreaService} from '../../services/interestArea.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import {EventService} from '../../services/event.service';
import {DetailedEvent} from '../../dtos/detailed-event';
import {FilterEmployees} from '../../dtos/filter-employees';
import {DatePipe} from '@angular/common';
import {FilterEmployeesSmart} from '../../dtos/filter-employees-smart';


@Component({
  selector: 'app-employee-overview',
  templateUrl: './employee-overview.component.html',
  styleUrls: ['./employee-overview.component.scss']
})
export class EmployeeOverviewComponent implements OnInit {
  employees: SuperSimpleEmployee[] = [];
  error: boolean = false;
  errorMessage: string = '';
  employeeFilterForm: FormGroup;
  employeeSmartFilterForm;

  // Pagination
  page = 1;
  pageSize = 10;
  collectionSize;
  interestAreas: InterestArea[];
  pageEmployees: SuperSimpleEmployee[];
  smartFilter: boolean;
  myEvents: DetailedEvent[];

  selectedItems = [];
  selectedItemsSmart = [];
  dropdownSettingsInterests: IDropdownSettings;
  dropdownSettingsEvents: IDropdownSettings;

  constructor(private authService: AuthService, public router: Router,
              private employeeService: EmployeeService,
              private interestAreaService: InterestAreaService,
              private formBuilder: FormBuilder,
              private eventService: EventService,
              public datePipe: DatePipe) {
    this.employeeFilterForm = this.formBuilder.group(
      {
        interests: '',
        date: '',
        time: ''
      }
    );

    this.employeeSmartFilterForm = this.formBuilder.group(
      {
        events: ''
      }
    );
  }

  ngOnInit(): void {
    this.smartFilter = false;
    this.loadEmployees();
    this.loadInterestAreas();
    this.loadMyEvents();

    this.dropdownSettingsInterests = {
      singleSelection: false,
      idField: 'id',
      textField: 'description',
      selectAllText: 'Alle auswählen',
      unSelectAllText: 'Keine auswählen',
      itemsShowLimit: 2,
      allowSearchFilter: true
    };

    this.dropdownSettingsEvents = {
      singleSelection: false,
      idField: 'id',
      textField: 'title',
      selectAllText: 'Alle auswählen',
      unSelectAllText: 'Keine auswählen',
      itemsShowLimit: 2,
      allowSearchFilter: true
    };
  }

  private loadMyEvents() {
    this.eventService.getEventsOfTokenSub().subscribe(
      (events: DetailedEvent[]) => {
        this.myEvents = events;
      }
    );
  }

  private loadInterestAreas() {
    this.interestAreaService.getInterestAreas().subscribe(
      (interests: InterestArea[]) => {
        this.interestAreas = interests;
      }
    );
  }

  private loadEmployees() {
    this.employeeService.findAll().subscribe(
      (employees: SuperSimpleEmployee[]) => {
        this.employees = employees;
        this.refreshEmployees();
      }, error => {
        this.defaultServiceErrorHandling(error);
      });
  }

  public refreshEmployees() {
    this.collectionSize = this.employees.length;
    this.pageEmployees = this.employees
      .map((employee, i) => ({id: i + 1, ...employee}))
      .slice((this.page - 1) * this.pageSize, (this.page - 1) * this.pageSize + this.pageSize);
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

  filterEmployees(filterEmployees: FilterEmployees) {
    this.employeeService.filterEmployees(filterEmployees).subscribe(
      (employees: SuperSimpleEmployee[]) => {
        this.employees = employees;
        this.refreshEmployees();
      }
    );

  }

  changeFilterMode() {
    if (this.myEvents === null || this.myEvents === undefined) {
      this.eventService.getEventsOfTokenSub().subscribe(
        (events: DetailedEvent[]) => {
          this.myEvents = events;
        }
      );
    }
    this.smartFilter = !this.smartFilter;
  }

  setTime() {
    if (this.employeeFilterForm.value.time === null || this.employeeFilterForm.value.time === '') {
      this.employeeFilterForm.value.time = '12:00';
    }
    if (this.employeeFilterForm.value.date === '') { this.employeeFilterForm.value.time = ''; }
  }

  setDate() {
    if (this.employeeFilterForm.value.date === null || this.employeeFilterForm.value.date === '') {
      this.employeeFilterForm.value.date = this.datePipe.transform(Date.now(), 'yyyy-MM-dd');
    }
    if (this.employeeFilterForm.value.time === '') { this.employeeFilterForm.value.date = ''; }
  }

  filterEmployeesSmart(filterEmployeesSmart: FilterEmployeesSmart) {
    this.employeeService.filterEmployeesSmart(filterEmployeesSmart).subscribe(
      (employees: SuperSimpleEmployee[]) => {
        this.employees = employees;
        this.refreshEmployees();
      }
    );
  }

  resetForm() {
    this.employeeFilterForm.reset();
    this.employeeSmartFilterForm.reset();
    this.smartFilter = false;
  }
}
