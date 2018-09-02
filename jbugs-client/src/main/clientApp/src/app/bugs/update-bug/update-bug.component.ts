import {Component, Inject, OnInit} from '@angular/core';
import {ErrorStateMatcher, MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {BugListService} from "../bugs.service";
import {ListBugsComponent} from "../list-bugs/list-bugs.component";
import {TranslateService} from "@ngx-translate/core";
import {FormControl, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {AuthenticationService} from "../../authentication/authentication.service";


export class MyErrorStateMatcher implements ErrorStateMatcher {

  constructor() {
  }

  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}


@Component({
  selector: 'app-update-bug',
  templateUrl: './update-bug.component.html',
  styleUrls: ['./update-bug.component.css']
})
export class UpdateBugComponent implements OnInit {

  descriptionErrorMessage: string;
  fixedVersionErrorMessage: string;
  versionErrorMessage: string;
  matcher = new MyErrorStateMatcher();
  successorsList: any[];
  severities: string[] = [
    "CRITICAL", "HIGH", "MEDIUM", "LOW"
  ];
  constructor(private translate: TranslateService,
              public dialogRef: MatDialogRef<ListBugsComponent>,
              @Inject(MAT_DIALOG_DATA) public data,
              public bugmngmt: BugListService,
              public authService: AuthenticationService
  ) {

  }

  descriptionFormControl = new FormControl('', [
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

  ngOnInit() {
    this.getStatusSuccessor();
    this.data.statusBefore = this.data.statusString;
  }

  canCloseBug() {

    if (this.data.statusBefore === "REJECTED" || this.data.statusBefore === "FIXED") {
      return true;
    }
    return false;
  }

  submitUpdateBug() {


    this.bugmngmt.updateBug(this.data).subscribe(
      data => {
        this.dialogRef.close();
      }, error => {
        console.log("Update error:" + error)
      }
    );

  }

  getStatusSuccessor() {
    this.bugmngmt.getStatusSuccessors(this.data.id).subscribe(
      value => {
        this.successorsList = value;
        console.log(this.successorsList);
        if (this.successorsList.indexOf("CLOSED") > -1) {
          this.successorsList.splice(this.successorsList.indexOf("CLOSED"), 1);
        }
      }
    )
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

  closeBug(id) {
    this.bugmngmt.closeBug(id).subscribe(
      value => {
        console.log("bug closed");
        this.dialogRef.close()
      }
    )
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getDescriptionErrorMessage() {
    this.descriptionFormControl.hasError('descriptionInvalid') ? (this.translate.get('Invalid Description').subscribe((res: string) => this.descriptionErrorMessage = res)) :
      '';
    return this.descriptionErrorMessage;
  }

  getVersionErrorMessage() {
    this.versionFormControl.hasError('versionInvalid') ? (this.translate.get('Invalid Version').subscribe((res: string) => this.versionErrorMessage = res)) :
      '';
    return this.versionErrorMessage;
  }

  getFixedVersionErrorMessage() {
    this.fixedVersionFormControl.hasError('versionInvalid') ? (this.translate.get('Invalid Version').subscribe((res: string) => this.fixedVersionErrorMessage = res)) :
      '';
    return this.fixedVersionErrorMessage;
  }

  // getDateErrorMessage(){
  //   this.dateFormControl.hasError('required') ? (this.translate.get('textFieldValidation.required').subscribe((res: string) => this.dateErrorMessage = res)) :
  //     this.dateFormControl.hasError('dateInvalid') ? (this.translate.get('textFieldValidation.invalidDate').subscribe((res: string) => this.dateErrorMessage = res)) :
  //       '';
  //
  //   return this.dateErrorMessage;
  // }
}
