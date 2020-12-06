import { TestBed } from '@angular/core/testing';

import { UpdateHeaderService } from './update-header.service';

describe('UpdateHeaderService', () => {
  let service: UpdateHeaderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UpdateHeaderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
