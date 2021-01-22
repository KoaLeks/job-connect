import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployerEventsComponent } from './employer-events.component';

describe('EmployerEventsComponent', () => {
  let component: EmployerEventsComponent;
  let fixture: ComponentFixture<EmployerEventsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmployerEventsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployerEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
