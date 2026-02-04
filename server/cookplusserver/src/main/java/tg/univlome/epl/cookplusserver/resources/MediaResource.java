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
import tg.univlome.epl.cookplusserver.entities.Media;
import tg.univlome.epl.cookplusserver.services.MediaService;
import tg.univlome.epl.cookplusserver.utils.SecurityContext;

import java.util.List;

@Path("/medias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MediaResource {

    @Inject
    private MediaService mediaService;

    @Inject
    private SecurityContext securityContext;

    @GET
    public Response findAll() {
        List<Media> medias = mediaService.findAll();
        return Response.ok(medias).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Media media = mediaService.findById(id);
        return Response.ok(media).build();
    }

    @GET
    @Path("/recette/{recetteId}")
    public Response findByRecetteId(@PathParam("recetteId") Long recetteId) {
        List<Media> medias = mediaService.findByRecetteId(recetteId);
        return Response.ok(medias).build();
    }

    @GET
    @Path("/ingredient/{ingredientId}")
    public Response findByIngredientId(@PathParam("ingredientId") Long ingredientId) {
        List<Media> medias = mediaService.findByIngredientId(ingredientId);
        return Response.ok(medias).build();
    }

    @POST
    public Response create(Media media,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        Media saved = mediaService.save(media);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Media media,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        mediaService.findById(id);
        media.setId(id);
        Media updated = mediaService.save(media);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        mediaService.deleteById(id);
        return Response.noContent().build();
    }
}
