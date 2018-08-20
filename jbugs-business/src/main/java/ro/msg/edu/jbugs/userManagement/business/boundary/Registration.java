package ro.msg.edu.jbugs.userManagement.business.boundary;

import ro.msg.edu.jbugs.userManagement.business.control.UserManagementController;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/register")
public class Registration {

    @EJB
    private UserManagementController userManagement;

    @GET
    public String getUsers() {
        return "qwer";
    }

    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response registerUser(@FormParam("firstName") String firstName,
                                @FormParam("lastName") String lastName,
                                @FormParam("email") String email,
                                @FormParam("password") String password,
                                @FormParam("phone") String phoneNumber
                                ){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        userDTO.setPhoneNumber(phoneNumber);
        try {
            userManagement.createUser(userDTO);

            return Response.status(Response.Status.CREATED).build();
        } catch (BusinessException e){
//            return e.getExceptionCode().toString();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
