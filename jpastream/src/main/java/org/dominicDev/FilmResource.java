package org.dominicDev;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class FilmResource {

    @GET
    @Path("/helloWorld")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return "Hello, World!";
    }

}
