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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tg.univlome.epl.cookplusserver.entities.Recette;
import tg.univlome.epl.cookplusserver.services.RecetteService;
import tg.univlome.epl.cookplusserver.utils.SecurityContext;

import java.util.List;

/**
 * REST endpoint for recipes. Provides CRUD operations for recipe management.
 *
 * @author DAKEY Ahoefa Light
 */
@Path("/recettes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecetteResource {

    @Inject
    private RecetteService recetteService;

    @Inject
    private SecurityContext securityContext;

    @GET
    public Response findAll() {
        List<Recette> recettes = recetteService.findAll();
        return Response.ok(recettes).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Recette recette = recetteService.findById(id);
        recetteService.incrementerVues(id);
        return Response.ok(recette).build();
    }

    @GET
    @Path("/search")
    public Response searchByNom(@QueryParam("nom") String nom) {
        List<Recette> recettes = recetteService.findByNom(nom);
        return Response.ok(recettes).build();
    }

    @GET
    @Path("/utilisateur/{userId}")
    public Response findByUtilisateur(@PathParam("userId") Long userId) {
        List<Recette> recettes = recetteService.findByUtilisateurId(userId);
        return Response.ok(recettes).build();
    }

    @GET
    @Path("/public")
    public Response findPublic() {
        List<Recette> recettes = recetteService.findPublic();
        return Response.ok(recettes).build();
    }

    @GET
    @Path("/difficulte/{level}")
    public Response findByDifficulte(@PathParam("level") String difficulte) {
        List<Recette> recettes = recetteService.findByDifficulte(difficulte);
        return Response.ok(recettes).build();
    }

    @POST
    public Response create(Recette recette,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        Recette saved = recetteService.save(recette);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Recette recette,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        recetteService.findById(id);
        recette.setId(id);
        Recette updated = recetteService.save(recette);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        recetteService.deleteById(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/cout-total")
    public Response calculerCoutTotal(@PathParam("id") Long id) {
        double cout = recetteService.calculerCoutTotal(id);
        return Response.ok("{ \"cout\": " + cout + " }").build();
    }

    @GET
    @Path("/{id}/note-moyenne")
    public Response calculerNoteMoyenne(@PathParam("id") Long id) {
        double note = recetteService.calculerNoteMoyenne(id);
        return Response.ok("{ \"noteMoyenne\": " + note + " }").build();
    }
}
