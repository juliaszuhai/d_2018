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
  baseURL = 'http://localhost:8080/jbugs/rest/manageusers';
  getAllUsers(){

    return this.http.get(this.baseURL + '/getallusers');
  }

  activateUser(username){
    const params = new HttpParams()
      .set('username', username);
    return this.http.get(this.baseURL+'/activateuser', {params});

  }

  deactivateUser(username){
    const params = new HttpParams()
      .set('username', username);
    return this.http.get(this.baseURL+'/deactivateuser', {params});
  }


}


