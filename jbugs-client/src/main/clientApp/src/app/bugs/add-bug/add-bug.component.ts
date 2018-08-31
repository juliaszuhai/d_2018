import {Component, OnInit} from '@angular/core';
import {BugData, BugListService} from "../bugs.service";
import {MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";


@Component({
  selector: 'app-add-bug',
  templateUrl: './add-bug.component.html',
  styleUrls: ['./add-bug.component.css']
})
export class AddBugComponent implements OnInit {

  bugData: BugData;
  severities: string[] = [
    "CRITICAL", "HIGH", "MEDIUM", "LOW"
  ];
  fileUploaded: string;
  attachment: File;
  error: boolean;
  errorMessage: string;


  constructor(public dialogRef2: MatDialogRef<AddBugComponent>,
              private bugService: BugListService,
              private router: Router,
              private translate: TranslateService
  ) {
    this.bugData = {
      id: null,
      title: '',
      description: '',
      version: '',
      fixedVersion: '',
      targetDate: new Date(''),
      status: 'NEW',
      severity: '',
      createdByUser: {id: 4, username: bugService.getLoggedUserName()},
      assignedTo: {id: 4, username: ''},
      fileName: null

    }
    this.error = false;
    this.errorMessage = '';

  }

  ngOnInit() {
  }


  displayError() {
    return this.error;
  }

  getMessage() {
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
    this.bugService.validateBug(this.bugData)
      .subscribe(
        data => {
          if (this.attachment != null)
            this.bugService.addFile(data, this.attachment).subscribe(
              data => {
                this.error = false;
              }
            )

        },
        err => {
          this.error = true;
          if (err.valueOf().error.value == 'DESCRIPTION_TOO_SHORT') {
            this.errorMessage = "Description is too short. Write more";
          } else if (err.valueOf().error.value == 'VERSION_NOT_VALID') {
            this.errorMessage = "Version syntax is incorrect";
          }
          else {
            this.errorMessage = "nu merge";
          }
        }
      );
  }

  parseFile($event) {
    let eventTarget = <HTMLInputElement>event.target;
    this.attachment = eventTarget.files[0];
    this.bugData.fileName = this.attachment.name;
    //alert(this.attachment.name);

  }
}

