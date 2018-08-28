import {inject, TestBed} from '@angular/core/testing';

import {BugsGuard} from './bugs.guard';

describe('BugsGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BugsGuard]
    });
  });

  it('should ...', inject([BugsGuard], (guard: BugsGuard) => {
    expect(guard).toBeTruthy();
  }));
});
