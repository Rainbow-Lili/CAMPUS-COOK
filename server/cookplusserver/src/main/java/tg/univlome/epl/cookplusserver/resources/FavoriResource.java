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
import tg.univlome.epl.cookplusserver.entities.Favori;
import tg.univlome.epl.cookplusserver.services.FavoriService;
import tg.univlome.epl.cookplusserver.utils.SecurityContext;

import java.util.List;

@Path("/favoris")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FavoriResource {

    @Inject
    private FavoriService favoriService;

    @Inject
    private SecurityContext securityContext;

    @GET
    public Response findAll() {
        List<Favori> favoris = favoriService.findAll();
        return Response.ok(favoris).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Favori favori = favoriService.findById(id);
        return Response.ok(favori).build();
    }

    @GET
    @Path("/utilisateur/{userId}")
    public Response findByUtilisateurId(@PathParam("userId") Long utilisateurId) {
        List<Favori> favoris = favoriService.findByUtilisateurId(utilisateurId);
        return Response.ok(favoris).build();
    }

    @GET
    @Path("/utilisateur/{userId}/recette/{recetteId}")
    public Response findByUtilisateurIdAndRecetteId(@PathParam("userId") Long utilisateurId,
            @PathParam("recetteId") Long recetteId) {
        Favori favori = favoriService.findByUtilisateurIdAndRecetteId(utilisateurId, recetteId);
        return Response.ok(favori).build();
    }

    @POST
    public Response create(Favori favori,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        Favori saved = favoriService.save(favori);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Favori favori,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        favoriService.findById(id);
        favori.setId(id);
        Favori updated = favoriService.save(favori);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        favoriService.deleteById(id);
        return Response.noContent().build();
    }
}
