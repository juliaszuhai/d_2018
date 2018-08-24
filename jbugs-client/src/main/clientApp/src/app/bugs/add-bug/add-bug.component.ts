import {Component, Inject, OnInit} from '@angular/core';
import {BugData, BugListService, RelatedUser} from "../bugs.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";


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

  error: boolean;
  errorMessage: string;

  constructor(public dialogRef2: MatDialogRef<AddBugComponent>,
               private bugService: BugListService,
              private router: Router) {
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
    this.error = false;
    this.errorMessage = '';
  }



  ngOnInit() {
  }


  displayError() {
    return this.error;
  }

  getMessage(){
    return this.errorMessage;
  }

  onNoClick(): void {
    this.dialogRef2.close();
  }

  validateDescription() {
    return this.bugData.description.length >= 250;
  }

  validateVersion() {
    const regex = new RegExp('([a-zA-Z0-9]+).([a-zA-Z0-9]+).([a-zA-Z0-9]+)');
    return regex.test(this.bugData.version);
  }

  addBugForm() {
    this.bugService.validateBug(this.bugData.title, this.bugData.description,this.bugData.version,this.bugData.fixedVersion,
      this.bugData.targetDate,this.bugData.severity,this.bugData.assignedTo.username,this.bugData.createdByUser.username)
      .subscribe(
        data => {
          this.error = false;
          this.router.navigate([`/bugs`]);
        },
        err => {
          this.error = true;
          if(err.valueOf().error.value=='DESCRIPTION_TOO_SHORT') {
            this.errorMessage = "Description is too short. Write more";
          } else{
            this.errorMessage = "Version syntax is incorrect"
          }
        }
      );
  }
}
