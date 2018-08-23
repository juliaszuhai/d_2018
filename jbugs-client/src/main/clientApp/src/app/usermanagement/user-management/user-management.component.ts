import { Component, OnInit } from '@angular/core';
import {UsermanagementService} from "../usermanagement.service";

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {

  userData: any;

  constructor(private usrMgmtService: UsermanagementService) { }

  getUsers(){
    this.usrMgmtService.getAllUsers()
      .subscribe(
        data => {
          this.userData = data;
        }
      )
  }

  ngOnInit() {
  }

}
