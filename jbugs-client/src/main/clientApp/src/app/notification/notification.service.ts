import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "../../../node_modules/@angular/common/http";

export interface NotificationData {
  dateSent: string,
  newData,
  oldData,
  urlBug: string,
  UserID: number,
  typeNotification: string;

}

export interface MessageNotification {
  subtitleMessage: string,
  content: string,
  oldUser,
  newUser
}

export interface UpdatedUser {
  color: string,
  cols: number,
  rows: number,
  text: string
}
@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private http: HttpClient) {  }

  baseURL = 'http://localhost:8080/jbugs/rest';

  getNotificationByUsername(username){

    const params = new HttpParams()
      .set('username', username);
    return this.http.get<NotificationData[]>(this.baseURL + '/manage-users/get-notification-of-user', {params});
  }
}
