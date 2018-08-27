import {Component, Inject, OnInit} from '@angular/core';
import {ErrorStateMatcher, MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {UserManagementComponent} from "../user-management/user-management.component";
import {UsermanagementService} from "../usermanagement.service";
import {FormControl, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {TranslateService} from "@ngx-translate/core";


export interface UserData {
  firstName: string,
  lastName: string,
  password: string,
  email: string,
  phoneNumber: string;
}


export class MyErrorStateMatcher implements ErrorStateMatcher {

  constructor() {
  }

  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}


@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {


  constructor(
    private translate: TranslateService,
    public dialogRef: MatDialogRef<UserManagementComponent>,
    @Inject(MAT_DIALOG_DATA) public data: UserData,
    public usermgmt: UsermanagementService) {
  }

  matcher = new MyErrorStateMatcher();
  passwordFormControl = new FormControl('', [
    Validators.required,
    this.validatePassword
  ]);

  emailFormControl = new FormControl('', [
    Validators.email,
    Validators.required,
    this.validateEmail
  ]);

  validatePassword(control: FormControl) {
    const regex = new RegExp('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*.-]).{6,}$');

    if (regex.test(control.value)) {
      return null;
    }
    return {
      passwordInvalid: {
        password: control.value
      }
    }
  }

  validateEmail(control: FormControl) {
    const regex = new RegExp('^[A-Za-z0-9._%+-]+@msggroup.com$');
    if (regex.test(control.value)) {
      return null;
    }
    return {
      emaildomainerror: {
        emai: control.value
      }
    }
  }

  submitRegister() {
    this.usermgmt.registerUser(this.data).subscribe(
      data => {
        console.log("Register works");
        this.dialogRef.close();
      }, error => {
        console.log("Register error:" + error)
      }
    );

  }

  passwordErrorMessage: string;
  emailErrorMessage: string;

  getPasswordErrorMessage() {
    this.passwordFormControl.hasError('required') ? (this.translate.get('textFieldValidation.required').subscribe((res: string) => this.passwordErrorMessage = res)) :
      this.passwordFormControl.hasError('passwordInvalid') ? (this.translate.get('textFieldValidation.invalidPassword').subscribe((res: string) => this.passwordErrorMessage = res)) :
        '';

    return this.passwordErrorMessage;
  }

  getEmailErrorMessages(){
    console.log(this.emailFormControl);
    this.emailFormControl.hasError('required') ? (this.translate.get('textFieldValidation.required').subscribe((res: string) => this.emailErrorMessage = res)) :
      this.emailFormControl.hasError('email') ? (this.translate.get('textFieldValidation.invalidEmail').subscribe((res: string) => this.emailErrorMessage = res)) :
        this.emailFormControl.hasError('emaildomainerror') ? (this.translate.get('textFieldValidation.invalidDomain').subscribe((res: string) => this.emailErrorMessage = res)) :
          '';
    return this.emailErrorMessage;

  }


  onNoClick(): void {

    this.dialogRef.close();
  }

  ngOnInit() {
  }

}
