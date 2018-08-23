import { UsermanagementModule } from './usermanagement.module';

describe('UsermanagementModule', () => {
  let usermanagementModule: UsermanagementModule;

  beforeEach(() => {
    usermanagementModule = new UsermanagementModule();
  });

  it('should create an instance', () => {
    expect(usermanagementModule).toBeTruthy();
  });
});
