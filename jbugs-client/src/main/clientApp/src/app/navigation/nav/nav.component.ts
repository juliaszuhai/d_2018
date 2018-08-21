import {Component, OnInit, ViewChild} from '@angular/core';
import {MatToolbar, MatButton} from '@angular/material';
import {AuthenticationService} from '../../authentication/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  constructor(private authService: AuthenticationService, private router: Router) {
  }

  getFirstName() {
    return localStorage['firstName'];
  }

  public isLoggedIn() {
    return this.authService.isLoggedIn();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  ngOnInit() {
  }

}
