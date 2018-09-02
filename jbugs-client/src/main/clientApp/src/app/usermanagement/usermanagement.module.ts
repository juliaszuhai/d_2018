import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {LoginguardGuard} from "../authentication/loginguard.guard";
import {UserManagementComponent} from "./user-management/user-management.component";
import {MatTableModule} from '@angular/material/table'
import {
  ErrorStateMatcher,
  MatButtonModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatSelectModule,
  MatTabsModule,
  ShowOnDirtyErrorStateMatcher
} from "@angular/material";
import {RegisterUserComponent} from "./register-user/register-user.component";
import {MatDialogModule} from "@angular/material/dialog";
import {MatChipsModule} from "@angular/material/chips";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {UpdateUserComponent} from "./update-user/update-user.component";
import {DeactivationPopupComponent} from "./deactivation-popup/deactivation-popup.component";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {HttpLoaderFactory} from "../app.module";
import {HttpClient} from "@angular/common/http";
//import {UserManagementGuard} from "../authentication/user-management.guard";


const userManagementRoutes: Routes = [
  {path: 'manage-users', component: UserManagementComponent, canActivate: [LoginguardGuard]}//, UserManagementGuard]},
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
    RouterModule.forChild(userManagementRoutes),
    MatTableModule,
    MatButtonModule,
    MatDialogModule,
    MatChipsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatIconModule,
    MatTabsModule,
    MatListModule,
    MatSelectModule
  ],
  declarations: [
    UserManagementComponent,
    RegisterUserComponent,
    UpdateUserComponent,
    DeactivationPopupComponent
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
    MatIconModule,
    MatTabsModule,
    MatListModule,
    MatSelectModule
  ],
  entryComponents: [
    RegisterUserComponent,
    UpdateUserComponent,
    DeactivationPopupComponent
  ],
  providers: [
    {provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher}
  ]
})
export class UsermanagementModule { }
