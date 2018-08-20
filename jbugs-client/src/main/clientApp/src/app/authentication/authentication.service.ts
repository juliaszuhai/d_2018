import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpRequest} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import * as moment from 'moment';
import {catchError, tap} from 'rxjs/operators';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Router} from '@angular/router';
import {now} from 'moment';

export interface UserLoginData {
  username: string;
  password: string;
}

export interface UserData {
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  baseURL = 'http://localhost:8080/jbugs/rest/';

  constructor(private http: HttpClient, private  router: Router) {
  }

  public getToken(): string {
    return localStorage.getItem('token');
  }

  validateUser(username: string, password: string) {

    let body = new URLSearchParams();
    body.set('username', username);
    body.set('password', password);

    return this.http.post<UserData>(this.baseURL + 'authenticate',
      body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      }).pipe(
      tap(res => this.setSession(res))
    );
  }


  private setSession(authResult) {

    const helper = new JwtHelperService();


    const decodedToken = helper.decodeToken(authResult.token);


    localStorage.setItem('token', authResult.token);
    localStorage.setItem('username', decodedToken.iss);
    localStorage.setItem('id_token', decodedToken.iss);
    localStorage.setItem('firstName', decodedToken.firstName);
    localStorage.setItem('lastName', decodedToken.lastName);
    localStorage.setItem('email', decodedToken.email);
    localStorage.setItem('phone', decodedToken.phone);
    localStorage.setItem('expires_at', decodedToken.exp);
  }

  public isLoggedIn() {

    if (!localStorage['expires_at']) {
      return false;
    }
    return this.getExpiration().isAfter(now());
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  public logout() {
    localStorage.removeItem('id_token');
    localStorage.removeItem('expires_at');
    localStorage.removeItem('username');
    localStorage.removeItem('firstName');
    localStorage.removeItem('lastName');
    localStorage.removeItem('email');
    localStorage.removeItem('phone');
  }

  getExpiration() {
    const time = localStorage['expires_at'];

    const correctSec = time * 1000;
    var expiresAt = new Date(correctSec);

    console.log(correctSec);
    console.log(expiresAt);

    return moment(expiresAt);
  }


}

