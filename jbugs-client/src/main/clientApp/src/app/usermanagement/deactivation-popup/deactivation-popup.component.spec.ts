import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DeactivationPopupComponent} from './deactivation-popup.component';

describe('DeactivationPopupComponent', () => {
  let component: DeactivationPopupComponent;
  let fixture: ComponentFixture<DeactivationPopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DeactivationPopupComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeactivationPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
