import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {BugData, BugListService, RelatedUser} from "../bugs.service";
import {DateAdapter, DateAdapter, MatDialog, MatPaginator, MatTableDataSource, PageEvent} from '@angular/material';
import {BugsPopupComponent} from "../bugs-popup/bugs-popup.component";
import {AddBugComponent} from "../add-bug/add-bug.component";
import {HttpParams} from "@angular/common/http";
import {TranslateService} from "@ngx-translate/core";
import {UpdateBugComponent} from "../update-bug/update-bug.component";
import {ListBugsPipe} from "./list-bugs-pipe";
import {ActivatedRoute} from "@angular/router";
import {DatePipe} from "@angular/common";
import {DateFormat} from "../update-bug/date-format";

@Component({
  selector: 'app-list-bugs',
  templateUrl: './list-bugs.component.html',
  styleUrls: ['./list-bugs.component.css'],
  providers: [ListBugsPipe]
})


export class ListBugsComponent implements OnInit {

  selectedTitles: string;

  bugData: BugData;
  relatedUser: RelatedUser;
  bugList: MatTableDataSource<BugData[]>;


  listId: number[] = [];
  forExcel: number[] = [];
  sorted: Object[] = [];
  sortByTitle: Object = {argument: 'title', order: 'asc'};
  sortByVersion: Object = {argument: 'version', order: 'asc'};

  @ViewChild('filterForm') filterForm;
  id: number;
  private sub: any;
  // MatPaginator Inputs
  pageSize = 10;
  length = 100;
  pageIndex = 0;
  pageSizeOptions: number[] = [5, 10, 25, 100];

  // MatPaginator Output
  pageEvent: PageEvent;

  setPageSizeOptions(setPageSizeOptionsInput: string) {
    this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private bugService: BugListService,
              public dialog: MatDialog,
              private translate: TranslateService,
              private changeDetectorRefs: ChangeDetectorRef,
              private route: ActivatedRoute) {

    this.bugData = {
      id: null,
      title: '',
      description: '',
      version: '',
      targetDate: null,
      status: '',
      fixedVersion: '',
      severity: '',
      createdByUser: null,
      assignedTo: null,
      attachments: []
    };

    this.relatedUser = {
      id: 0,
      username: '',
    };


  }


  displayedColumns: string[] = [
    'number',
    'title',
    'version',
    'targetDate',
    'status',
    'fixedVersion',
    'severity',
    'createdBy',
    'assignedTo',
    'edit',
    'exportExcel'
  ];

  openDialog(bug: BugData): void {

    const dialogRef = this.dialog.open(BugsPopupComponent, {
      width: '250px',
      data: {
        description: bug.description,
        id: bug.id
      }
    });

  }

  toggleSort(attribute: String, isChecked: boolean): void {
    let sortObj = attribute == 'title' ? this.sortByTitle : this.sortByVersion;

    if (isChecked) {
      this.sorted.push(sortObj);
      this.sorted = this.sorted.slice(0);
    } else {
      this.sorted.splice(this.sorted.indexOf(sortObj), 1);
    }
    this.sortDataSource();

  }

  sortDataSource() {
    this.sorted.forEach(arg => {
      this.bugList = this.bugList.data.sort(function (bug1: BugData, bug2: BugData) {
        if (bug1[arg.argument] > bug2[arg.argument]) {
          return arg.order == "asc" ? 1 : -1;
        } else if (bug1[arg.argument] < bug2[arg.argument]) {
          return arg.order == "asc" ? -1 : 1;
        }
        else return 0;
      });
    });
  }


  openUpdateDialog(bug): void {
    const dialogRef = this.dialog.open(UpdateBugComponent, {
      width: '60%',
      data: {
        id: bug.id,
        title: bug.title,
        description: bug.description,
        fixedVersion: bug.fixedVersion,
        version: bug.version,
        targetDate: bug.targetDate,
        statusString: bug.status,
        severityString: bug.severity,
        assignedToString: bug.assignedTo.username,
        createdByUserString: bug.createdByUser.username
      }
    });

    dialogRef.afterClosed().subscribe(result => {

      this.bugService.getBugsFromServer().subscribe(
        {
          next: (value: any[]) => {
            this.bugList = new MatTableDataSource<BugData[]>(value);

            this.sortDataSource();

          }
        }
      );
    });
  }

  ngOnInit() {
    this.bugService.getBugsFromServer().subscribe(
      {
        next: (value: any[]) => {
          this.bugList = new MatTableDataSource<BugData[]>(value);

          this.sortDataSource();

        }
      }
    );
    this.sub = this.route.params.subscribe(params => {
      this.id = +params['id'];
    });
    console.log(this.id);
    if (this.id) {
      this.filter(this.bugData.title, this.bugData.description, this.bugData.status, this.bugData.severity, 0, 1, this.id);
    }

  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }


  filter(title: string, description: string, status: string, severity: string, pageIndex = 0, pageSize = this.pageSize, id: number) {
    this.bugService.filter(title, description, status, severity, pageIndex, pageSize, id).subscribe(
      {
        next: (value: any[]) => {
          this.bugList = new MatTableDataSource<BugData[]>(value);

          this.sortDataSource();
        }
      }
    );
  }

  // sort(title, version) {
  //   this.bugService.sort(title, version).subscribe(
  //     {
  //       next: (value: BugData[]) => {
  //         console.log('received: ' + JSON.stringify(value));
  //         this.bugList = value;
  //       }
  //     }
  //   );
  // }

  // sort(title, version) {
  //   this.bugList.sort(function (bug1: BugData, bug2: BugData) {
  //     if (bug1.title < bug2.title ) return -1;
  //     else if (bug1.title > bug2.title ) return 1;
  //     else return 0;
  //   });
  //
  //
  // }

  // sort(args) {
  //   this.bugList.sort(function (bug1: BugData, bug2: BugData) {
  //     if (bug1[args] < bug2[args]) return -1;
  //     else if (bug1[args] > bug2[args]) return 1;
  //     else return 0;
  //   });
  // }


  onChangeCheck(bug: BugData, checked: boolean) {
    if (checked) {
      this.forExcel.push(bug.id);
    } else {
      this.forExcel.splice(this.forExcel.indexOf(bug.id), 1);
    }
    //this.updateExcelLink()
  }

  updateExcelLink() {
    let httpParams = new HttpParams();
    this.forExcel.forEach(value => httpParams = httpParams.append("titles", value.toString()));
    this.selectedTitles = httpParams.toString();
  }

  downloadExcel() {
    this.bugService.excel(this.forExcel);
  }


  getDate(d) {


    var expiresAt = new Date(d);

    return expiresAt
  }


  openAddBug() {
    const dialogRef2 = this.dialog.open(AddBugComponent, {
      width: '700px',
      data: {bugService: this.bugService}
    });
  }
}
