import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {BugData, BugListService, RelatedUser} from "../bugs.service";
import {MatDialog, MatPaginator, MatTableDataSource} from '@angular/material';
import {BugsPopupComponent} from "../bugs-popup/bugs-popup.component";
import {AddBugComponent} from "../add-bug/add-bug.component";
import {HttpParams} from "@angular/common/http";
import {TranslateService} from "@ngx-translate/core";
import {UpdateBugComponent} from "../update-bug/update-bug.component";
import {ActivatedRoute} from "@angular/router";

// import {DateFormat} from "../update-bug/date-format";

@Component({
  selector: 'app-list-bugs',
  templateUrl: './list-bugs.component.html',
  styleUrls: ['./list-bugs.component.css'],
})


export class ListBugsComponent implements OnInit {

  selectedTitles: string;

  bugData;
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
  length = 14;
  pageIndex = 0;
  pageSizeOptions: number[] = [5, 10, 25, 100];



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
      fileName: '',
    };

    this.relatedUser = {
      id: 0,
      username: '',
    };


  }


  displayedColumns: string[] = [
    // 'number',
    'title',
    // 'version',
    'targetDate',
    'status',
    // 'fixedVersion',
    'severity',
    'createdBy',
    'assignedTo',
    'edit',
    'exportExcel'
  ];

  openDialog(bug: BugData): void {
    const dialogRef = this.dialog.open(BugsPopupComponent, {
      width: '75%',
      data: {
        description: bug.description,
        title: bug.title,
        version: bug.version,
        fixedVersion: bug.fixedVersion,
        status: bug.status,
        severity: bug.severity,
        createdBy: bug.createdByUser.username,
        assignedTo: bug.assignedTo.username,
        targetDate: this.getDate(bug.targetDate).toDateString(),
        id: bug.id,
        fileName: bug.fileName
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
      this.bugList = this.bugList.data.sort(function (bug1, bug2) {

        if (bug1[arg.argument].toLowerCase() > bug2[arg.argument].toLowerCase()) {
          return arg.order == "asc" ? 1 : -1;
        } else if (bug1[arg.argument].toLowerCase() < bug2[arg.argument].toLowerCase()) {
          return arg.order == "asc" ? -1 : 1;
        }
        else return 0;
      });
    });
    console.log(this.sorted);
  }


  openUpdateDialog(bug): void {
    const dialogRef = this.dialog.open(UpdateBugComponent, {
      width: '70%',
      data: {
        id: bug.id,
        title: bug.title,
        description: bug.description,
        fixedVersion: bug.fixedVersion,
        version: bug.version,
        targetDate: new Date(bug.targetDate),
        statusString: bug.status,
        severityString: bug.severity,
        assignedToString: bug.assignedTo.username,
        createdByUserString: bug.createdByUser.username
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.filter(this.bugData.title, this.bugData.description, this.bugData.status, this.bugData.severity, this.pageIndex, this.pageSize, this.id);
    });
  }


  ngOnDestroy() {
    this.sub.unsubscribe();
  }


  filter(title: string, description: string, status: string, severity: string, pageIndex = 0, pageSize = this.pageSize, id: number) {
    this.pageIndex = pageIndex;
    this.bugService.filter(title, description, status, severity, pageIndex, pageSize, id).subscribe(
      {
        next: (value: any[]) => {
          console.log(value);
          this.bugList = new MatTableDataSource<BugData[]>(value['filteredList']);
          this.length = value['actualListSize'];
          this.sortDataSource();
        }
      }
    );
  }




  onChangeCheck(bug: BugData, checked: boolean) {
    if (checked) {
      this.forExcel.push(bug.id);
    } else {
      this.forExcel.splice(this.forExcel.indexOf(bug.id), 1);
    }

  }

  updateExcelLink() {
    let httpParams = new HttpParams();
    this.forExcel.forEach(value => httpParams = httpParams.append("titles", value.toString()));
    this.selectedTitles = httpParams.toString();
  }

  downloadExcel() {
    console.log(this.selectedTitles);
    console.log(this.forExcel);
    this.bugService.excel(this.forExcel);
  }


  getDate(d) {

    //Ce scop are metoda?


    return new Date(d);
  }


  openAddBug() {
    const dialogRef2 = this.dialog.open(AddBugComponent, {
      width: '70%',
      data: {bugService: this.bugService}
    });
    dialogRef2.afterClosed().subscribe(result => {
      this.filter(this.bugData.title, this.bugData.description, this.bugData.status, this.bugData.severity, this.pageIndex, this.pageSize, this.id);
    });
  }

  ngOnInit() {

    this.filter(this.bugData.title, this.bugData.description, this.bugData.status, this.bugData.severity, 0, this.pageSize, this.id);
    this.sub = this.route.params.subscribe(params => {
      this.id = +params['id'];
    });
    if (this.id) {
      this.filter(this.bugData.title, this.bugData.description, this.bugData.status, this.bugData.severity, 0, 1, this.id);
    }


  }
}
