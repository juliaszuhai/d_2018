import { TranslatorModule } from './translator.module';

describe('TranslatorModule', () => {
  let translatorModule: TranslatorModule;

  beforeEach(() => {
    translatorModule = new TranslatorModule();
  });

  it('should create an instance', () => {
    expect(translatorModule).toBeTruthy();
  });
});
