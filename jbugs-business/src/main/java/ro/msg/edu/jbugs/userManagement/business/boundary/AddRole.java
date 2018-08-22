package ro.msg.edu.jbugs.userManagement.business.boundary;


import ro.msg.edu.jbugs.userManagement.business.control.PermissionManagementController;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagementController;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTOHelper;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/addrole")
public class AddRole {

    @EJB
    private PermissionManagementController permissionManagementController;

    @GET
    public void addRole(){
        permissionManagementController.addRoleToUser("ADM","doreld");

    }
}
