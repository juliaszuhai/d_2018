import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './authentication/login/login.component';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {JwtModule} from '@auth0/angular-jwt';
import {NavComponent} from './nav/nav.component';
import {ContentComponent} from './content/content.component';
import {RouterModule, Routes} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule, MatCheckboxModule, MatMenuModule, MatToolbarModule, MatIconModule} from '@angular/material';
import {AuthenticationModule} from './authentication/authentication.module';
import {TranslatorComponent} from "./translator/translator.component";
import {TranslatorModule} from "./translator/translator.module";


const appRoutes: Routes = [
  {
    path: '', pathMatch: 'full', redirectTo: '/content'
  },
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'content', component: ContentComponent
  },

];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavComponent,
    ContentComponent

  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    JwtModule,
    RouterModule.forRoot(appRoutes),
    BrowserAnimationsModule,
    MatButtonModule,
    MatCheckboxModule,
    MatMenuModule,
    MatToolbarModule,
    AuthenticationModule,
    TranslatorModule,
    MatIconModule
  ],
  providers: [],
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
