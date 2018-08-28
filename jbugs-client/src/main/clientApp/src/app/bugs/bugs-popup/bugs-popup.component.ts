import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {BugData} from "../bugs.service";
import {TranslateService} from "@ngx-translate/core";
import {BugsPopupService} from "./bugs-popup.service";

@Component({
  selector: 'app-bugs-popup',
  templateUrl: './bugs-popup.component.html',
  styleUrls: ['./bugs-popup.component.css']
})
export class BugsPopupComponent implements OnInit {


description: string;

  constructor(
    private bugPopupService: BugsPopupService,
    private translate: TranslateService,
    public dialogRef: MatDialogRef<BugsPopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: BugData) {
  }

  downloadPdf(id){
    console.log(id);
    this.bugPopupService.getPdf(id);

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
  }

}
