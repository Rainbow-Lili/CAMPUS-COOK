package tg.univlome.epl.cookplusserver.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * Exception thrown when authentication fails.
 *
 * @author DAKEY Ahoefa Light
 */
public class AuthenticationException extends WebApplicationException {

    public AuthenticationException(String message) {
        super(Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorResponse(401, message))
                .build());
    }
}
