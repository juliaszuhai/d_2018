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
  statusUpdated: boolean;
  username: string;
  notifications: NotificationData[];
  messageNotification: MessageNotification;
  updatedUser: UpdatedUser[] = [];

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
    this.statusUpdated = false;
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
    } else if (notificationType == 'BUG_UPDATED') {
      this.translate.get('notification.bugUpdate').subscribe((res: string) => notification = res);
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
    let newData = JSON.parse(notificationData.newData);
    let titleBug = '';
    let titleBugStatus = '';
    if (notificationData.typeNotification == 'WELCOME_NEW_USER') {
      this.translate.get('notification.welcomeMessage').subscribe((res: string) => notificationTitle = res);
      if (!(newData === null)) {
        notificationTitle = notificationTitle + newData.firstName;
        console.log(newData.firstName);
      }
    } else if (notificationData.typeNotification == 'USER_UPDATED') {
      if (newData.username === this.username) {
        this.translate.get('notification.userUpdatedByMe').subscribe((res: string) => notificationTitle = res);
      } else {
        this.translate.get('notification.userUpdatedBySomeone').subscribe((res: string) => notificationTitle = res);
      }
    } else if (notificationData.typeNotification == 'BUG_CREATED') {
      this.translate.get('notification.bugTitleCreated').subscribe((res: string) => notificationTitle = res);

    } else if (notificationData.typeNotification == 'BUG_UPDATED') {
      this.translate.get('notification.bugTitleUpdated').subscribe((res: string) => notificationTitle = res);

    } else if (notificationData.typeNotification == 'BUG_CLOSED') {
      this.translate.get('notification.bug').subscribe((res: string) => notificationTitle = res);
      notificationTitle = notificationTitle + newData.title;
      this.translate.get('notification.titleBugClosed').subscribe((res: string) => titleBug = res);
      notificationTitle = notificationTitle + titleBug;
    } else if (notificationData.typeNotification == 'BUG_STATUS_UPDATED') {
      this.translate.get('notification.status').subscribe((res: string) => notificationTitle = res);
      notificationTitle = notificationTitle + newData.title;
      this.translate.get('notification.titleStatus').subscribe((res: string) => titleBugStatus = res);
      notificationTitle = notificationTitle + titleBugStatus;
    } else if (notificationData.typeNotification == 'USER_DEACTIVATED') {
      this.translate.get('notification.user').subscribe((res: string) => notificationTitle = res);
      notificationTitle = notificationTitle + newData.firstName;
      let messageTitle = '';
      this.translate.get('notification.titleUserDeactivate').subscribe((res: string) => messageTitle = res);
      notificationTitle = notificationTitle + messageTitle;
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
      this.translate.get('notification.bugContentMessage').subscribe((res: string) => contentMessage = res);
    } else if (notificationData.typeNotification == 'BUG_UPDATED') {
      this.translate.get('notification.bugContentMessage').subscribe((res: string) => contentMessage = res);
    } else if (notificationData.typeNotification == 'BUG_CLOSED') {
      this.translate.get('notification.bugContentMessage').subscribe((res: string) => contentMessage = res);
    } else if (notificationData.typeNotification == 'BUG_STATUS_UPDATED') {
      this.translate.get('notification.bugContentMessage').subscribe((res: string) => contentMessage = res);
    } else if (notificationData.typeNotification == 'USER_DEACTIVATED') {
      this.translate.get('notification.userDeactivateContent').subscribe((res: string) => contentMessage = res);
    }
    return contentMessage;
  }


  createUpdateContent(notificationData: NotificationData) {
    let content = {
      oldUser: null,
      newUser: null,
      newBug: null,
      oldBug: null,
      urlBug: ''
    };
    let userContent = {
      oldData: {
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: ''
      },
      newData: {
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: ''
      }
    };

    content.newUser = JSON.parse(notificationData.newData);
    content.oldUser = JSON.parse(notificationData.oldData);

    if (!(content.newUser === null) && !(content.oldUser === null)) {


      if (content.newUser.username === content.oldUser.username) {
        content.newUser.username = '';
        content.oldUser.username = '';
      } else {
        userContent.oldData.username = content.oldUser.username;
        userContent.newData.username = content.newUser.username;
      }
      if (content.newUser.firstName === content.oldUser.firstName) {
        content.newUser.firstName = '';
        content.oldUser.firstName = '';
      } else {
        userContent.oldData.firstName = content.oldUser.firstName;
        userContent.newData.firstName = content.newUser.firstName;
      }
      if (content.newUser.lastName === content.oldUser.lastName) {
        content.newUser.lastName = '';
        content.oldUser.lastName = '';
      } else {
        userContent.oldData.lastName = content.oldUser.lastName;
        userContent.newData.lastName = content.newUser.lastName;
      }
      if (content.newUser.email === content.oldUser.email) {
        content.newUser.email = '';
        content.oldUser.email = '';
      } else {
        userContent.oldData.email = content.oldUser.email;
        userContent.newData.email = content.newUser.email;
      }
      if (content.newUser.phoneNumber === content.oldUser.phoneNumber) {
        content.newUser.phoneNumber = '';
        content.oldUser.phoneNumber = '';
      } else {
        userContent.oldData.phoneNumber = content.oldUser.phoneNumber;
        userContent.newData.phoneNumber = content.newUser.phoneNumber;
      }

    }
    this.updatedUser.push(userContent);
    return content;

  }


  getContent(notificationData: NotificationData) {
    let content = {
      newBug: {
        title: '',
        description: '',
        version: '',
        targetDate: null,
        status: '',
        fixedVersion: '',
        severity: '',
        createdByUser: {
          username: '',
          firstName: '',
          lastName: '',
          email: '',
          phoneNumber: ''
        },
        assignedTo: {
          username: '',
          firstName: '',
          lastName: '',
          email: '',
          phoneNumber: ''
        },
        fileName: '',
      },
      oldUser: null,
      newUser: null,
      oldBug: {
        title: '',
        description: '',
        version: '',
        targetDate: null,
        status: '',
        fixedVersion: '',
        severity: '',
        createdByUser: {
          username: '',
          firstName: '',
          lastName: '',
          email: '',
          phoneNumber: ''
        },
        assignedTo: {
          username: '',
          firstName: '',
          lastName: '',
          email: '',
          phoneNumber: ''
        },
        fileName: '',
      },
      urlBug: ''

    };
    if (notificationData.typeNotification == 'WELCOME_NEW_USER') {
      content.newUser = JSON.parse(notificationData.newData);
    } else if (notificationData.typeNotification == 'USER_UPDATED') {
      content = this.createUpdateContent(notificationData);

    } else if (notificationData.typeNotification == 'BUG_CREATED') {
      content.newBug = JSON.parse(notificationData.newData);
      if (!(notificationData.urlBug === undefined)) {
        content.urlBug = JSON.parse(notificationData.urlBug);
      }
    } else if (notificationData.typeNotification == 'BUG_UPDATED') {
      content.newBug = JSON.parse(notificationData.newData);
      if (!(notificationData.urlBug === undefined)) {
        content.urlBug = JSON.parse(notificationData.urlBug);
      }
    } else if (notificationData.typeNotification == 'BUG_CLOSED') {
      content.newBug = JSON.parse(notificationData.newData);
      if (!(notificationData.urlBug === undefined)) {
        content.urlBug = JSON.parse(notificationData.urlBug);
      }
    } else if (notificationData.typeNotification == 'BUG_STATUS_UPDATED') {
      this.statusUpdated = true;
      content.newBug = JSON.parse(notificationData.newData);
      content.oldBug = JSON.parse(notificationData.oldData);
      if (!(notificationData.urlBug === undefined)) {
        content.urlBug = JSON.parse(notificationData.urlBug);
      }


    } else if (notificationData.typeNotification == 'USER_DEACTIVATED') {
      content.newUser = JSON.parse(notificationData.newData);
    }
    return content;
  }

  getMessageUpdateUser(notification: NotificationData) {
    this.messageNotification = {
      subtitleMessage: '',
      oldUser: null,
      newUser: null,
      content: ''
    }
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


}
