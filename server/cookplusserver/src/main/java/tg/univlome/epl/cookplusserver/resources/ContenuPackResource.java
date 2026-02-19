package tg.univlome.epl.cookplusserver.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import tg.univlome.epl.cookplusserver.entities.ContenuPack;
import tg.univlome.epl.cookplusserver.services.ContenuPackService;
import tg.univlome.epl.cookplusserver.services.PackService;
import tg.univlome.epl.cookplusserver.utils.SecurityContext;

/**
 * REST endpoint for pack contents. Provides CRUD operations for managing
 * ingredients in packs.
 *
 * @author DAKEY Ahoefa Light
 */
@Path("/contenu-packs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContenuPackResource {

    @Inject
    private ContenuPackService contenuPackService;

    @Inject
    private PackService packService;

    @Inject
    private SecurityContext securityContext;

    /**
     * Récupère un contenu de pack par son ID
     */
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        ContenuPack contenu = contenuPackService.findById(id);
        return Response.ok(contenu).build();
    }

    /**
     * Récupère tous les contenus d'un pack
     */
    @GET
    @Path("/pack/{packId}")
    public Response findByPackId(@PathParam("packId") Long packId) {
        List<ContenuPack> contenus = contenuPackService.findByPackId(packId);
        return Response.ok(contenus).build();
    }

    /**
     * Récupère tous les packs contenant un ingrédient spécifique
     */
    @GET
    @Path("/ingredient/{ingredientId}")
    public Response findByIngredientId(@PathParam("ingredientId") Long ingredientId) {
        List<ContenuPack> contenus = contenuPackService.findByIngredientId(ingredientId);
        return Response.ok(contenus).build();
    }

    /**
     * Ajoute un ingrédient à un pack
     */
    @POST
    @Path("/pack/{packId}/ingredient/{ingredientId}")
    public Response create(
            @PathParam("packId") Long packId,
            @PathParam("ingredientId") Long ingredientId,
            @QueryParam("quantite") Double quantite,
            @QueryParam("unite") String unite,
            @HeaderParam("Authorization") String authHeader) {
        
        Long userId = securityContext.extractUserIdFromToken(authHeader);
        
        // Vérifier que l'utilisateur est le propriétaire du pack ou un admin
        if (!packService.isPackOwner(packId, userId)) {
            securityContext.validateAdminRole(authHeader);
        }
        
        if (quantite == null || quantite <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"La quantité doit être positive\"}")
                    .build();
        }
        
        ContenuPack saved = contenuPackService.create(packId, ingredientId, quantite, unite);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    /**
     * Met à jour un contenu de pack
     */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id,
            ContenuPack contenuPack,
            @HeaderParam("Authorization") String authHeader) {
        
        ContenuPack existing = contenuPackService.findById(id);
        Long userId = securityContext.extractUserIdFromToken(authHeader);
        
        // Vérifier que l'utilisateur est le propriétaire du pack ou un admin
        if (!packService.isPackOwner(existing.getPack().getId(), userId)) {
            securityContext.validateAdminRole(authHeader);
        }
        
        ContenuPack updated = contenuPackService.update(id, contenuPack);
        return Response.ok(updated).build();
    }

    /**
     * Supprime un ingrédient d'un pack
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        
        ContenuPack existing = contenuPackService.findById(id);
        Long userId = securityContext.extractUserIdFromToken(authHeader);
        
        // Vérifier que l'utilisateur est le propriétaire du pack ou un admin
        if (!packService.isPackOwner(existing.getPack().getId(), userId)) {
            securityContext.validateAdminRole(authHeader);
        }
        
        contenuPackService.deleteById(id);
        return Response.noContent().build();
    }

    /**
     * Supprime tous les ingrédients d'un pack
     */
    @DELETE
    @Path("/pack/{packId}")
    public Response deleteByPackId(@PathParam("packId") Long packId,
            @HeaderParam("Authorization") String authHeader) {
        
        Long userId = securityContext.extractUserIdFromToken(authHeader);
        
        // Vérifier que l'utilisateur est le propriétaire du pack ou un admin
        if (!packService.isPackOwner(packId, userId)) {
            securityContext.validateAdminRole(authHeader);
        }
        
        contenuPackService.deleteByPackId(packId);
        return Response.noContent().build();
    }

    /**
     * Compte le nombre d'ingrédients dans un pack
     */
    @GET
    @Path("/pack/{packId}/count")
    public Response countByPackId(@PathParam("packId") Long packId) {
        int count = contenuPackService.countByPackId(packId);
        return Response.ok("{\"count\": " + count + "}").build();
    }

    /**
     * Calcule le coût total des ingrédients d'un pack
     */
    @GET
    @Path("/pack/{packId}/total-price")
    public Response calculateTotalPrice(@PathParam("packId") Long packId) {
        Double total = contenuPackService.calculateTotalPrice(packId);
        return Response.ok("{\"totalPrice\": " + total + "}").build();
    }

    /**
     * Calcule la marge et le pourcentage de marge d'un pack
     */
    @GET
    @Path("/pack/{packId}/margin")
    public Response calculateMargin(@PathParam("packId") Long packId) {
        Double margin = contenuPackService.calculateMargin(packId);
        Double percentage = contenuPackService.calculateMarginPercentage(packId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("margin", margin);
        result.put("marginPercentage", percentage);
        
        return Response.ok(result).build();
    }
}
