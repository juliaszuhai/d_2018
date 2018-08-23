import {Component, OnInit} from '@angular/core';
import {UsermanagementService} from "../usermanagement.service";

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

  constructor(private usrMgmtService: UsermanagementService) {
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
      return "Deactivate";
    } else {
      return "Activate";
    }
  }

  activateUser(username) {
    this.usrMgmtService.activateUser(username)
      .subscribe(
        data => {
          console.log(data);
          this.getUsers();
        }
      );
  }

  deactivateUser(username) {
    this.usrMgmtService.deactivateUser(username)
      .subscribe(
        data => {
          console.log(data);
          this.getUsers();
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
  }

}
