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
import tg.univlome.epl.cookplusserver.entities.Boutique;
import tg.univlome.epl.cookplusserver.services.BoutiqueService;
import tg.univlome.epl.cookplusserver.utils.SecurityContext;

/**
 * REST endpoint for boutiques. Provides CRUD operations for boutique
 * management.
 *
 * @author DAKEY Ahoefa Light
 */
@Path("/boutiques")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoutiqueResource {

    @Inject
    private BoutiqueService boutiqueService;

    // @Inject
    // private SecurityContext securityContext;

    /**
     * Récupère toutes les boutiques
     */
    @GET
    public Response findAll() {
        List<Boutique> boutiques = boutiqueService.findAll();
        return Response.ok(boutiques).build();
    }

    /**
     * Récupère uniquement les boutiques actives
     */
    @GET
    @Path("/active")
    public Response findActive() {
        List<Boutique> boutiques = boutiqueService.findActive();
        return Response.ok(boutiques).build();
    }

    /**
     * Récupère les boutiques les mieux notées
     */
    @GET
    @Path("/top-rated")
    public Response findTopRated(@QueryParam("limit") Integer limit) {
        int maxResults = limit != null && limit > 0 ? limit : 10;
        List<Boutique> boutiques = boutiqueService.findTopRated(maxResults);
        return Response.ok(boutiques).build();
    }

    /**
     * Récupère une boutique par son ID
     */
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Boutique boutique = boutiqueService.findById(id);
        return Response.ok(boutique).build();
    }

    /**
     * Récupère la boutique d'un fournisseur
     */
    @GET
    @Path("/fournisseur/{fournisseurId}")
    public Response findByFournisseurId(@PathParam("fournisseurId") Long fournisseurId) {
        Boutique boutique = boutiqueService.findByFournisseurId(fournisseurId);
        return Response.ok(boutique).build();
    }

    /**
     * Recherche des boutiques par nom
     */
    @GET
    @Path("/search")
    public Response searchByNom(@QueryParam("nom") String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Le paramètre 'nom' est requis\"}")
                    .build();
        }
        List<Boutique> boutiques = boutiqueService.searchByNom(nom);
        return Response.ok(boutiques).build();
    }

    /**
     * Crée une nouvelle boutique (réservé aux fournisseurs)
     */
    @POST
    public Response create(Boutique boutique,
            @HeaderParam("Authorization") String authHeader) {
        // securityContext.validateFournisseurOrAdminRole(authHeader);
        Boutique saved = boutiqueService.create(boutique);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    /**
     * Met à jour une boutique existante
     */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Boutique boutique,
            @HeaderParam("Authorization") String authHeader) {
        // Long userId = securityContext.extractUserIdFromToken(authHeader);
        
        // Vérifier que l'utilisateur est le propriétaire ou un admin
        // if (!boutiqueService.isBoutiqueOwner(id, userId)) {
        //     securityContext.validateAdminRole(authHeader);
        // }
        
        Boutique updated = boutiqueService.update(id, boutique);
        return Response.ok(updated).build();
    }

    /**
     * Supprime une boutique (réservé aux admins)
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        // securityContext.validateAdminRole(authHeader);
        boutiqueService.deleteById(id);
        return Response.noContent().build();
    }

    /**
     * Active/désactive une boutique
     */
    @PUT
    @Path("/{id}/toggle-active")
    public Response toggleActive(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        // Long userId = securityContext.extractUserIdFromToken(authHeader);
        
        // Vérifier que l'utilisateur est le propriétaire ou un admin
        // if (!boutiqueService.isBoutiqueOwner(id, userId)) {
        //     securityContext.validateAdminRole(authHeader);
        // }
        
        boutiqueService.toggleActive(id);
        return Response.ok("{\"message\": \"Statut de la boutique modifié\"}").build();
    }

    /**
     * Récupère le nombre de boutiques actives
     */
    @GET
    @Path("/stats/count-active")
    public Response countActive() {
        Long count = boutiqueService.countActive();
        return Response.ok("{\"count\": " + count + "}").build();
    }

    /**
     * Récupère les boutiques par catégorie
     */
    @GET
    @Path("/categorie/{categorie}")
    public Response findByCategorie(@PathParam("categorie") String categorie) {
        List<Boutique> boutiques = boutiqueService.findByCategorie(categorie);
        return Response.ok(boutiques).build();
    }

    /**
     * Récupère les boutiques par type
     */
    @GET
    @Path("/type/{type}")
    public Response findByType(@PathParam("type") String type) {
        List<Boutique> boutiques = boutiqueService.findByType(type);
        return Response.ok(boutiques).build();
    }

    /**
     * Récupère les boutiques spécialisées uniquement
     */
    @GET
    @Path("/specialisees")
    public Response findSpecialisees() {
        List<Boutique> boutiques = boutiqueService.findByType("BOUTIQUE_SPECIALISEE");
        return Response.ok(boutiques).build();
    }

    /**
     * Met à jour la note moyenne de la boutique (calculée depuis les packs)
     */
    @PUT
    @Path("/{id}/update-note")
    public Response updateNoteMoyenne(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        // securityContext.validateAdminRole(authHeader);
        boutiqueService.updateNoteMoyenne(id);
        return Response.ok("{\"message\": \"Note moyenne mise à jour\"}").build();
    }
}
