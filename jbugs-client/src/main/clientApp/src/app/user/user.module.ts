import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProfileComponent} from './profile/profile.component';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from '../authentication/login/login.component';
import {MatExpansionModule} from '@angular/material';
import {LoginguardGuard} from '../authentication/loginguard.guard';
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {HttpLoaderFactory} from "../app.module";
import {HttpClient} from "@angular/common/http";


const profileRoutes: Routes = [
  {path: 'profile', component: ProfileComponent, canActivate: [LoginguardGuard]},
];


@NgModule({
  imports: [
    TranslateModule.forChild({

      loader: {

        provide: TranslateLoader,

        useFactory: HttpLoaderFactory,

        deps: [HttpClient]

      }

    }),
    CommonModule,
    RouterModule.forChild(profileRoutes),
    MatExpansionModule,
  ],
  declarations: [ProfileComponent],
  exports: [ProfileComponent, RouterModule]
})
export class UserModule { }
