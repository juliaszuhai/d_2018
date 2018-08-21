import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LoginComponent} from "../authentication/login/login.component";
import {RouterModule, Routes} from "@angular/router";
import {ListBugsComponent} from "./list-bugs/list-bugs.component";


const loginRoutes: Routes = [
  {path: 'listBugs', component: ListBugsComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(loginRoutes),
  ],
  declarations: [ListBugsComponent],
  exports: [ListBugsComponent,RouterModule]
})
export class BugsModule { }
