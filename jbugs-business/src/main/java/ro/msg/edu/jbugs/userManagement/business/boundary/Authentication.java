package ro.msg.edu.jbugs.userManagement.business.boundary;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.Gson;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagementController;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;

import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;

import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Path("/authenticate")

public class Authentication {

    @EJB
    private UserManagementController userManagement;


    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password,@Context SecurityContext securityContext) {
        try {

            UserDTO authUser = userManagement.login(username, password);
           User user = userManagement.getUserForUsername(username);
            String token = issueToken(user);
            return Response.ok("{\"token\": \""+token+"\"}").build();
        } catch(BusinessException e){
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getExceptionCode().getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    private String issueToken(User user) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Bucharest"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
        Date out = Date.from(tomorrowMidnight.atZone(ZoneId.systemDefault()).toInstant());

        List<Role> userRoles = user.getRoles();
        String rolesJson = new Gson().toJson(user.getRoles());


        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer(user.getUsername())
                    .withExpiresAt(out)
                    .withClaim("firstName", user.getFirstName())
                    .withClaim("lastName", user.getLastName())
                    .withClaim("email", user.getEmail())
                    .withClaim("phone", user.getPhoneNumber())
                    .withClaim("role", rolesJson)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
            return "";
        }

    }


}
