import {Component, Inject, OnInit} from '@angular/core';
import {UserManagementComponent} from "../user-management/user-management.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {TranslatorService} from "../../translator/translator.service";

@Component({
  selector: 'app-deactivation-popup',
  templateUrl: './deactivation-popup.component.html',
  styleUrls: ['./deactivation-popup.component.css']
})
export class DeactivationPopupComponent implements OnInit {

  constructor(
    public translatorService: TranslatorService,
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
