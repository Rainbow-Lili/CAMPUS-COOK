package tg.univlome.epl.cookplusserver.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tg.univlome.epl.cookplusserver.entities.Notification;
import tg.univlome.epl.cookplusserver.services.NotificationService;
import tg.univlome.epl.cookplusserver.utils.SecurityContext;

import java.util.List;

@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    @Inject
    private NotificationService notificationService;

    @Inject
    private SecurityContext securityContext;

    @GET
    public Response findAll() {
        List<Notification> notifications = notificationService.findAll();
        return Response.ok(notifications).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Notification notification = notificationService.findById(id);
        return Response.ok(notification).build();
    }

    @GET
    @Path("/utilisateur/{userId}")
    public Response findByUtilisateurId(@PathParam("userId") Long utilisateurId) {
        List<Notification> notifications = notificationService.findByUtilisateurId(utilisateurId);
        return Response.ok(notifications).build();
    }

    @GET
    @Path("/utilisateur/{userId}/unread")
    public Response findUnreadByUtilisateurId(@PathParam("userId") Long utilisateurId) {
        List<Notification> notifications = notificationService.findUnreadByUtilisateurId(utilisateurId);
        return Response.ok(notifications).build();
    }

    @POST
    public Response create(Notification notification,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        Notification saved = notificationService.save(notification);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Notification notification,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        notificationService.findById(id);
        notification.setId(id);
        Notification updated = notificationService.save(notification);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        notificationService.deleteById(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/mark-as-read")
    public Response markAsRead(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        notificationService.markAsRead(id);
        return Response.ok().build();
    }
}
