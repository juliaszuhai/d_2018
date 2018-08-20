import {Component, OnInit} from '@angular/core';
import {UserData} from '../../authentication/authentication.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  userData: UserData;

  constructor() {
    this.userData = {
      username: '',
      firstName: '',
      lastName: '',
      email: '',
      phoneNumber: '',

    };
  }


  populateUserData() {
    this.userData['username'] = localStorage['username'];
    this.userData['firstName'] = localStorage['firstName'];
    this.userData['lastName'] = localStorage['lastName'];
    this.userData['email'] = localStorage['email'];
    this.userData['phoneNumber'] = localStorage['phone'];
  }

  ngOnInit() {
    this.populateUserData();
  }

}
