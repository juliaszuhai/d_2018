import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {StatisticOnBugStatusComponent} from './statistic-on-bug-status.component';

describe('StatisticOnBugStatusComponent', () => {
  let component: StatisticOnBugStatusComponent;
  let fixture: ComponentFixture<StatisticOnBugStatusComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [StatisticOnBugStatusComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatisticOnBugStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
