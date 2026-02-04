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
import tg.univlome.epl.cookplusserver.entities.RecetteIngredient;
import tg.univlome.epl.cookplusserver.services.RecetteIngredientService;
import tg.univlome.epl.cookplusserver.utils.SecurityContext;

import java.util.List;

@Path("/recette-ingredients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecetteIngredientResource {

    @Inject
    private RecetteIngredientService recetteIngredientService;

    @Inject
    private SecurityContext securityContext;

    @GET
    public Response findAll() {
        List<RecetteIngredient> recetteIngredients = recetteIngredientService.findAll();
        return Response.ok(recetteIngredients).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        RecetteIngredient recetteIngredient = recetteIngredientService.findById(id);
        return Response.ok(recetteIngredient).build();
    }

    @GET
    @Path("/recette/{recetteId}")
    public Response findByRecetteId(@PathParam("recetteId") Long recetteId) {
        List<RecetteIngredient> recetteIngredients = recetteIngredientService.findByRecetteId(recetteId);
        return Response.ok(recetteIngredients).build();
    }

    @GET
    @Path("/ingredient/{ingredientId}")
    public Response findByIngredientId(@PathParam("ingredientId") Long ingredientId) {
        List<RecetteIngredient> recetteIngredients = recetteIngredientService.findByIngredientId(ingredientId);
        return Response.ok(recetteIngredients).build();
    }

    @POST
    public Response create(RecetteIngredient recetteIngredient,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        RecetteIngredient saved = recetteIngredientService.save(recetteIngredient);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, RecetteIngredient recetteIngredient,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        recetteIngredientService.findById(id);
        recetteIngredient.setId(id);
        RecetteIngredient updated = recetteIngredientService.save(recetteIngredient);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        recetteIngredientService.deleteById(id);
        return Response.noContent().build();
    }
}
