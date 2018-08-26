import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginguardGuard} from "../authentication/loginguard.guard";
import {RouterModule, Routes} from "@angular/router";
import {RolesAndPermissionsViewComponent} from "./roles-and-permissions-view/roles-and-permissions-view.component";
import {MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule} from "@angular/material";
import {MatChipsModule} from "@angular/material/chips";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatDialogModule} from "@angular/material/dialog";
import {MatTableModule} from "@angular/material/table";

const permissionRoutes: Routes = [
  {path: 'roles-and-permissions', component: RolesAndPermissionsViewComponent, canActivate: [LoginguardGuard]},
];


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(permissionRoutes),
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
    RolesAndPermissionsViewComponent
  ],
  exports: [
    MatTableModule,
    MatButtonModule,
    MatDialogModule,
    MatChipsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatIconModule
  ]
})
export class PermissionManagementModule {
}
