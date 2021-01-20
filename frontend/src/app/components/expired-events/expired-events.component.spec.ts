import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpiredEventsComponent } from './expired-events.component';

describe('ExpiredEventsComponent', () => {
  let component: ExpiredEventsComponent;
  let fixture: ComponentFixture<ExpiredEventsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExpiredEventsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExpiredEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
