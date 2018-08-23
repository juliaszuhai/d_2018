import { Component, OnInit } from '@angular/core';
import {BugData, BugListService, RelatedUser} from "../bugs.service";
import {MatDatepickerContent, MatDatepickerModule} from '@angular/material/datepicker';

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
  constructor(private bugService: BugListService) {
    this.bugData={
     title:'',
      description:'',
      version:'',
      fixedVersion:'',
      targetDate: new Date(''),
      status:'NEW',
      severity:'',
      createdByUser: {id:4,username:bugService.getLoggedUserName()},
      assignedTo: {id:4,username:''}

    }
  }

  ngOnInit() {
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
