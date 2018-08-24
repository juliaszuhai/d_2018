import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BugData, BugListService, RelatedUser} from "../bugs.service";
import {DataSource} from '@angular/cdk/table';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatDialogConfig} from '@angular/material';
import {BugsPopupComponent} from "../bugs-popup/bugs-popup.component";
import {MatChipsModule} from '@angular/material/chips';

@Component({
  selector: 'app-list-bugs',
  templateUrl: './list-bugs.component.html',
  styleUrls: ['./list-bugs.component.css']
})
export class ListBugsComponent implements OnInit {

  bugData: BugData;
  relatedUser: RelatedUser;
  bugList: BugData[];

  constructor(private bugService: BugListService, public dialog: MatDialog) {

    this.bugData = {
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

  getDate(d) {

    const correctSec = d * 1000;
    var expiresAt = new Date(correctSec);

    return expiresAt
  }


}
