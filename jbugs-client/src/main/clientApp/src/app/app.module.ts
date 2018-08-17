import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {JwtModule} from '@auth0/angular-jwt';
import { NavComponent } from './nav/nav.component';
import { ContentComponent } from './content/content.component';
import {RouterModule, Routes} from '@angular/router';

const appRoutes: Routes = [
  {
    path: '', pathMatch: 'full', redirectTo: '/content'
  },
  {
    path: 'login',  component: LoginComponent
  },
  {
    path: 'content',  component: ContentComponent
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
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
