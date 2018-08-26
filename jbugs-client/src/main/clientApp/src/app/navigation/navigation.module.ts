import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NavComponent} from './nav/nav.component';
import {MatButtonModule, MatCheckboxModule, MatIconModule, MatMenuModule, MatToolbarModule} from '@angular/material';
import {AuthenticationModule} from '../authentication/authentication.module';
import {UserModule} from '../user/user.module';
import {TranslatorModule} from "../translator/translator.module";

@NgModule({
  imports: [
    CommonModule,
    MatButtonModule,
    MatCheckboxModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    AuthenticationModule,
    UserModule,
    TranslatorModule
  ],
  declarations: [NavComponent],
  exports: [NavComponent,
    MatButtonModule,
    MatCheckboxModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule]
})
export class NavigationModule {
}
