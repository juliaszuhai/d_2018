import { BugsModule } from './bugs.module';

describe('BugsModule', () => {
  let bugsModule: BugsModule;

  beforeEach(() => {
    bugsModule = new BugsModule();
  });

  it('should create an instance', () => {
    expect(bugsModule).toBeTruthy();
  });
});
