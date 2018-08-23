import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {ListBugsComponent} from "./list-bugs/list-bugs.component";
import {FormsModule} from "@angular/forms";
import {LoginguardGuard} from "../authentication/loginguard.guard";
import {AddBugComponent} from "./add-bug/add-bug.component";
import {MatNativeDateModule, MatDatepickerModule, MatFormFieldModule} from "@angular/material";
import {MatSelectModule} from '@angular/material/select';



const bugRoutes: Routes = [
  {path: 'bugs', component: ListBugsComponent, canActivate: [LoginguardGuard]},
  {path: 'add-bug', component: AddBugComponent, canActivate: [LoginguardGuard]}
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(bugRoutes),
    FormsModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatSelectModule
  ],
  declarations: [ListBugsComponent],
  exports: [ListBugsComponent,RouterModule,MatDatepickerModule,MatFormFieldModule,MatNativeDateModule,MatSelectModule]
})
export class BugsModule { }
