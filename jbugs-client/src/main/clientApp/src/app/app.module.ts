import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {FormsModule} from '@angular/forms';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {JwtModule} from '@auth0/angular-jwt';
import {RouterModule, Routes} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatCheckboxModule,
  MatMenuModule,
  MatSnackBarModule,
  MatTableModule,
  MatToolbarModule
} from '@angular/material';
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
import {NotificationModule} from "./notification/notification.module";
import {PermissionGuard} from "./authentication/permission.guard";
import {BugsGuard} from "./authentication/bugs.guard";
//import {ErrorViewComponent} from './error-view/error-view.component';


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
//    ErrorViewComponent,

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
    MatSnackBarModule,
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
    PermissionManagementModule,
    NotificationModule
  ],
  providers: [LoginguardGuard,
    PermissionGuard,
    BugsGuard,
  ],

  bootstrap: [AppComponent],
  exports: [
    MatButtonModule,
    MatCheckboxModule,
    MatMenuModule,
    MatToolbarModule,
    MatSnackBarModule
  ],
})
export class AppModule {
}

