import {Component, OnInit} from '@angular/core';
import {AuthenticationService, UserData, UserLoginData} from '../authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userLoginData: UserLoginData;
  userData: UserData;


  constructor(private authenticationService: AuthenticationService) {
    this.userLoginData = {
      username: '',
      password: '',
    };

    this.userData = {
      username: '',
      firstName: '',
      lastName: '',
      email: '',
      phoneNumber: '',

    };
  }

  submitForm() {
    this.authenticationService.validateUser(this.userLoginData.username, this.userLoginData.password)
      .subscribe((response) => {
        console.log(response);
      });
  }

  isLoggedIn() {
    return this.authenticationService.isLoggedIn();
  }

  logout() {
    this.authenticationService.logout();
  }

  ngOnInit() {
  }

}
