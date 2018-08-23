import { Component, OnInit } from '@angular/core';
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
  constructor(private usrMgmtService: UsermanagementService) { }
  getUsers(){
    this.usrMgmtService.getAllUsers()
      .subscribe(
        data => {
          this.dataSource = data;
        }
      )
  }

  displayedColumns: string[] = ['firstName', 'lastName', 'username', 'email','phoneNumber'];


  ngOnInit() {
      this.getUsers();
  }

}
