import {Component, Inject, OnInit} from '@angular/core';
import {Attachment, BugData, BugListService, RelatedUser} from "../bugs.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";



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
  filesUploaded: string[] = [];
  attachments: Attachment[] = [];
  error: boolean;
  errorMessage: string;


  constructor(public dialogRef2: MatDialogRef<AddBugComponent>,
               private bugService: BugListService,
              private router: Router,
              private translate: TranslateService
              ) {
    this.bugData={
      id: null,
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
    this.bugService.validateBug(this.bugData, this.attachments)
      .subscribe(
        data => {
          this.error = false;
          this.router.navigate([`/bugs`]);
        },
        err => {
          this.error = true;
          if(err.valueOf().error.value=='DESCRIPTION_TOO_SHORT') {
            this.errorMessage = "Description is too short. Write more";
          } else if(err.valueOf().error.value=='VER'){
            this.errorMessage = "Version syntax is incorrect"
          }
        }
      );
  }

  parseFile($event) {
    let reader : FileReader[] = [];
    let eventTarget = <HTMLInputElement>event.target;
    if (eventTarget.files && eventTarget.files.length > 0) {
      for (let i = 0; i < eventTarget.files.length; i++) {
        let file = eventTarget.files[i];
        reader[i] = new FileReader();
        this.filesUploaded[i] = file.name;
        this.attachments[i] = {
          bugDTO: this.bugData,
          blob: new Uint8Array(),
          extension:file.name.substring(file.name.lastIndexOf('.',0) + 1).toUpperCase()
        }
        reader[i].onload = (event) => {
          this.attachments[i].blob = reader[i].result;
        }
        reader[i].readAsArrayBuffer(file);
      }
    }

  }
}
