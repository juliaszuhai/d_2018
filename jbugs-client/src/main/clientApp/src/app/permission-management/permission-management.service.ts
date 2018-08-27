import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
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
    const params = new HttpParams()
      .set('roleType', roleType)
      .set('permissionType', permissionType);
    return this.http.get(this.baseURL + '/add-permission-to-role', {params});
  }

  removePermissionFromRole(roleType, permissionType) {
    const params = new HttpParams()
      .set('roleType', roleType)
      .set('permissionType', permissionType);
    return this.http.get(this.baseURL + '/revoke-permission-from-role', {params});
  }
}
