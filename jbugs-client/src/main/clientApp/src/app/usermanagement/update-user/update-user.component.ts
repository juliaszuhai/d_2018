import {Component, Inject, OnInit} from '@angular/core';
import {UsermanagementService} from "../usermanagement.service";
import {UserManagementComponent} from "../user-management/user-management.component";
import {FormControl, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {MyErrorStateMatcher} from "../register-user/register-user.component";
import {PermissionManagementService} from "../../permission-management/permission-management.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {

  constructor(
    private translate: TranslateService,
    public dialogRef: MatDialogRef<UserManagementComponent>,
    @Inject(MAT_DIALOG_DATA) public data,
    public usermgmt: UsermanagementService,
    public permissionmngmt: PermissionManagementService
  ) {
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

    if (control.value === null ||
      control.value === undefined ||
      control.value === '') {
      return null;
    }
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

  submitUpdate() {
    if (this.data.password === null ||
      this.data.password === undefined ||
      this.data.password === '') {
      delete this.data.password;
    }
    this.usermgmt.updateUser(this.data).subscribe(
      data => {
        this.dialogRef.close();
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

  getEmailErrorMessages() {
    this.emailFormControl.hasError('email') ? (this.translate.get('textFieldValidation.invalidEmail').subscribe((res: string) => this.emailErrorMessage = res)) :
      this.emailFormControl.hasError('emaildomainerror') ? (this.translate.get('textFieldValidation.invalidDomain').subscribe((res: string) => this.emailErrorMessage = res)) :
        '';
    return this.emailErrorMessage;

  }


  onNoClick(): void {
    this.dialogRef.close();
  }


  userRoles;
  allRoles;
  selectedrole;

  getUserRoles() {
    this.usermgmt.getRolesOfUser(this.data.username)
      .subscribe(roles => {
        this.userRoles = roles;
      })
  }

  getAllRoles() {
    this.permissionmngmt.getAllRoles()
      .subscribe(roles => {
        this.allRoles = roles;

      });
  }

  addRoleToUser() {
    this.usermgmt.addRoleToUser(this.data.username, this.selectedrole)
      .subscribe(() => {
        this.getUserRoles();
      });
  }

  removeRoleFromUser() {
    this.usermgmt.revokeRoleFromUser(this.data.username, this.selectedrole)
      .subscribe(() => {
        this.getUserRoles();
      });
  }


  ngOnInit() {
    this.getUserRoles();
    this.getAllRoles();
  }

}
