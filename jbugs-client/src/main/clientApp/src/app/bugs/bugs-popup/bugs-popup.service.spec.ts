import { TestBed, inject } from '@angular/core/testing';

import { BugsPopupService } from './bugs-popup.service';

describe('BugsPopupService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BugsPopupService]
    });
  });

  it('should be created', inject([BugsPopupService], (service: BugsPopupService) => {
    expect(service).toBeTruthy();
  }));
});
