package server.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class MovieWebException extends WebApplicationException {

    public MovieWebException(Response.Status status, String message) {
           super(Response.status(status).entity(message).type(MediaType.APPLICATION_JSON_TYPE).build());

    }
}
