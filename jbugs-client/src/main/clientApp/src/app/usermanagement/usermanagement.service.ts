import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {UserData} from "./register-user/register-user.component";


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
    const body = new URLSearchParams();
    body.set('username', username);
    return this.http.post(this.baseURL + '/manage-users/activate-user', body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      });

  }

  deactivateUser(username){
    const body = new URLSearchParams();
    body.set('username', username);
    return this.http.post(this.baseURL + '/manage-users/deactivate-user', body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      });
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
    let body = new URLSearchParams();
    body.set('username', username);
    body.set('roleType', roleType);
    return this.http.post(this.baseURL + '/manage-permissions/add-role-to-user',
      body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      });
  }

  revokeRoleFromUser(username: string, roleType: string) {
    const body = new URLSearchParams();
    body.set('username', username);
    body.set('roleType', roleType);
    return this.http.post(this.baseURL + '/manage-permissions/revoke-role-from-user', body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      });
  }
}


