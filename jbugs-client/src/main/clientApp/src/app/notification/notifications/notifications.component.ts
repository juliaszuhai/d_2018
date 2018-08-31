import {Component, OnInit} from '@angular/core';
import {NotificationData, NotificationService} from "../notification.service";
import {MatDialog} from "@angular/material";
import {NotificationMessageComponent} from "./notification-message/notification-message.component";
import {ActivatedRoute, Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";


@Component({
  selector: 'app-view-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  notifications: NotificationData[];
  displayedColumns = [
    'typeNotification',
    'targetDate',
    'message',
    'urlBug'
  ]

  constructor(private notificationService: NotificationService,
              public dialog: MatDialog, private route: ActivatedRoute,
              private router: Router,
              private translate: TranslateService) {
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

  getTypeOfNotification(notificationType) {
    let notification = '';
    if (notificationType == 'WELCOME_NEW_USER') {
      this.translate.get('notification.welcome').subscribe((res: string) => notification = res);
    } else if (notificationType == 'USER_UPDATED') {
      this.translate.get('notification.userUpdate').subscribe((res: string) => notification = res);
    } else if (notificationType == 'BUG_CREATED') {
      this.translate.get('notification.bugUpdate').subscribe((res: string) => notification = res);
    } else if (notificationType == 'BUG_CREATED') {
      this.translate.get('notification.bugCreated').subscribe((res: string) => notification = res);
    } else if (notificationType == 'BUG_CLOSED') {
      this.translate.get('notification.bugClosed').subscribe((res: string) => notification = res);
    } else if (notificationType == 'BUG_STATUS_UPDATED') {
      this.translate.get('notification.bugStatusUpdate').subscribe((res: string) => notification = res);
    } else if (notificationType == 'USER_DEACTIVATED') {
      this.translate.get('notification.userDeactivate').subscribe((res: string) => notification = res);
    }
    return notification;
  }

  getMessage(notification: NotificationData) {
    let message = '';

    let newdata = JSON.parse(notification.newData);
    console.log(newdata.username);
    if (notification.typeNotification == 'WELCOME_NEW_USER') {
      this.translate.get('notification.welcomeMessage').subscribe((res: string) => message = res);
      if (!(newdata === null)) {
        console.log('abks');
        message = message + newdata.username;
      }
    } else if (notification.typeNotification == 'USER_UPDATED') {
      this.translate.get('notification.userUpdate').subscribe((res: string) => message = res);
    } else if (notification.typeNotification == 'BUG_CREATED') {
      this.translate.get('notification.bugUpdate').subscribe((res: string) => message = res);
    } else if (notification.typeNotification == 'BUG_CREATED') {
      this.translate.get('notification.bugCreated').subscribe((res: string) => message = res);
    } else if (notification.typeNotification == 'BUG_CLOSED') {
      this.translate.get('notification.bugClosed').subscribe((res: string) => message = res);
    } else if (notification.typeNotification == 'BUG_STATUS_UPDATED') {
      this.translate.get('notification.bugStatusUpdate').subscribe((res: string) => message = res);
    } else if (notification.typeNotification == 'USER_DEACTIVATED') {
      this.translate.get('notification.userDeactivate').subscribe((res: string) => message = res);
    }
    return message;
  }

  goToBug(id) {
    this.router.navigate(['/filterBugById', id]);
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
