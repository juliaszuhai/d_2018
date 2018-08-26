import {Component, OnInit} from '@angular/core';
import {PermissionManagementService} from "../permission-management.service";

@Component({
  selector: 'app-roles-and-permissions-view',
  templateUrl: './roles-and-permissions-view.component.html',
  styleUrls: ['./roles-and-permissions-view.component.css']
})
export class RolesAndPermissionsViewComponent implements OnInit {

  constructor(private permissionmanager: PermissionManagementService) {
  }

  roleDataSource: any;

  displayedColumns: string[] = [
    'type',
    'permissions',
  ];

  getRoles() {
    this.permissionmanager.getAllRoles()
      .subscribe(
        data => {
          this.roleDataSource = data;
          console.log(this.roleDataSource);
        }
      )
  }

  getStringPermissionList(permissionlist) {
    let permissionString = '';
    permissionlist.forEach(p => {
      permissionString += p.type + ', ';
    });
    return permissionString.slice(0, permissionString.length - 2);
  }

  ngOnInit() {
    this.getRoles();

  }

}
