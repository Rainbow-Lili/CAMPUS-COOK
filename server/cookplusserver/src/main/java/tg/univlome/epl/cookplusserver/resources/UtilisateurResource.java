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
import java.util.List;
import tg.univlome.epl.cookplusserver.entities.Utilisateur;
import tg.univlome.epl.cookplusserver.services.UtilisateurService;
import tg.univlome.epl.cookplusserver.utils.SecurityContext;


@Path("/utilisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UtilisateurResource {

    @Inject
    private UtilisateurService utilisateurService;

    @Inject
    private SecurityContext securityContext;

    @GET
    public Response findAll() {
        List<Utilisateur> utilisateurs = utilisateurService.findAll();
        return Response.ok(utilisateurs).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Utilisateur utilisateur = utilisateurService.findById(id);
        return Response.ok(utilisateur).build();
    }

    @GET
    @Path("/email/{email}")
    public Response findByEmail(@PathParam("email") String email) {
        Utilisateur utilisateur = utilisateurService.findByEmail(email);
        return Response.ok(utilisateur).build();
    }

    @GET
    @Path("/role/{role}")
    public Response findByRole(@PathParam("role") String role) {
        List<Utilisateur> utilisateurs = utilisateurService.findByRole(role);
        return Response.ok(utilisateurs).build();
    }

    @POST
    public Response create(Utilisateur utilisateur) {
        Utilisateur saved = utilisateurService.save(utilisateur);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Utilisateur utilisateur, 
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        utilisateurService.findById(id);
        utilisateur.setId(id);
        Utilisateur updated = utilisateurService.save(utilisateur);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id, 
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        utilisateurService.deleteById(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/activate")
    public Response activate(@PathParam("id") Long id, 
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        utilisateurService.activateUser(id);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}/deactivate")
    public Response deactivate(@PathParam("id") Long id, 
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        utilisateurService.deactivateUser(id);
        return Response.ok().build();
    }
}