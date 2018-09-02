import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NotificationsComponent} from './notifications/notifications.component';
import {RouterModule, Routes} from "@angular/router";
import {MatTableModule} from "../../../node_modules/@angular/material/table";
import {
  MatButtonModule,
  MatCardModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatSelectModule,
  MatTabsModule
} from "@angular/material";
import {MatDialogModule} from "../../../node_modules/@angular/material/dialog";
import {MatChipsModule} from "../../../node_modules/@angular/material/chips";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {HttpLoaderFactory} from "../app.module";
import {HttpClient} from "../../../node_modules/@angular/common/http";
import {NotificationMessageComponent} from './notifications/notification-message/notification-message.component';
import {LoginguardGuard} from "../authentication/loginguard.guard";
import {ListBugsComponent} from "../bugs/list-bugs/list-bugs.component";

const notificationRoutes: Routes = [
  {path: 'notification', component: NotificationsComponent, canActivate: [LoginguardGuard]},
  {path: 'filterBugById/:id', component: ListBugsComponent, canActivate: [LoginguardGuard]},
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
    RouterModule.forChild(notificationRoutes),
    CommonModule,
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
    MatSelectModule,
    MatCardModule,
    MatExpansionModule,
    MatGridListModule
  ],
  declarations: [NotificationsComponent,

    NotificationMessageComponent],
  exports: [
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
   NotificationMessageComponent
  ],
})
export class NotificationModule { }
