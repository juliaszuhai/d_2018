import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PermissionManagementService {

  constructor(private http: HttpClient) {
  }

  baseURL = 'http://localhost:8080/jbugs/rest/manage-permissions';

  getAllPermissions() {
    return this.http.get(this.baseURL + '/get-all-permissions');
  }

  getAllRoles() {
    return this.http.get(this.baseURL + '/get-all-roles');
  }

  getPermissionsForRole(roleType) {
    const params = new HttpParams()
      .set('roleType', roleType);
    return this.http.get(this.baseURL + '/get-permissions-for-role', {params});
  }

  addPermissionToRole(roleType, permissionType) {
    const body = new URLSearchParams();
    body.set('roleType', roleType);
    body.set('permissionType', permissionType);
    return this.http.post(this.baseURL + '/add-permission-to-role', body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      });
  }

  removePermissionFromRole(roleType, permissionType) {
    const body = new URLSearchParams();
    body.set('roleType', roleType);
    body.set('permissionType', permissionType);
    return this.http.post(this.baseURL + '/revoke-permission-from-role', body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      });
  }
}
