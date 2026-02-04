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
import tg.univlome.epl.cookplusserver.entities.Commentaire;
import tg.univlome.epl.cookplusserver.services.CommentaireService;
import tg.univlome.epl.cookplusserver.utils.SecurityContext;

import java.util.List;

@Path("/commentaires")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentaireResource {

    @Inject
    private CommentaireService commentaireService;

    @Inject
    private SecurityContext securityContext;

    @GET
    public Response findAll() {
        List<Commentaire> commentaires = commentaireService.findAll();
        return Response.ok(commentaires).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Commentaire commentaire = commentaireService.findById(id);
        return Response.ok(commentaire).build();
    }

    @GET
    @Path("/recette/{recetteId}")
    public Response findByRecetteIdAndActive(@PathParam("recetteId") Long recetteId) {
        List<Commentaire> commentaires = commentaireService.findByRecetteIdAndActive(recetteId);
        return Response.ok(commentaires).build();
    }

    @GET
    @Path("/utilisateur/{userId}")
    public Response findByUtilisateurId(@PathParam("userId") Long utilisateurId) {
        List<Commentaire> commentaires = commentaireService.findByUtilisateurId(utilisateurId);
        return Response.ok(commentaires).build();
    }

    @POST
    public Response create(Commentaire commentaire,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        Commentaire saved = commentaireService.save(commentaire);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Commentaire commentaire,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        commentaireService.findById(id);
        commentaire.setId(id);
        Commentaire updated = commentaireService.save(commentaire);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        commentaireService.deleteById(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/deactivate")
    public Response markAsInactive(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        commentaireService.markAsInactive(id);
        return Response.ok().build();
    }
}
