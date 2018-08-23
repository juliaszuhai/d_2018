import {Component, OnInit, ViewChild} from '@angular/core';
import {MatToolbar, MatButton} from '@angular/material';
import {AuthenticationService} from '../../authentication/authentication.service';
import {Router} from '@angular/router';
import {TranslateService} from "ng2-translate";
import {TranslatorService} from "../../translator/translator.service";

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  constructor(private authService: AuthenticationService,
              private router: Router,
              private translateService: TranslatorService) {
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

  languageOption:Object;



  public getTranslation(id : number){
    this.translateService.getLanguageFile(id)
      .subscribe(lng =>this.languageOption=lng)
  }
  ngOnInit() {
    sessionStorage.setItem("lng","0");
    this.getTranslation(parseInt(sessionStorage.getItem("lng")));

  }

}
