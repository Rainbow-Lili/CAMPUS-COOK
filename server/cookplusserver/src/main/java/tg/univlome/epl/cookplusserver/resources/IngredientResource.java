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
import tg.univlome.epl.cookplusserver.entities.Ingredient;
import tg.univlome.epl.cookplusserver.services.IngredientService;
import tg.univlome.epl.cookplusserver.utils.SecurityContext;

import java.util.List;

@Path("/ingredients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IngredientResource {

    @Inject
    private IngredientService ingredientService;

    @Inject
    private SecurityContext securityContext;

    @GET
    public Response findAll() {
        List<Ingredient> ingredients = ingredientService.findAll();
        return Response.ok(ingredients).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Ingredient ingredient = ingredientService.findById(id);
        return Response.ok(ingredient).build();
    }

    @GET
    @Path("/nom/{nom}")
    public Response findByNom(@PathParam("nom") String nom) {
        Ingredient ingredient = ingredientService.findByNom(nom);
        return Response.ok(ingredient).build();
    }

    @GET
    @Path("/categorie/{categorie}")
    public Response findByCategorie(@PathParam("categorie") String categorie) {
        List<Ingredient> ingredients = ingredientService.findByCategorie(categorie);
        return Response.ok(ingredients).build();
    }

    @GET
    @Path("/disponibles")
    public Response findAvailable() {
        List<Ingredient> ingredients = ingredientService.findAvailable();
        return Response.ok(ingredients).build();
    }

    @POST
    public Response create(Ingredient ingredient,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        Ingredient saved = ingredientService.save(ingredient);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Ingredient ingredient,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        ingredientService.findById(id);
        ingredient.setId(id);
        Ingredient updated = ingredientService.save(ingredient);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        ingredientService.deleteById(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/toggle-disponibilite")
    public Response toggleDisponibilite(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        ingredientService.toggleDisponibilite(id);
        return Response.ok().build();
    }
}
