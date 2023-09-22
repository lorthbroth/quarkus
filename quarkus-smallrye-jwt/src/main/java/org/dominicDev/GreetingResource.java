package org.dominicDev;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.HashMap;
import java.util.Map;

@Path("/hello")
public class GreetingResource {

    @Inject
    JsonWebToken jsonWebToken;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        return "Hello DominicDev";
    }

    @GET
    @Path("secure-roles")
    @RolesAllowed({"user", "Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloRolesAllowed(@Context SecurityContext context) {
        Map<String, String> res = new HashMap<>();
        res.put("message", getResponseString(context) + ", birthdate: " + jsonWebToken.getClaim("birthdate").toString());
        return Response.status(Response.Status.OK).entity(res).build();
    }

    public String getResponseString(SecurityContext context) {
        String name;
        if (context.getUserPrincipal() == null) {
            name = "anonymous";
        }else if (!context.getUserPrincipal().getName().equals(jsonWebToken.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        }else {
            name = context.getUserPrincipal().getName();
        }
        return String.format("hello + %s," + " isHttps: $s," + " authScheme: %s," + " hasJWT: %s", name, context.isSecure(), context.getAuthenticationScheme(), hasJWT());
    }

    private  boolean hasJWT() {
        return jsonWebToken.getClaimNames() !=null;
    }
}
