import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {AuthenticationService} from './authentication.service';
import {TokenInterceptorService} from './token-interceptor.service';
import {MatButtonModule} from '@angular/material';

const loginRoutes: Routes = [
  {path: 'login', component: LoginComponent},
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(loginRoutes),
    MatButtonModule,
  ],
  declarations: [LoginComponent],
  exports: [LoginComponent, RouterModule,MatButtonModule,],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptorService,
    multi: true,
  }]
})
export class AuthenticationModule {
}
