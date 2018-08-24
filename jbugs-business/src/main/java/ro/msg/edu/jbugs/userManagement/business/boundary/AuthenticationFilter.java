package ro.msg.edu.jbugs.userManagement.business.boundary;


import com.auth0.jwt.JWT;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.exceptions.ExceptionCode;

import ro.msg.edu.jbugs.userManagement.business.utils.Secured;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    /**
     * Extract the token from the request and validate it
     * @param requestContext the token from request
     */
    @Override
    public void filter(ContainerRequestContext requestContext) {

        // Get the HTTP Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {

            throw new NotAuthorizedException("Authorization header must be provided");

        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();


        try {

            validateToken(token);

        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }

        validateRequest(token, requestContext);

    }


    private void validateToken(String token) throws BusinessException {
        if (JWT.decode(token).getClaim("exp").asDate().compareTo(Date.from(Instant.now())) < 0) {
            throw new BusinessException(ExceptionCode.TOKEN_EXPIRED);
        }
    }

    private void validateRequest(String token, ContainerRequestContext requestContext) {
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<String> methodPermissions = extractPermission(resourceMethod);
        try {
            if (!methodPermissions.isEmpty()) {
                checkPermissions(methodPermissions, token);
            }
        } catch (BusinessException e) {
            requestContext.abortWith(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity(e.getExceptionCode())
                            .build());
        }
    }

    private List<String> extractPermission(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<String>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<String>();
            } else {
                String[] allowedPermissions = secured.value();
                return Arrays.asList(allowedPermissions);
            }
        }
    }

    private void checkPermissions(List<String> allowedPermissions, String token) throws BusinessException {

        List<String> userPermisions = getUserPermisions(token);

        if (!userPermisions.containsAll(allowedPermissions)) {
            throw new BusinessException(ExceptionCode.USER_PERMISSION_VALIDATION);
        }

    }

    private List<String> getUserPermisions(String token) {
        String jsonRoleUser = JWT.decode(token).getClaim("role").asString();
        Type collectionType = new TypeToken<List<Role>>() {
        }.getType();
        List<Role> userRoles = new Gson().fromJson(jsonRoleUser, collectionType);
        List<List<String>> userPermisions = userRoles
                .stream()
                .map(role -> {
                    List<Permission> permissions = role.getPermissions();
                    List<String> permisionType = permissions.stream()
                            .map(permission -> permission.getType())
                            .collect(Collectors.toList());
                    return permisionType;
                }).collect(Collectors.toList());

        List<String> userPermisionType = userPermisions.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return userPermisionType;
    }

}