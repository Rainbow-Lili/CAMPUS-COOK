package tg.univlome.epl.cookplusserver.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * Exception for business rule violations. Returns a 400 Bad Request response.
 *
 * @author DAKEY Ahoefa Light
 */
public class BusinessException extends WebApplicationException {

    public BusinessException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(400, message))
                .build());
    }
}
