package ro.msg.edu.jbugs.userManagement.business.boundary;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagementController;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Path("/authenticate")
public class Authentication {

    @EJB
    private UserManagementController userManagement;

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
            String token = issueToken(authUser);

            // Return the token on the response
            return Response.ok("{\"token\": \""+token+"\"}").build();
        } catch(BusinessException e){
            System.out.println("-----------------------------\n\n\n EXCEPTION"+e.getExceptionCode()+" \n\n\n ------------");
            System.out.println();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
         catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }


    private String issueToken(UserDTO userDTO) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Berlin"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
        Date out = Date.from(tomorrowMidnight.atZone(ZoneId.systemDefault()).toInstant());

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer(userDTO.getUsername())
                    .withExpiresAt(out)
                    .withClaim("firstName", userDTO.getFirstName())
                    .withClaim("lastName", userDTO.getLastName())
                    .withClaim("email", userDTO.getEmail())
                    .withClaim("phone", userDTO.getPhoneNumber())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
            return "";
        }

    }


}
