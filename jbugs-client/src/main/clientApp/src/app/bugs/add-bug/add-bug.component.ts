import {Component, OnInit} from '@angular/core';
import {BugData, BugListService} from "../bugs.service";
import {MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {ErrorStateMatcher} from "@angular/material";
import {FormControl, FormGroupDirective, NgForm, Validators} from "@angular/forms";


export class MyErrorStateMatcher implements ErrorStateMatcher {

  constructor() {
  }

  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-add-bug',
  templateUrl: './add-bug.component.html',
  styleUrls: ['./add-bug.component.css']
})
export class AddBugComponent implements OnInit {

  descriptionErrorMessage: string;
  fixedVersionErrorMessage: string;
  versionErrorMessage: string;
  bugData: BugData;
  severities: string[] = [
    "CRITICAL", "HIGH", "MEDIUM", "LOW"
  ];
  fileUploaded: string;
  attachment: File;
  error: boolean;
  errorMessage: string;
  matcher = new MyErrorStateMatcher();


  descriptionFormControl = new FormControl('',
    [
      Validators.required,
      this.validateDescription
    ]);
  fixedVersionFormControl = new FormControl('', [
    Validators.required,
    this.validateVersion
  ]);
  versionFormControl = new FormControl('', [
    Validators.required,
    this.validateVersion
  ]);

  constructor(public dialogRef: MatDialogRef<AddBugComponent>,
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

  validateVersion(control: FormControl) {
    const regex = new RegExp('([a-zA-Z0-9]+).([a-zA-Z0-9]+).([a-zA-Z0-9]+)');
    if (control.value === null ||
      control.value === undefined ||
      control.value === '') {
      return null;
    }
    if (regex.test(control.value)) {
      return null;
    }
    return {
      versionInvalid: {
        version: control.value
      }
    }
  }

  validateDescription(control: FormControl) {

    if (control.value.toString().length >= 250) {
      return null;
    }
    return {
      descriptionInvalid: {
        description: control.value
      }
    }
  }

  // getVersionErrorMessage() {
  //   this.versionFormControl.hasError('versionInvalid') ? (this.translate.get('Invalid Version').subscribe((res: string) => this.versionErrorMessage = res)) :
  //     '';
  //   return this.versionErrorMessage;
  // }
  //
  // getFixedVersionErrorMessage() {
  //   this.fixedVersionFormControl.hasError('versionInvalid') ? (this.translate.get('Invalid Version').subscribe((res: string) => this.fixedVersionErrorMessage = res)) :
  //     '';
  //   return this.fixedVersionErrorMessage;
  // }

  getDescriptionErrorMessage() {
    this.descriptionFormControl.hasError('descriptionInvalid') ? (this.translate.get('Invalid Description').subscribe((res: string) => this.descriptionErrorMessage = res)) :
      '';
    return this.descriptionErrorMessage;
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
    this.dialogRef.close();
  }

  // validateDescription() {
  //   return this.bugData.description.length >= 250;
  // }

  // validateVersion() {
  //   const regex = new RegExp('([a-zA-Z0-9]+).([a-zA-Z0-9]+).([a-zA-Z0-9]+)');
  //   return regex.test(this.bugData.version);
  // }

  addBugForm() {
    this.bugService.validateBug(this.bugData)
      .subscribe(
        data => {

          if (this.attachment != null)
            this.bugService.addFile(data, this.attachment.name, this.attachment).subscribe(
              data => {
                this.error = false;

              }
            );
          this.dialogRef.close();
        }
        // err => {
        //   this.error = true;
        //   if (err.valueOf().error.value == 'DESCRIPTION_TOO_SHORT') {
        //     this.errorMessage = "Description is too short. Write more";
        //   } else if (err.valueOf().error.value == 'VERSION_NOT_VALID') {
        //     this.errorMessage = "Version syntax is incorrect";
        //   }
        //   else {
        //     this.errorMessage = "nu merge";
        //   }
        // }
      );
  }

  parseFile($event) {
    let eventTarget = <HTMLInputElement>event.target;
    this.attachment = eventTarget.files[0];
    this.bugData.fileName = this.attachment.name;
    //alert(this.attachment.name);

  }
}

