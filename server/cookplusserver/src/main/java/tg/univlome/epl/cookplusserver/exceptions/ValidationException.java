package tg.univlome.epl.cookplusserver.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class ValidationException extends WebApplicationException {

    public ValidationException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(400, message))
                .build());
    }
}
