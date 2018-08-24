import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";

export interface UserUpdateData{
  firstName: string,
  lastnName: string,
  username: string,
  active: boolean,
}


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


}


