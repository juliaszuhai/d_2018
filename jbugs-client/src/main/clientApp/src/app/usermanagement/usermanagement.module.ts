import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Route, RouterModule, Routes} from "@angular/router";
import {ProfileComponent} from "../user/profile/profile.component";
import {LoginguardGuard} from "../authentication/loginguard.guard";
import {UserManagementComponent} from "./user-management/user-management.component";
import {MatTableModule} from '@angular/material/table'
import {
  ErrorStateMatcher,
  MatButtonModule,
  MatFormField, MatFormFieldControl,
  MatFormFieldModule, MatIconModule,
  MatInputModule,
  ShowOnDirtyErrorStateMatcher
} from "@angular/material";
import {RegisterUserComponent} from "./register-user/register-user.component";
import {MatDialogModule} from "@angular/material/dialog";
import {MatChipsModule} from "@angular/material/chips";
import {BugsPopupComponent} from "../bugs/bugs-popup/bugs-popup.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {UpdateUserComponent} from "./update-user/update-user.component";


const userManagementRoutes: Routes = [
  {path: 'manageusers', component: UserManagementComponent, canActivate: [LoginguardGuard]},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(userManagementRoutes),
    MatTableModule,
    MatButtonModule,
    MatDialogModule,
    MatChipsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatIconModule
  ],
  declarations: [
    UserManagementComponent,
    RegisterUserComponent,
    UpdateUserComponent
  ],
  exports: [UserManagementComponent,
    RouterModule,
    MatTableModule,
    MatButtonModule,
    MatDialogModule,
    MatChipsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatIconModule
  ],
  entryComponents: [
    RegisterUserComponent
  ],
  providers: [
    {provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher}
  ]
})
export class UsermanagementModule { }
