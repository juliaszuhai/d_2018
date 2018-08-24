import {Component, Inject, OnInit} from '@angular/core';
import {BugData, BugListService, RelatedUser} from "../bugs.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";


@Component({
  selector: 'app-add-bug',
  templateUrl: './add-bug.component.html',
  styleUrls: ['./add-bug.component.css']
})
export class AddBugComponent implements OnInit {

  bugData: BugData;
  severities: string[]=[
    "CRITICAL","HIGH","MEDIUM","LOW"
  ];
  constructor(public dialogRef2: MatDialogRef<AddBugComponent>,
               private bugService: BugListService) {
    this.bugData={
      id:1,
      title:'',
      description:'',
      version:'',
      fixedVersion:'',
      targetDate: new Date(''),
      status:'NEW',
      severity:'',
      createdByUser: {id:4,username:this.bugService.getLoggedUserName()},
      assignedTo: {id:4,username:''}

    }
  }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef2.close();
  }

  validateDescription() {
    return true;
  }

  validateVersion() {
    return true;
  }

  addBugForm() {
    this.bugService.validateBug(this.bugData.title, this.bugData.description,this.bugData.version,this.bugData.fixedVersion,
      this.bugData.targetDate,this.bugData.severity,this.bugData.assignedTo.username,this.bugData.createdByUser.username)
      .subscribe(
        data => {
        },
      );
  }
}
