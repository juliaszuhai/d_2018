import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {BugData, BugListService} from "../bugs.service";
import {ListBugsComponent} from "../list-bugs/list-bugs.component";
import {TranslateService} from "@ngx-translate/core";
import {MatDialog, MatPaginator, MatTableDataSource, PageEvent} from '@angular/material';


@Component({
  selector: 'app-update-bug',
  templateUrl: './update-bug.component.html',
  styleUrls: ['./update-bug.component.css']
})
export class UpdateBugComponent implements OnInit {

  bugData: BugData;
  severities: string[]=[
    "CRITICAL","HIGH","MEDIUM","LOW"
  ];
  satus: string[] =[
    "NEW","IN_PROGRESS","FIXED","CLOSED","REJECTED","INFO_NEEDED"
  ];
  error: boolean;
  errorMessage: string;

  constructor(  private translate: TranslateService,
               public dialogRef: MatDialogRef<ListBugsComponent>,
               @Inject(MAT_DIALOG_DATA) public data,
               public bugmngmt: BugListService
               //public permissionmngmt: PermissionManagementService
  ) {
    this.error = false;
    this.errorMessage = '';
  }



  ngOnInit() {
  }

  submitUpdateBug() {
    console.log(this.data);
    //this.data.targetDateString = this.data.targetDate.toISOString().slice(0,10);
    //console.log("fuuuck you" + this.data.targetDate)

    this.bugmngmt.updateBug(this.data).subscribe(
      data => {
        this.dialogRef.close();
      }, error => {
        //console.log("Update error:" + error)
        if(error.valueOf().error.value=='DESCRIPTION_TOO_SHORT') {
          this.errorMessage = "Description is too short. Write more";
        } else if(error.valueOf().error.value=='VER'){
          this.errorMessage = "Version syntax is incorrect"
        }
      }
    );

  }

  validateDescription() {
    return this.data.description.length >= 250;
  }

  validateVersion() {
    const regex = new RegExp('([a-zA-Z0-9]+).([a-zA-Z0-9]+).([a-zA-Z0-9]+)');
    return regex.test(this.data.version);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
