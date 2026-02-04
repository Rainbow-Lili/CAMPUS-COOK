package tg.univlome.epl.cookplusserver.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * Exception thrown when an entity is not found.
 *
 * @author DAKEY Ahoefa Light
 */
public class EntityNotFoundException extends WebApplicationException {

    public EntityNotFoundException(String message) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(404, message))
                .build());
    }

    public EntityNotFoundException(String entityName, Long id) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(404, entityName + " avec l'ID " + id + " non trouvé(e)"))
                .build());
    }
}
