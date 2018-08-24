import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {ListBugsComponent} from "./list-bugs/list-bugs.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {LoginguardGuard} from "../authentication/loginguard.guard";
import {AddBugComponent} from "./add-bug/add-bug.component";
import {MatNativeDateModule, MatDatepickerModule, MatFormFieldModule} from "@angular/material";
import {MatSelectModule} from '@angular/material/select';

import {MatButtonModule, MatCheckboxModule, MatDialogRef} from "@angular/material";
import {MatDialogModule} from '@angular/material/dialog';
import {BugsPopupComponent} from "./bugs-popup/bugs-popup.component";
import {MatChipsModule} from '@angular/material/chips';

const bugRoutes: Routes = [
  {path: 'bugs', component: ListBugsComponent, canActivate: [LoginguardGuard]},
  {path: 'add-bug', component: AddBugComponent, canActivate: [LoginguardGuard]}
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(bugRoutes),
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCheckboxModule,
    MatDialogModule,
    MatChipsModule

  ],
  declarations: [ListBugsComponent, BugsPopupComponent],
  exports: [ListBugsComponent,RouterModule,MatButtonModule,  MatCheckboxModule, MatDialogModule, BugsPopupComponent, MatDialogModule,
    MatChipsModule

  ],
  entryComponents: [
    BugsPopupComponent
  ]
})
export class BugsModule { }
