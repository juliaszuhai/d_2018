import { LanguageModule } from './language.module';

describe('LanguageModule', () => {
  let translatorModule: LanguageModule;

  beforeEach(() => {
    translatorModule = new LanguageModule();
  });

  it('should create an instance', () => {
    expect(translatorModule).toBeTruthy();
  });
});
