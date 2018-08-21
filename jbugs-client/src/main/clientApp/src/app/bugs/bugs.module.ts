import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LoginComponent} from "../authentication/login/login.component";
import {RouterModule, Routes} from "@angular/router";
import {ListBugsComponent} from "./list-bugs/list-bugs.component";
import {LoginguardGuard} from "../authentication/loginguard.guard";


const bugRoutes: Routes = [
  {path: 'listBugs', component: ListBugsComponent, canActivate: [LoginguardGuard]},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(bugRoutes),
  ],
  declarations: [ListBugsComponent],
  exports: [ListBugsComponent,RouterModule]
})
export class BugsModule { }
