import {Component, Inject, OnInit} from '@angular/core';
import {UserManagementComponent} from "../user-management/user-management.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-deactivation-popup',
  templateUrl: './deactivation-popup.component.html',
  styleUrls: ['./deactivation-popup.component.css']
})
export class DeactivationPopupComponent implements OnInit {

  constructor(
    private translate: TranslateService,
    public dialogRef: MatDialogRef<UserManagementComponent>,
    @Inject(MAT_DIALOG_DATA) public data,) {
  }

  okButtonAction() {
    this.dialogRef.close();
  }

  onNoClick(): void {

    this.dialogRef.close();
  }

  ngOnInit() {
  }

}
