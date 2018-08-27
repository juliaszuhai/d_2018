import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {ListBugsComponent} from "./list-bugs/list-bugs.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {LoginguardGuard} from "../authentication/loginguard.guard";
import {MatButtonModule, MatCheckboxModule, MatDialogRef} from "@angular/material";
import {MatDialogModule} from '@angular/material/dialog';
import {BugsPopupComponent} from "./bugs-popup/bugs-popup.component";
import {MatChipsModule} from '@angular/material/chips';
import {AddBugComponent} from "./add-bug/add-bug.component";
import {MatNativeDateModule, MatDatepickerModule, MatFormFieldModule} from "@angular/material";
import {MatSelectModule} from '@angular/material/select';
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {HttpLoaderFactory} from "../app.module";
import {HttpClient} from "@angular/common/http";


const bugRoutes: Routes = [
  {path: 'bugs', component: ListBugsComponent, canActivate: [LoginguardGuard]},
  // {path: 'add-bug', component: AddBugComponent, canActivate: [LoginguardGuard]}
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
    RouterModule.forChild(bugRoutes),
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCheckboxModule,
    MatDialogModule,
    MatChipsModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatSelectModule,


  ],
  declarations: [ListBugsComponent, BugsPopupComponent, AddBugComponent],
  exports: [ListBugsComponent, RouterModule, MatButtonModule, MatCheckboxModule, MatDialogModule, BugsPopupComponent, MatDialogModule,
    MatChipsModule, MatDatepickerModule, MatFormFieldModule, MatNativeDateModule, MatSelectModule

  ],
  entryComponents: [
    BugsPopupComponent,
    AddBugComponent
  ]
})
export class BugsModule {
}
