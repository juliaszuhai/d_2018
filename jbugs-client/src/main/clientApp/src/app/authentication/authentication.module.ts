import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {AuthenticationService} from './authentication.service';
import {TokenInterceptorService} from './token-interceptor.service';

const loginRoutes: Routes = [
  {path: 'login', component: LoginComponent},
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(loginRoutes)
  ],
  declarations: [LoginComponent],
  exports: [LoginComponent, RouterModule],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptorService,
    multi: true,
  }]
})
export class AuthenticationModule {
}
