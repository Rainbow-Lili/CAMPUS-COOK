package tg.univlome.epl.cookplusserver.resources;

import java.util.List;

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
import tg.univlome.epl.cookplusserver.entities.Pack;
import tg.univlome.epl.cookplusserver.services.PackService;
import tg.univlome.epl.cookplusserver.utils.SecurityContext;

/**
 * REST endpoint for packs. Provides CRUD operations for pack management.
 *
 * @author DAKEY Ahoefa Light
 */
@Path("/packs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PackResource {

    @Inject
    private PackService packService;

    // @Inject
    // private SecurityContext securityContext;

    /**
     * Récupère tous les packs
     */
    @GET
    public Response findAll() {
        List<Pack> packs = packService.findAll();
        return Response.ok(packs).build();
    }

    /**
     * Récupère uniquement les packs disponibles
     */
    @GET
    @Path("/available")
    public Response findAvailable() {
        List<Pack> packs = packService.findAvailable();
        return Response.ok(packs).build();
    }

    /**
     * Récupère les packs les plus vendus
     */
    @GET
    @Path("/top-selling")
    public Response findTopSelling(@QueryParam("limit") Integer limit) {
        int maxResults = limit != null && limit > 0 ? limit : 10;
        List<Pack> packs = packService.findTopSelling(maxResults);
        return Response.ok(packs).build();
    }

    /**
     * Récupère les packs les mieux notés
     */
    @GET
    @Path("/top-rated")
    public Response findTopRated(@QueryParam("limit") Integer limit) {
        int maxResults = limit != null && limit > 0 ? limit : 10;
        List<Pack> packs = packService.findTopRated(maxResults);
        return Response.ok(packs).build();
    }

    /**
     * Récupère un pack par son ID
     */
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Pack pack = packService.findById(id);
        return Response.ok(pack).build();
    }

    /**
     * Récupère les packs d'une boutique
     */
    @GET
    @Path("/boutique/{boutiqueId}")
    public Response findByBoutiqueId(@PathParam("boutiqueId") Long boutiqueId) {
        List<Pack> packs = packService.findByBoutiqueId(boutiqueId);
        return Response.ok(packs).build();
    }

    /**
     * Récupère les packs associés à une recette
     */
    @GET
    @Path("/recette/{recetteId}")
    public Response findByRecetteId(@PathParam("recetteId") Long recetteId) {
        List<Pack> packs = packService.findByRecetteId(recetteId);
        return Response.ok(packs).build();
    }

    /**
     * Recherche des packs par nom
     */
    @GET
    @Path("/search")
    public Response searchByNom(@QueryParam("nom") String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Le paramètre 'nom' est requis\"}")
                    .build();
        }
        List<Pack> packs = packService.searchByNom(nom);
        return Response.ok(packs).build();
    }

    /**
     * Recherche des packs par plage de prix
     */
    @GET
    @Path("/price-range")
    public Response findByPriceRange(
            @QueryParam("min") Double minPrix,
            @QueryParam("max") Double maxPrix) {
        if (minPrix == null || maxPrix == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Les paramètres 'min' et 'max' sont requis\"}")
                    .build();
        }
        List<Pack> packs = packService.findByPriceRange(minPrix, maxPrix);
        return Response.ok(packs).build();
    }

    /**
     * Crée un nouveau pack
     */
    @POST
    @Path("/boutique/{boutiqueId}")
    public Response create(@PathParam("boutiqueId") Long boutiqueId,
            Pack pack,
            @HeaderParam("Authorization") String authHeader) {
        // securityContext.validateFournisseurOrAdminRole(authHeader);
        Pack saved = packService.create(pack, boutiqueId);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    /**
     * Met à jour un pack existant
     */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Pack pack,
            @HeaderParam("Authorization") String authHeader) {
        // Long userId = securityContext.extractUserIdFromToken(authHeader);
        
        // Vérifier que l'utilisateur est le propriétaire ou un admin
        // if (!packService.isPackOwner(id, userId)) {
        //     securityContext.validateAdminRole(authHeader);
        // }
        
        Pack updated = packService.update(id, pack);
        return Response.ok(updated).build();
    }

    /**
     * Supprime un pack
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        // Long userId = securityContext.extractUserIdFromToken(authHeader);
        
        // Vérifier que l'utilisateur est le propriétaire ou un admin
        // if (!packService.isPackOwner(id, userId)) {
        //     securityContext.validateAdminRole(authHeader);
        // }
        
        packService.deleteById(id);
        return Response.noContent().build();
    }

    /**
     * Active/désactive un pack
     */
    @PUT
    @Path("/{id}/toggle-disponibilite")
    public Response toggleDisponibilite(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        // Long userId = securityContext.extractUserIdFromToken(authHeader);
        
        // Vérifier que l'utilisateur est le propriétaire ou un admin
        // if (!packService.isPackOwner(id, userId)) {
        //     securityContext.validateAdminRole(authHeader);
        // }
        
        packService.toggleDisponibilite(id);
        return Response.ok("{\"message\": \"Disponibilité du pack modifiée\"}").build();
    }

    /**
     * Met à jour le stock d'un pack
     */
    @PUT
    @Path("/{id}/stock")
    public Response updateStock(@PathParam("id") Long id,
            @QueryParam("quantite") Integer quantite,
            @HeaderParam("Authorization") String authHeader) {
        if (quantite == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Le paramètre 'quantite' est requis\"}")
                    .build();
        }
        
        // Long userId = securityContext.extractUserIdFromToken(authHeader);
        
        // Vérifier que l'utilisateur est le propriétaire ou un admin
        // if (!packService.isPackOwner(id, userId)) {
        //     securityContext.validateAdminRole(authHeader);
        // }
        
        packService.updateStock(id, quantite);
        return Response.ok("{\"message\": \"Stock mis à jour\"}").build();
    }

    /**
     * Récupère les packs par catégorie
     */
    @GET
    @Path("/categorie/{categorie}")
    public Response findByCategorie(@PathParam("categorie") String categorie) {
        List<Pack> packs = packService.findByCategorie(categorie);
        return Response.ok(packs).build();
    }

    /**
     * Récupère les packs par budget
     */
    @GET
    @Path("/budget/{budget}")
    public Response findByBudget(@PathParam("budget") String budget) {
        List<Pack> packs = packService.findByBudget(budget);
        return Response.ok(packs).build();
    }

    /**
     * Récupère les packs par catégorie et budget
     */
    @GET
    @Path("/categorie/{categorie}/budget/{budget}")
    public Response findByCategorieAndBudget(
            @PathParam("categorie") String categorie,
            @PathParam("budget") String budget) {
        List<Pack> packs = packService.findByCategorieAndBudget(categorie, budget);
        return Response.ok(packs).build();
    }

    /**
     * Récupère les packs par type
     */
    @GET
    @Path("/type/{typePack}")
    public Response findByTypePack(@PathParam("typePack") String typePack) {
        List<Pack> packs = packService.findByTypePack(typePack);
        return Response.ok(packs).build();
    }

    /**
     * Traite un achat de pack (décrémente le stock, incrémente les ventes)
     */
    @POST
    @Path("/{id}/purchase")
    public Response processPurchase(@PathParam("id") Long id,
            @QueryParam("quantite") Integer quantite,
            @HeaderParam("Authorization") String authHeader) {
        // securityContext.validateAndGetPayload(authHeader);
        
        if (quantite == null || quantite <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"La quantité doit être positive\"}")
                    .build();
        }
        
        packService.processPurchase(id, quantite);
        return Response.ok("{\"message\": \"Achat traité avec succès\"}").build();
    }
}
