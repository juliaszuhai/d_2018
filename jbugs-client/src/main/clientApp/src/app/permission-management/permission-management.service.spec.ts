import {inject, TestBed} from '@angular/core/testing';

import {PermissionManagementService} from './permission-management.service';

describe('PermissionManagementService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PermissionManagementService]
    });
  });

  it('should be created', inject([PermissionManagementService], (service: PermissionManagementService) => {
    expect(service).toBeTruthy();
  }));
});
