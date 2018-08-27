import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {UserData} from "./register-user/register-user.component";
import {TranslateService} from "@ngx-translate/core";


@Injectable({
  providedIn: 'root'
})
export class UsermanagementService {

  constructor(private http: HttpClient) { }

  baseURL = 'http://localhost:8080/jbugs/rest';
  getAllUsers(){

    return this.http.get(this.baseURL + '/manage-users/get-all-users');
  }

  activateUser(username){
    const params = new HttpParams()
      .set('username', username);
    return this.http.get(this.baseURL + '/manage-users/activate-user', {params});

  }

  deactivateUser(username){
    const params = new HttpParams()
      .set('username', username);
    return this.http.get(this.baseURL + '/manage-users/deactivate-user', {params});
  }

  registerUser(userData: UserData) {
    return this.http.post(this.baseURL + '/manage-users/register-user',
      userData,
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/json'}
        )
      });
  }

  updateUser(userData: UserData) {
    return this.http.post(this.baseURL + '/manage-users/update-user', userData,
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/json'}
        )
      })
  }


  getRolesOfUser(username: string) {
    const params = new HttpParams()
      .set('username', username);
    return this.http.get(this.baseURL + '/manage-users/get-roles-of-user', {params});
  }

  addRoleToUser(username: string, roleType: string) {
    const params = new HttpParams()
      .set('username', username)
      .set('roleType', roleType);
    return this.http.get(this.baseURL + '/manage-permissions/add-role-to-user', {params});
  }

  revokeRoleFromUser(username: string, roleType: string) {
    const params = new HttpParams()
      .set('username', username)
      .set('roleType', roleType);
    return this.http.get(this.baseURL + '/manage-permissions/revoke-role-from-user', {params});
  }
}


