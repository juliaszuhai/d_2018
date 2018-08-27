import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {BugData} from "../bugs.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-bugs-popup',
  templateUrl: './bugs-popup.component.html',
  styleUrls: ['./bugs-popup.component.css']
})
export class BugsPopupComponent implements OnInit {


description: string;

  constructor(
    private translate: TranslateService,
    public dialogRef: MatDialogRef<BugsPopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: BugData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
  }

}
