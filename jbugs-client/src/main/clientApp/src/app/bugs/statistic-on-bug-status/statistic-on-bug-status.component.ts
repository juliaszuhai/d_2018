import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {BugListService} from "../bugs.service";

@Component({
  selector: 'app-statistic-on-bug-status',
  templateUrl: './statistic-on-bug-status.component.html',
  styleUrls: ['./statistic-on-bug-status.component.css']
})
export class StatisticOnBugStatusComponent implements OnInit {

  @ViewChild('baseChart')
  element: ElementRef;

  public doughnutChartLabels: string[] = ['NEW', 'IN_PROGRESS', 'FIXED', 'CLOSED', 'REJECTED', 'INFO_NEEDED'];
  public doughnutChartData: number[] = [];
  public doughnutChartType: string = 'doughnut';

  constructor(private bugService: BugListService) {
  }


  ngOnInit() {
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

  // protected updateBidDistributionStat()
  // {
  //   this.doughnutChartLabels.length = 0;
  //   this.doughnutChartData.length   = 0;
  //   this.doughtnutBackgroundColor.length = 0;
  //
  //   var chartObject = {};
  //
  //   // Build Chart Object Labels/Data.
  //   this.auction.getBids().forEach(function(bidObject)
  //   {
  //     if(typeof chartObject[bidObject.getTeam().getAbbreviation()] === 'undefined')
  //     {
  //       chartObject[bidObject.getTeam().getAbbreviation()]       = {};
  //       chartObject[bidObject.getTeam().getAbbreviation()].value = 0;
  //       chartObject[bidObject.getTeam().getAbbreviation()].color = bidObject.getTeam().getPrimaryColor();
  //     }
  //
  //     chartObject[bidObject.getTeam().getAbbreviation()].value++;
  //   });
  //
  //   this.bidDistributionStatLabels = Object.keys(chartObject);
  //
  //   // Init colors
  //   var colorObject = {
  //     backgroundColor: []
  //   };
  //
  //   for (var team in chartObject)
  //   {
  //     this.bidDistributionStatData.push(chartObject[team].value);
  //     colorObject.backgroundColor.push(chartObject[team].color);
  //   }
  //
  //   this.bidDistributionStatColors.push(colorObject);
  // }
  //
  // chartClicked(e: any): void
  // {
  //   console.log('chart clicked!');
  //
  //   this.updateBidDistributionStat();
  // }

}
