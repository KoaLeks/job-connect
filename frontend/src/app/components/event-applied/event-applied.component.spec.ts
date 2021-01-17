import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventAppliedComponent } from './event-applied.component';

describe('EventAppliedComponent', () => {
  let component: EventAppliedComponent;
  let fixture: ComponentFixture<EventAppliedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EventAppliedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EventAppliedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
