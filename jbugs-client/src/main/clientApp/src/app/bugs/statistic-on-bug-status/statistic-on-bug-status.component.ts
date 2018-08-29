import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {BugListService} from "../bugs.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-statistic-on-bug-status',
  templateUrl: './statistic-on-bug-status.component.html',
  styleUrls: ['./statistic-on-bug-status.component.css']
})
export class StatisticOnBugStatusComponent implements OnInit {

  countNewBug: number = 0
  countInProgressBug: number = 0
  countFixedBug: Number = 0;
  countClosedBug: Number = 0;
  countRejectedBug: Number = 0;
  countInfoBug: Number = 0;
  // Doughnut
  public doughnutChartLabels: string[] = ['NEW', 'IN_PROGRESS', 'FIXED', 'CLOSED', 'REJECTED', 'INFO_NEEDED'];
  public doughnutChartData: number[] = [200, 450, 100];
  public doughnutChartType: string = 'doughnut';

  constructor(private bugService: BugListService,
              private route: ActivatedRoute,
              private changeDetectorRefs: ChangeDetectorRef) {
  }

  ngOnInit() {

    this.getStatistic();
  }

  getStatistic() {
    this.bugService.countBugsByStatus("NEW")
      .subscribe(data => this.countNewBug = data);
    console.log(this.countNewBug)
    this.bugService.countBugsByStatus("IN_PROGRESS")
      .subscribe(data => this.countInProgressBug = data);
    this.bugService.countBugsByStatus("FIXED")
      .subscribe(data => this.countFixedBug = data);
    this.bugService.countBugsByStatus("CLOSED")
      .subscribe(data => this.countClosedBug = data);
    this.bugService.countBugsByStatus("REJECTED")
      .subscribe(data => this.countRejectedBug = data);
    this.bugService.countBugsByStatus("INFO_NEEDED")
      .subscribe(data => this.countInfoBug = data);
  }

  // events
  public chartClicked(e: any): void {
    console.log(e);
  }

  public chartHovered(e: any): void {
    console.log(e);
  }

}
