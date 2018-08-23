import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BugsPopupComponent } from './bugs-popup.component';

describe('BugsPopupComponent', () => {
  let component: BugsPopupComponent;
  let fixture: ComponentFixture<BugsPopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BugsPopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BugsPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
