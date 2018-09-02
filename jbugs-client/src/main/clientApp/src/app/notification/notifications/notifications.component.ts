import {Component, OnInit} from '@angular/core';
import {MessageNotification, NotificationData, NotificationService, UpdatedUser} from "../notification.service";
import {MatDialog} from "@angular/material";
import {ActivatedRoute, Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";


@Component({
  selector: 'app-view-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  username: string;
  notifications: NotificationData[];
  usersUpdated: UpdatedUser[] = [];
  messageNotification: MessageNotification;
  displayedColumns: string[] = [
    'updatedData',
    'oldData',
    'newData'
  ];
  updatedUser: NotificationData;
  constructor(private notificationService: NotificationService,
              public dialog: MatDialog, private route: ActivatedRoute,
              private router: Router,
              private translate: TranslateService) {

    this.messageNotification = {
      subtitleMessage: '',
      oldUser: null,
      newUser: null,
      content: ''
    }

  }

  ngOnInit() {
    this.username = localStorage['username'];
    this.getNotifications(this.username);

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

  getTitle(notificationData: NotificationData) {
    let notificationTitle = '';
    let newUserData = JSON.parse(notificationData.newData);

    if (notificationData.typeNotification == 'WELCOME_NEW_USER') {
      this.translate.get('notification.welcomeMessage').subscribe((res: string) => notificationTitle = res);
      if (!(newUserData === null)) {
        notificationTitle = notificationTitle + newUserData.firstName;
        console.log(newUserData.firstName);
      }
    } else if (notificationData.typeNotification == 'USER_UPDATED') {
      if (newUserData.username === this.username) {
        this.translate.get('notification.userUpdatedByMe').subscribe((res: string) => notificationTitle = res);
      } else {
        this.translate.get('notification.userUpdatedBySomeone').subscribe((res: string) => notificationTitle = res);
      }
    } else if (notificationData.typeNotification == 'BUG_CREATED') {
      this.translate.get('notification.bugUpdate').subscribe((res: string) => notificationTitle = res);
    } else if (notificationData.typeNotification == 'BUG_CREATED') {
      this.translate.get('notification.bugCreated').subscribe((res: string) => notificationTitle = res);
    } else if (notificationData.typeNotification == 'BUG_CLOSED') {
      this.translate.get('notification.bugClosed').subscribe((res: string) => notificationTitle = res);
    } else if (notificationData.typeNotification == 'BUG_STATUS_UPDATED') {
      this.translate.get('notification.bugStatusUpdate').subscribe((res: string) => notificationTitle = res);
    } else if (notificationData.typeNotification == 'USER_DEACTIVATED') {
      this.translate.get('notification.userDeactivate').subscribe((res: string) => notificationTitle = res);
    }
    return notificationTitle;
  }

  getContentMessage(notificationData: NotificationData) {
    let contentMessage = '';

    if (notificationData.typeNotification == 'WELCOME_NEW_USER') {
      this.translate.get('notification.contentMessage').subscribe((res: string) => contentMessage = res);
    } else if (notificationData.typeNotification == 'USER_UPDATED') {
      this.translate.get('notification.userUpdateContent').subscribe((res: string) => contentMessage = res);
    } else if (notificationData.typeNotification == 'BUG_CREATED') {
      this.translate.get('notification.bugUpdate').subscribe((res: string) => contentMessage = res);
    } else if (notificationData.typeNotification == 'BUG_CREATED') {
      this.translate.get('notification.bugCreated').subscribe((res: string) => contentMessage = res);
    } else if (notificationData.typeNotification == 'BUG_CLOSED') {
      this.translate.get('notification.bugClosed').subscribe((res: string) => contentMessage = res);
    } else if (notificationData.typeNotification == 'BUG_STATUS_UPDATED') {
      this.translate.get('notification.bugStatusUpdate').subscribe((res: string) => contentMessage = res);
    } else if (notificationData.typeNotification == 'USER_DEACTIVATED') {
      this.translate.get('notification.userDeactivate').subscribe((res: string) => contentMessage = res);
    }
    return contentMessage;
  }

  // getMessageWelcome(notification: NotificationData) {
  //   this.messageNotification = {
  //     subtitleMessage: '',
  //     oldUser: null,
  //     newUser: null,
  //     content: ''
  //   }
  //   this.translate.get('notification.welcomeMessage').subscribe((res: string) => this.messageNotification.subtitleMessage = res);
  //   if (!(this.messageNotification.newUser === null)) {
  //     this.messageNotification.subtitleMessage = this.messageNotification.subtitleMessage + this.messageNotification.newUser.firstName;
  //     this.translate.get('notification.contentMessage').subscribe((res: string) => this.messageNotification.content = res);
  //   }
  //   this.messageNotification.newUser = JSON.parse(notification.newData);
  //   this.messageNotification.oldUser = JSON.parse(notification.oldData);
  //   return this.messageNotification;
  // }
  createUpdateContent(notificationData: NotificationData) {
    let content = {
      oldUser: null,
      newUser: null,
    };

    content.newUser = JSON.parse(notificationData.newData);
    content.oldUser = JSON.parse(notificationData.oldData);

    if (!(content.newUser === null) && !(content.oldUser === null)) {


      if (content.newUser.username === content.oldUser.username) {
        content.newUser.username = '';
        content.oldUser.username = '';
      }
      if (content.newUser.firstName === content.oldUser.firstName) {
        content.newUser.firstName = '';
        content.oldUser.firstName = '';
      }
      if (content.newUser.email === content.oldUser.email) {
        content.newUser.email = '';
        content.oldUser.email = '';
      }
      if (content.newUser.phoneNumber === content.oldUser.phoneNumber) {
        content.newUser.phoneNumber = '';
        content.oldUser.phoneNumber = '';
      }

    }
    return content;

  }


  getContent(notificationData: NotificationData) {
    let content = {

      oldUser: null,
      newUser: null,

    };
    if (notificationData.typeNotification == 'WELCOME_NEW_USER') {
      content.newUser = JSON.parse(notificationData.newData);
    } else if (notificationData.typeNotification == 'USER_UPDATED') {
      content.newUser = this.createUpdateContent(notificationData).newUser;
      content.oldUser = this.createUpdateContent(notificationData).oldUser;

    } else if (notificationData.typeNotification == 'BUG_CREATED') {

    } else if (notificationData.typeNotification == 'BUG_CREATED') {

    } else if (notificationData.typeNotification == 'BUG_CLOSED') {

    } else if (notificationData.typeNotification == 'BUG_STATUS_UPDATED') {

    } else if (notificationData.typeNotification == 'USER_DEACTIVATED') {

    }
    return content;
  }

  // getSubtitleMessage(notification: NotificationData) {
  //   if (notification.typeNotification == 'WELCOME_NEW_USER') {
  //     this.translate.get('notification.welcomeMessage').subscribe((res: string) => this.messageNotification.subtitleMessage = res);
  //     if (!(this.messageNotification.newUser === null)) {
  //       this.messageNotification.subtitleMessage = this.messageNotification.subtitleMessage + this.messageNotification.newUser.firstName;
  //       // this.translate.get('notification.contentMessage').subscribe((res: string) => this.messageNotification.content = res);
  //     }
  //   } else if (notification.typeNotification == 'USER_UPDATED') {
  //     if (this.messageNotification.newUser.username === this.username) {
  //       this.translate.get('notification.userUpdatedByMe').subscribe((res: string) => this.messageNotification.subtitleMessage = res);
  //     } else {
  //       this.translate.get('notification.userUpdatedBySomeone').subscribe((res: string) => this.messageNotification.subtitleMessage = res);
  //     }
  //   } else if (notification.typeNotification == 'BUG_CREATED') {
  //     this.translate.get('notification.bugUpdate').subscribe((res: string) => notification = res);
  //   } else if (notification.typeNotification == 'BUG_CREATED') {
  //     this.translate.get('notification.bugCreated').subscribe((res: string) => notification = res);
  //   } else if (notification.typeNotification == 'BUG_CLOSED') {
  //     this.translate.get('notification.bugClosed').subscribe((res: string) => notification = res);
  //   } else if (notification.typeNotification == 'BUG_STATUS_UPDATED') {
  //     this.translate.get('notification.bugStatusUpdate').subscribe((res: string) => notification = res);
  //   } else if (notification.typeNotification == 'USER_DEACTIVATED') {
  //     this.translate.get('notification.userDeactivate').subscribe((res: string) => notification = res);
  //   }
  //   return this.messageNotification.subtitleMessage;
  // }
  // getMessageForANotification(notification: NotificationData) {
  //
  //   if (notification.typeNotification == 'WELCOME_NEW_USER') {
  //     return this.getMessageWelcome(notification);
  //   } else if (notification.typeNotification == 'USER_UPDATED') {
  //     return this.getMessageUpdateUser(notification);
  //   } else if (notification.typeNotification == 'BUG_CREATED') {
  //     this.translate.get('notification.bugUpdate').subscribe((res: string) => notification = res);
  //   } else if (notification.typeNotification == 'BUG_CREATED') {
  //     this.translate.get('notification.bugCreated').subscribe((res: string) => notification = res);
  //   } else if (notification.typeNotification == 'BUG_CLOSED') {
  //     this.translate.get('notification.bugClosed').subscribe((res: string) => notification = res);
  //   } else if (notification.typeNotification == 'BUG_STATUS_UPDATED') {
  //     this.translate.get('notification.bugStatusUpdate').subscribe((res: string) => notification = res);
  //   } else if (notification.typeNotification == 'USER_DEACTIVATED') {
  //     this.translate.get('notification.userDeactivate').subscribe((res: string) => notification = res);
  //   }
  // }
  createDataForMessageUpdate(notification: NotificationData) {
    this.messageNotification.newUser = JSON.parse(notification.newData);
    this.messageNotification.oldUser = JSON.parse(notification.oldData);
    let numberColumn = 0;
    let numberrow = 0;

    this.usersUpdated.push({color: 'lightblue', cols: 1, text: 'ana', rows: 1})

  }


  getMessageUpdateUser(notification: NotificationData) {
    this.messageNotification = {
      subtitleMessage: '',
      oldUser: null,
      newUser: null,
      content: ''
    };
    this.messageNotification.newUser = JSON.parse(notification.newData);
    this.messageNotification.oldUser = JSON.parse(notification.oldData);
    if (this.messageNotification.newUser.username === this.username) {
      this.translate.get('notification.userUpdatedByMe').subscribe((res: string) => this.messageNotification.subtitleMessage = res);
    } else {
      this.translate.get('notification.userUpdatedBySomeone').subscribe((res: string) => this.messageNotification.subtitleMessage = res);
    }

    this.translate.get('notification.userUpdateContent').subscribe((res: string) => this.messageNotification.content = res);

    return this.messageNotification;
  }

  getMessage(notification: NotificationData) {
    let message = '';
    this.messageNotification.newUser = JSON.parse(notification.newData);

    if (notification.typeNotification == 'WELCOME_NEW_USER') {

    } else if (notification.typeNotification == 'USER_UPDATED') {
      this.getMessageUpdateUser(notification);
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
  }

  goToBug(id) {
    this.router.navigate(['/filterBugById', id]);
  }

  // viewMessage(element): void {
  //   const dialogRef = this.dialog.open(NotificationMessageComponent, {
  //     width: '60%',
  //     data: {
  //       message: element.message,
  //     }
  //   });
  //
  // }
}
