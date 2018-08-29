import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "../../../node_modules/@angular/common/http";

export interface NotificationData {
  urlBug: string,
  message: string,
  targetDate: string,
  typeNotification: string;

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
    return this.http.get(this.baseURL + '/manage-users/get-notification-of-user', {params});
  }
}
