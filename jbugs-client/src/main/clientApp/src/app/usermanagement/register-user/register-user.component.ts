import {Component, Inject, OnInit} from '@angular/core';
import {ErrorStateMatcher, MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {UserManagementComponent} from "../user-management/user-management.component";
import {UsermanagementService} from "../usermanagement.service";
import {FormControl, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {TranslatorService} from "../../translator/translator.service";

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
    public translatorService: TranslatorService,
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


  getPassowordErrorMessage() {
    return this.passwordFormControl.hasError('required') ? 'This field is required.' :
      this.passwordFormControl.hasError('passwordInvalid') ? 'Not a valid password format.' :
        '';
  }

  getEmailErrorMessages(){
    console.log(this.emailFormControl);
    if(this.emailFormControl.hasError('required')){
      return 'This field is required.'
    } else if(this.emailFormControl.hasError('email')){
      return 'Invalid email format'
    } else if(this.emailFormControl.hasError('emaildomainerror')){
      return 'Unsupported email domain'
    }
  }


  onNoClick(): void {

    this.dialogRef.close();
  }

  ngOnInit() {
  }

}
