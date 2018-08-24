import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {UserRegisterData} from "./register-user/register-user.component";




@Injectable({
  providedIn: 'root'
})
export class UsermanagementService {

  constructor(private http: HttpClient) { }
  baseURL = 'http://localhost:8080/jbugs/rest/manage-users';
  getAllUsers(){

    return this.http.get(this.baseURL + '/get-all-users');
  }

  activateUser(username){
    const params = new HttpParams()
      .set('username', username);
    return this.http.get(this.baseURL+'/activate-user', {params});

  }

  deactivateUser(username){
    const params = new HttpParams()
      .set('username', username);
    return this.http.get(this.baseURL+'/deactivate-user', {params});
  }

  registerUser(userData: UserRegisterData){
    return this.http.post(this.baseURL+'/register-user',
      userData,
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/json'}
        )
      });
  }

}


