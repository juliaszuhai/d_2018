import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './authentication/login/login.component';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {JwtModule} from '@auth0/angular-jwt';
import {RouterModule, Routes} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatCheckboxModule,
  MatIconModule,
  MatMenuModule,
  MatTableModule,
  MatToolbarModule
} from '@angular/material';
import {AuthenticationModule} from './authentication/authentication.module';
import {NavigationModule} from './navigation/navigation.module';
import {ProfileComponent} from './user/profile/profile.component';
import {UserModule} from './user/user.module';
import {LoginguardGuard} from './authentication/loginguard.guard';

import {TranslatorModule} from "./translator/translator.module";

import {BugsModule} from "./bugs/bugs.module";
import {ListBugsComponent} from "./bugs/list-bugs/list-bugs.component";
import { RecaptchaDirective } from './authentication/login/recaptcha.directive';
import { BugsPopupComponent } from './bugs/bugs-popup/bugs-popup.component';
import { UserManagementComponent } from './usermanagement/user-management/user-management.component';
import {UsermanagementModule} from "./usermanagement/usermanagement.module";
import { AddBugComponent } from './bugs/add-bug/add-bug.component';




const appRoutes: Routes = [
  {
    path: '', pathMatch: 'full', redirectTo: '/login'
  },

  {
    path: 'bugs', component: ListBugsComponent,
  },
];

@NgModule({
  declarations: [
    AppComponent,
    RecaptchaDirective
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    JwtModule,
    RouterModule.forRoot(appRoutes),
    BrowserAnimationsModule,
    AuthenticationModule,
    NavigationModule,
    UserModule,
    BugsModule,
    TranslatorModule,
    MatTableModule,
    UsermanagementModule




  ],
  providers: [{
    provide: LoginguardGuard, useClass: LoginguardGuard
  }],

  bootstrap: [AppComponent],
  exports: [
    MatButtonModule,
    MatCheckboxModule,
    MatMenuModule,
    MatToolbarModule,

  ]
})
export class AppModule {
}
