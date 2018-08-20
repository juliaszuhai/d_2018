import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProfileComponent} from './profile/profile.component';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from '../authentication/login/login.component';
import {MatExpansionModule} from '@angular/material';
import {LoginguardGuard} from '../authentication/loginguard.guard';


const profileRoutes: Routes = [
  {path: 'profile', component: ProfileComponent, canActivate: [LoginguardGuard]},
];


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(profileRoutes),
    MatExpansionModule,
  ],
  declarations: [ProfileComponent],
  exports: [ProfileComponent, RouterModule]
})
export class UserModule { }
