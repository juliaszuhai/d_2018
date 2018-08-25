import {PermissionManagementModule} from './permission-management.module';

describe('PermissionManagementModule', () => {
  let permissionManagementModule: PermissionManagementModule;

  beforeEach(() => {
    permissionManagementModule = new PermissionManagementModule();
  });

  it('should create an instance', () => {
    expect(permissionManagementModule).toBeTruthy();
  });
});
