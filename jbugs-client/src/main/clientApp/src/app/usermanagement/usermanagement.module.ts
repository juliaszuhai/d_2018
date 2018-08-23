import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Route, RouterModule, Routes} from "@angular/router";
import {ProfileComponent} from "../user/profile/profile.component";
import {LoginguardGuard} from "../authentication/loginguard.guard";
import {UserManagementComponent} from "./user-management/user-management.component";
import {MatTableModule} from '@angular/material/table'


const userManagementRoutes: Routes = [
  {path: 'manageusers', component: UserManagementComponent, canActivate: [LoginguardGuard]},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(userManagementRoutes),
    MatTableModule
  ],
  declarations: [UserManagementComponent],
  exports: [UserManagementComponent,
    RouterModule,
    MatTableModule]
})
export class UsermanagementModule { }
