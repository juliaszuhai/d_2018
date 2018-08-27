import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {FormsModule} from '@angular/forms';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {JwtModule} from '@auth0/angular-jwt';
import {RouterModule, Routes} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule, MatCheckboxModule, MatMenuModule, MatTableModule, MatToolbarModule} from '@angular/material';
import {AuthenticationModule} from './authentication/authentication.module';
import {NavigationModule} from './navigation/navigation.module';
import {UserModule} from './user/user.module';
import {LoginguardGuard} from './authentication/loginguard.guard';
import {BugsModule} from "./bugs/bugs.module";
import {RecaptchaDirective} from './authentication/login/recaptcha.directive';
import {UsermanagementModule} from "./usermanagement/usermanagement.module";
import {PermissionManagementModule} from "./permission-management/permission-management.module";
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';


const appRoutes: Routes = [
  {
    path: '', pathMatch: 'full', redirectTo: '/login'
  },

];

export function HttpLoaderFactory(http: HttpClient) {

  return new TranslateHttpLoader(http);

}

@NgModule({
  declarations: [
    AppComponent,
    RecaptchaDirective,
  ],
  imports: [
    TranslateModule.forRoot({

      loader: {

        provide: TranslateLoader,

        useFactory: HttpLoaderFactory,

        deps: [HttpClient]

      }

    }),
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
    MatTableModule,
    UsermanagementModule,
    PermissionManagementModule
  ],
  providers: [{
    provide: LoginguardGuard, useClass: LoginguardGuard
  }],

  bootstrap: [AppComponent],
  exports: [
    MatButtonModule,
    MatCheckboxModule,
    MatMenuModule,
    MatToolbarModule
  ]
})
export class AppModule {
}

