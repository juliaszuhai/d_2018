import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import * as moment from 'moment';
import {tap} from 'rxjs/operators';
import {JwtHelperService} from '@auth0/angular-jwt';

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

  constructor(private http: HttpClient) {
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
    console.log(authResult);
    const expiresAt = moment().add(authResult.exp, 'second');
    const helper = new JwtHelperService();

    const decodedToken = helper.decodeToken(authResult.token);
    console.log(decodedToken);
    localStorage.setItem('username', decodedToken.iss);
    localStorage.setItem('id_token', decodedToken.iss);
    localStorage.setItem('firstName', decodedToken.firstName);
    localStorage.setItem('lastName', decodedToken.lastName);
    localStorage.setItem('email', decodedToken.email);
    localStorage.setItem('phone', decodedToken.phone);
    localStorage.setItem('expires_at', JSON.stringify(expiresAt.valueOf()));
  }

  public isLoggedIn() {
    return moment().isBefore(this.getExpiration());
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  public logout() {
    localStorage.removeItem('id_token');
    localStorage.removeItem('expires_at');
    localStorage.removeItem('username');
    localStorage.removeItem('firstName');
    localStorage.removeItem('email');
    localStorage.removeItem('phone');
  }

  getExpiration() {
    const expiration = localStorage.getItem('expires_at');
    const expiresAt = JSON.parse(expiration);
    return moment(expiresAt);
  }


}

