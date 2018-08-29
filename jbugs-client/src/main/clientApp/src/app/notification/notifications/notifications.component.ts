import {Component, OnInit} from '@angular/core';
import {NotificationService} from "../notification.service";
import {MatDialog} from "@angular/material";
import {NotificationMessageComponent} from "./notification-message/notification-message.component";
import {ActivatedRoute, Router} from "@angular/router";


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
    'urlBug'
  ]

  constructor(private notificationService: NotificationService,
              public dialog: MatDialog,private route: ActivatedRoute,
              private router: Router) {
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

  goToBug(id){
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
