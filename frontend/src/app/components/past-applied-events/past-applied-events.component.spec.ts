import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PastAppliedEventsComponent } from './past-applied-events.component';

describe('PastAppliedEventsComponent', () => {
  let component: PastAppliedEventsComponent;
  let fixture: ComponentFixture<PastAppliedEventsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PastAppliedEventsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PastAppliedEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
