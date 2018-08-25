import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RolesAndPermissionsViewComponent} from './roles-and-permissions-view.component';

describe('RolesAndPermissionsViewComponent', () => {
  let component: RolesAndPermissionsViewComponent;
  let fixture: ComponentFixture<RolesAndPermissionsViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RolesAndPermissionsViewComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RolesAndPermissionsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
