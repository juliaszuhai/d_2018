import {Component, OnInit} from '@angular/core';
import {PermissionManagementService} from "../permission-management.service";
import {MatDialog} from "@angular/material";
import {EditRoleComponent} from "../edit-role/edit-role.component";

@Component({
  selector: 'app-roles-and-permissions-view',
  templateUrl: './roles-and-permissions-view.component.html',
  styleUrls: ['./roles-and-permissions-view.component.css']
})
export class RolesAndPermissionsViewComponent implements OnInit {

  constructor(private permissionmanager: PermissionManagementService,
              public dialog: MatDialog) {
  }

  roleDataSource: any;

  displayedColumns: string[] = [
    'type',
    'permissions',
    'addPermission',
  ];

  getRoles() {
    this.permissionmanager.getAllRoles()
      .subscribe(
        data => {
          this.roleDataSource = data;

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

  openRoleEditDialog(element): void {
    const dialogRef = this.dialog.open(EditRoleComponent, {
      width: '60%',
      data: {
        type: element.type,
        permissions: element.permissions,
      }
    });
    dialogRef.afterClosed().subscribe(result => {

      this.getRoles();
    });
  }

  ngOnInit() {
    this.getRoles();

  }

}
