package ro.msg.edu.jbugs.userManagement.business.boundary;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/authenticate")
public class Authentication {

    @EJB
    private UserManagement userManagement;

    @GET
    public String getUsers() {
        return "qwer";
    }

    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) {
        try {

            // Authenticate the user using the credentials provided
            UserDTO authUser = userManagement.login(username, password);

            // Issue a token for the user
            String token = issueToken(username);

            // Return the token on the response
            return Response.ok(token).build();
        } catch(BusinessException e){
            System.out.println("-----------------------------\n\n\n EXCEPTION"+e.getExceptionCode()+" \n\n\n ------------");
            System.out.println();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
         catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private String issueToken(String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer("auth0")
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
            return "";
        }

    }


}
