import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {BugListService} from "../bugs.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-statistic-on-bug-status',
  templateUrl: './statistic-on-bug-status.component.html',
  styleUrls: ['./statistic-on-bug-status.component.css']
})
export class StatisticOnBugStatusComponent implements OnInit {

  @ViewChild('baseChart')
  element: ElementRef;

  labelNew: string;
  labelProgress: string;
  labelFixed: string;
  labelClosed: string;
  labelRejected: string;
  labelInfo: string;


  public doughnutChartLabels: string[] = [];
  public doughnutChartData: number[] = [];
  public doughnutChartType: string = 'doughnut';

  constructor(private bugService: BugListService, private translate: TranslateService) {

  }

  getLabels() {
    this.translate.get('statistics.new').subscribe((res: string) => this.doughnutChartLabels[0] = res);
    this.translate.get('statistics.inProgress').subscribe((res: string) => this.doughnutChartLabels[1] = res);
    this.translate.get('statistics.fixed').subscribe((res: string) => this.doughnutChartLabels[2] = res);
    this.translate.get('statistics.closed').subscribe((res: string) => this.doughnutChartLabels[3] = res);
    this.translate.get('statistics.rejected').subscribe((res: string) => this.doughnutChartLabels[4] = res);
    this.translate.get('statistics.infoNeeded').subscribe((res: string) => this.doughnutChartLabels[5] = res);

    return this.doughnutChartLabels;
  }

  ngOnInit() {

    console.log(this.doughnutChartLabels);
    this.getLabels();
    this.getStatistic();
  }

  getStatistic() {
    this.bugService.countBugsByStatus("NEW")
      .subscribe(data => {
        this.doughnutChartData[0] = data;
      });

    this.bugService.countBugsByStatus("IN_PROGRESS")
      .subscribe(data => {
        this.doughnutChartData[1] = data;
      });
    this.bugService.countBugsByStatus("FIXED")
      .subscribe(data => {
        this.doughnutChartData[2] = data;
      });
    this.bugService.countBugsByStatus("CLOSED")
      .subscribe(data => {
        this.doughnutChartData[3] = data;
      });
    this.bugService.countBugsByStatus("REJECTED")
      .subscribe(data => {
        this.doughnutChartData[4] = data;
      });
    this.bugService.countBugsByStatus("INFO_NEEDED")
      .subscribe(data => {
        this.doughnutChartData[5] = data;
      });
  }

  // events
  public chartClicked(e: any): void {
    console.log(e);
  }

  public chartHovered(e: any): void {
    console.log(e);
  }


}
