import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginguardGuard} from "../authentication/loginguard.guard";
import {RouterModule, Routes} from "@angular/router";
import {RolesAndPermissionsViewComponent} from "./roles-and-permissions-view/roles-and-permissions-view.component";
import {
  MatButtonModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatSelectModule
} from "@angular/material";
import {MatChipsModule} from "@angular/material/chips";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatDialogModule} from "@angular/material/dialog";
import {MatTableModule} from "@angular/material/table";
import {EditRoleComponent} from "./edit-role/edit-role.component";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {HttpLoaderFactory} from "../app.module";
import {HttpClient} from "@angular/common/http";
import {PermissionGuard} from "../authentication/permission.guard";

const permissionRoutes: Routes = [
  {
    path: 'roles-and-permissions',
    component: RolesAndPermissionsViewComponent,
    canActivate: [LoginguardGuard, PermissionGuard]
  },
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
    RouterModule.forChild(permissionRoutes),
    MatTableModule,
    MatButtonModule,
    MatDialogModule,
    MatChipsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatIconModule,
    MatListModule,
    MatSelectModule
  ],
  declarations: [
    RolesAndPermissionsViewComponent,
    EditRoleComponent
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
    MatIconModule,
    MatListModule,
    MatSelectModule
  ],
  entryComponents: [
    EditRoleComponent
  ],
})
export class PermissionManagementModule {
}
