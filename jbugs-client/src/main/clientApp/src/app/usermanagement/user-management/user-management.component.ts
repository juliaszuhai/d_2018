import {Component, OnInit} from '@angular/core';
import {UsermanagementService} from "../usermanagement.service";
import {MatDialog} from "@angular/material";
import {RegisterUserComponent, UserData} from "../register-user/register-user.component";
import {UpdateUserComponent} from "../update-user/update-user.component";
import {TranslatorService} from "../../translator/translator.service";

export interface UserElement {
  firstName: string;
  lastName: number;
  username: number;
  email: string;
  phoneNumber: string;
}


@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {


  userData;

  dataSource: any;

  constructor(private usrMgmtService: UsermanagementService,
              public dialog: MatDialog,
              private translate: TranslateService) {

  }

  openRegisterDialog(): void {
    const dialogRef = this.dialog.open(RegisterUserComponent,{
      width: '60%',
      data: {
        email: this.userData.email,
        firstName: this.userData.firstName,
        lastName: this.userData.lastName,
        password: this.userData.password,
        phoneNumber: this.userData.phoneNumber,
        username: this.userData.username
      }
    });

    dialogRef.afterClosed().subscribe(result => {

      this.getUsers();
    });
  }

  openUpdateDialog(user): void {
    const dialogRef = this.dialog.open(UpdateUserComponent, {
      width: '60%',
      data: {
        active: user.isActive,
        username: user.username,
        email: user.email,
        firstName: user.firstName,
        lastName: user.lastName,
        password: user.password,
        phoneNumber: user.phoneNumber
      }
    });

    dialogRef.afterClosed().subscribe(result => {

      this.getUsers();
    });
  }

  getUsers() {
    this.usrMgmtService.getAllUsers()
      .subscribe(
        data => {
          this.dataSource = data;
          console.log(this.dataSource);
        }
      )
  }



  getActivationButtonText(isActive) {
    if (isActive) {

      this.translate.get('userManagement.buttonDeactivate').subscribe((res: string) => this.button = res);
      return this.button;

    } else {

     this.translate.get('userManagement.buttonActivate').subscribe((res: string) => this.button = res);
     return this.button;

    }
  }

  activateUser(username) {
    this.usrMgmtService.activateUser(username)
      .subscribe(
        data => {
          this.getUsers();
        }
      );
  }

  openDeactivationErrorDialog(user): void {
    const dialogRef = this.dialog.open(DeactivationPopupComponent, {
      width: '300px',
      data: {
        username: user.username
      }
    });
  }


  deactivateUser(username) {
    this.usrMgmtService.deactivateUser(username)
      .subscribe(
        data => {
          this.getUsers();
        },
        error1 => {
          if (error1.status == 302) {
            this.openDeactivationErrorDialog(username);
          }
        }
      );
  }

  toggleActivation(isActive, username) {
    if (isActive) {
      this.deactivateUser(username);
    } else {
      this.activateUser(username);
    }
  }

  displayedColumns: string[] = [
    'firstName',
    'lastName',
    'username',
    'email',
    'phoneNumber',
    'activation',
    'edit'
  ];


  ngOnInit() {
    this.getUsers();
    this.userData = {
      email: "",
      firstName: "",
      lastName: "",
      password: "",
      phoneNumber: ""
    }
  }

}
