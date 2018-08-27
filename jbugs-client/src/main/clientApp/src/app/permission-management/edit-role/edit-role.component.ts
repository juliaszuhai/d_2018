import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {RolesAndPermissionsViewComponent} from "../roles-and-permissions-view/roles-and-permissions-view.component";
import {PermissionManagementService} from "../permission-management.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-edit-role',
  templateUrl: './edit-role.component.html',
  styleUrls: ['./edit-role.component.css']
})
export class EditRoleComponent implements OnInit {

  constructor(
    private translate: TranslateService,
    public dialogRef: MatDialogRef<RolesAndPermissionsViewComponent>,
    @Inject(MAT_DIALOG_DATA) public data,
    public permissionmanagement: PermissionManagementService,
  ) {
  }

  ownedPermissions: any;
  allPermissions: any;
  selectedPermission;

  syncPermissionsForRole() {
    console.log(this.data.type);
    this.permissionmanagement.getPermissionsForRole(this.data.type)
      .subscribe(perm => {
        this.ownedPermissions = perm;
      });
  }

  getAllPermissions() {
    this.permissionmanagement.getAllPermissions()
      .subscribe(perm => this.allPermissions = perm);
  }

  permissionToAdd(permission) {
    return !this.ownedPermissions.includes(permission);
  }

  addPermissionToRole() {
    this.permissionmanagement.addPermissionToRole(this.data.type, this.selectedPermission)
      .subscribe(() => {
          this.syncPermissionsForRole();
        }
      );
  }

  removePermissionFromRole() {
    this.permissionmanagement.removePermissionFromRole(this.data.type, this.selectedPermission)
      .subscribe(() => {
          this.syncPermissionsForRole();
        }
      );
  }

  ngOnInit() {
    this.syncPermissionsForRole();
    this.getAllPermissions();
  }

}
