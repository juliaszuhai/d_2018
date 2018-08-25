import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginguardGuard} from "../authentication/loginguard.guard";
import {RouterModule, Routes} from "@angular/router";
import {RolesAndPermissionsViewComponent} from "./roles-and-permissions-view/roles-and-permissions-view.component";

const permissionRoutes: Routes = [
  {path: 'roles-and-permissions', component: RolesAndPermissionsViewComponent, canActivate: [LoginguardGuard]},
];


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(permissionRoutes),
  ],
  declarations: [
    RolesAndPermissionsViewComponent
  ]
})
export class PermissionManagementModule {
}
