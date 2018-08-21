import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './authentication/login/login.component';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {JwtModule} from '@auth0/angular-jwt';
import {NavComponent} from './navigation/nav/nav.component';
import {ContentComponent} from './content/content.component';
import {RouterModule, Routes} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule, MatCheckboxModule, MatMenuModule, MatToolbarModule} from '@angular/material';
import {AuthenticationModule} from './authentication/authentication.module';
import { ListBugsComponent } from './bugs/list-bugs/list-bugs.component';
import {BugsModule} from "./bugs/bugs.module";
import {TranslatorModule} from "./translator/translator.module";


const appRoutes: Routes = [
  {
    path: '', pathMatch: 'full', redirectTo: '/content'
  },
  {
    path: 'content', component: ContentComponent
  },
  {
    path: 'listBugs', component: ListBugsComponent
  },


];

@NgModule({
  declarations: [
    AppComponent,
    ContentComponent,
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
    BugsModule
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
    MatIconModule
  ]
})
export class AppModule {
}
