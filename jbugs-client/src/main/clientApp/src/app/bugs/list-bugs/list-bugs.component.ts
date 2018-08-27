import {Component, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import {BugData, BugListService, RelatedUser} from "../bugs.service";
import {DataSource} from '@angular/cdk/table';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatDialogConfig} from '@angular/material';
import {BugsPopupComponent} from "../bugs-popup/bugs-popup.component";
import {MatChipsModule} from '@angular/material/chips';
import {AddBugComponent} from "../add-bug/add-bug.component";
import {HttpParams} from "@angular/common/http";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-list-bugs',
  templateUrl: './list-bugs.component.html',
  styleUrls: ['./list-bugs.component.css']
})
export class ListBugsComponent implements OnInit {

  selectedTitles: string;

  bugData: BugData;
  relatedUser: RelatedUser;
  bugList: BugData[];
  listId: number[] = [];
  forExcel: number[] = [];

  constructor(private bugService: BugListService,
              public dialog: MatDialog,
              private translate: TranslateService) {


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
      assignedTo: null
    };

    this.relatedUser = {
      id: 0,
      username: '',


    };


  }

  openDialog(bug: BugData): void {

    const dialogRef = this.dialog.open(BugsPopupComponent, {
      width: '250px',
      data: {description: bug.description}
    });

  }


  ngOnInit() {

    this.bugService.getBugsFromServer().subscribe(
      {
        next: (value: BugData[]) => {
          console.log('received: ' + JSON.stringify(value));
          this.bugList = value;
        }
      }
    );

  }


  filter(title: string, description: string, status: string, severity: string) {
    this.bugService.filter(title, description, status, severity).subscribe(
      {
        next: (value: BugData[]) => {
          console.log('received: ' + JSON.stringify(value));
          this.bugList = value;
        }
      }
    );
  }

  sort(title, version) {
    this.bugService.sort(title, version).subscribe(
      {
        next: (value: BugData[]) => {
          console.log('received: ' + JSON.stringify(value));
          this.bugList = value;
        }
      }
    );
  }

  // addId(id){
  //   this.listId.push(id);
  // }
  onChangeCheck(bug: BugData, checked: boolean) {
    if (checked) {
      this.forExcel.push(bug.id);
    } else {
      this.forExcel.splice(this.forExcel.indexOf(bug.id), 1);
    }
    this.updateExcelLink()
  }

  updateExcelLink() {
    let httpParams = new HttpParams();
    this.forExcel.forEach(value => httpParams = httpParams.append("titles", value.toString()));
    this.selectedTitles = httpParams.toString();

  }



  getDate(d) {

    const correctSec = d * 1000;
    var expiresAt = new Date(correctSec);

    return expiresAt
  }


  openAddBug() {
    const dialogRef2 = this.dialog.open(AddBugComponent, {
      width: '700px',
      data: {bugService: this.bugService}
    });
  }
}
