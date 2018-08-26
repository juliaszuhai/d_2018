import {Component, Inject, OnInit} from '@angular/core';
import {UsermanagementService} from "../usermanagement.service";
import {UserManagementComponent} from "../user-management/user-management.component";
import {FormControl, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {MyErrorStateMatcher} from "../register-user/register-user.component";
import {TranslatorService} from "../../translator/translator.service";
import {PermissionManagementService} from "../../permission-management/permission-management.service";

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {

  constructor(
    public translatorService: TranslatorService,
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
    console.log(this.data);
    this.usermgmt.updateUser(this.data).subscribe(
      data => {
        this.dialogRef.close();
      }, error => {
        console.log("Update error:" + error)
      }
    );

  }


  getPassowrdErrorMessage() {
    return this.passwordFormControl.hasError('required') ? 'This field is required.' :
      this.passwordFormControl.hasError('passwordInvalid') ? 'Not a valid password format.' :
        '';
  }

  getEmailErrorMessages() {
    console.log(this.emailFormControl);
    if (this.emailFormControl.hasError('email')) {
      return 'Invalid email format'
    } else if (this.emailFormControl.hasError('emaildomainerror')) {
      return 'Unsupported email domain'
    }
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
        console.log(roles);
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
