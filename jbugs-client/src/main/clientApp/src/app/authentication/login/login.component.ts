import {Component, OnInit} from '@angular/core';
import {AuthenticationService, UserData, UserLoginData} from '../authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userLoginData: UserLoginData;
  userData: UserData;
  error: boolean;

  constructor(private authenticationService: AuthenticationService, private router: Router) {
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
    this.error = false;
  }

  displayError() {
    return this.error;
  }

  validatePassword() {
    const regex = new RegExp('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*.-]).{6,}$');
    return regex.test(this.userLoginData.password);
  }

  validateUsername() {
    return this.userLoginData.username.length >= 6;
  }

  submitForm() {
    this.authenticationService.validateUser(this.userLoginData.username, this.userLoginData.password)
      .subscribe(
        data => {
          this.error = false;
          this.router.navigate([`/profile`]);
        },
        err => {
          this.error = true;
          console.log('Here i am');
        }
      );
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
