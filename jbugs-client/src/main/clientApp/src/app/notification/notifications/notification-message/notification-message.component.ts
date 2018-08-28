import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {RolesAndPermissionsViewComponent} from "../../../permission-management/roles-and-permissions-view/roles-and-permissions-view.component";
import {NotificationsComponent} from "../notifications.component";

@Component({
  selector: 'app-notification-message',
  templateUrl: './notification-message.component.html',
  styleUrls: ['./notification-message.component.css']
})
export class NotificationMessageComponent implements OnInit {

  constructor( public dialogRef: MatDialogRef<NotificationsComponent>,
               @Inject(MAT_DIALOG_DATA) public data) { }

  ngOnInit() {
  }

  getmessage(){

  }

}
