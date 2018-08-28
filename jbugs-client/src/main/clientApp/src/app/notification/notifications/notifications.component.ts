import {Component, OnInit} from '@angular/core';
import {NotificationData, NotificationService} from "../notification.service";
import {EditRoleComponent} from "../../permission-management/edit-role/edit-role.component";
import {MatDialog} from "@angular/material";
import {NotificationMessageComponent} from "./notification-message/notification-message.component";

@Component({
  selector: 'app-view-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  notifications: any
  displayedColumns=[
    'typeNotification',
    'targetDate',
    'message',
    'URLBug'
  ]

  constructor(private notificationService: NotificationService,
              public dialog: MatDialog,) {
  }

  ngOnInit() {
    const username = localStorage['username'];
    this.getNotifications(username);

    console.log(this.notifications);
  }

  getNotifications(username) {
    this.notificationService.getNotificationByUsername(username)
      .subscribe(
        data => {
          this.notifications = data;
          console.log(this.notifications);
        }
      )
  }

  viewMessage(element): void {
    const dialogRef = this.dialog.open(NotificationMessageComponent, {
      width: '60%',
      data: {
        message: element.message,
      }
    });

  }

}
