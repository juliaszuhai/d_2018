import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {ListBugsComponent} from "./list-bugs/list-bugs.component";

const bugRoutes: Routes = [

  {
    path: 'listBugs', component: ListBugsComponent
  },
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
