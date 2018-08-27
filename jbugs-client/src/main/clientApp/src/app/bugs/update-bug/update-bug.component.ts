import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {TranslatorService} from "../../translator/translator.service";
import {UsermanagementService} from "../../usermanagement/usermanagement.service";
import {UserManagementComponent} from "../../usermanagement/user-management/user-management.component";
import {PermissionManagementService} from "../../permission-management/permission-management.service";
import {BugData, BugListService} from "../bugs.service";
import {ListBugsComponent} from "../list-bugs/list-bugs.component";

@Component({
  selector: 'app-update-bug',
  templateUrl: './update-bug.component.html',
  styleUrls: ['./update-bug.component.css']
})
export class UpdateBugComponent implements OnInit {

  constructor( public translatorService: TranslatorService,
               public dialogRef: MatDialogRef<ListBugsComponent>,
               @Inject(MAT_DIALOG_DATA) public data: BugData,
               public bugmngmt: BugListService
               //public permissionmngmt: PermissionManagementService
  ) { }

  ngOnInit() {
  }

  submitUpdate() {
    console.log(this.data);
    this.bugmngmt.updateBug(this.data).subscribe(
      data => {
        this.dialogRef.close();
      }, error => {
        console.log("Update error:" + error)
      }
    );

  }

}
