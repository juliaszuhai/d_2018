import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../authentication/authentication.service';
import {Router} from '@angular/router';
import {TranslateService} from "@ngx-translate/core";


@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  constructor(private authService: AuthenticationService,
              private router: Router,
              private translate: TranslateService) {
  }

  getFirstName() {
    return localStorage['firstName'];
  }

  public isLoggedIn() {
    if (!this.authService.isLoggedIn()) {

    }
    return this.authService.isLoggedIn();
  }


  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }


  ngOnInit() {

    //this.translatorService.getTranslationObservable(0);

  }

}
