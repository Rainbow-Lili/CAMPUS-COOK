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
import tg.univlome.epl.cookplusserver.entities.Origine;
import tg.univlome.epl.cookplusserver.services.OrigineService;
import tg.univlome.epl.cookplusserver.utils.SecurityContext;

import java.util.List;

@Path("/origines")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrigineResource {

    @Inject
    private OrigineService origineService;

    @Inject
    private SecurityContext securityContext;

    @GET
    public Response findAll() {
        List<Origine> origines = origineService.findAll();
        return Response.ok(origines).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Origine origine = origineService.findById(id);
        return Response.ok(origine).build();
    }

    @GET
    @Path("/nom/{nom}")
    public Response findByNom(@PathParam("nom") String nom) {
        Origine origine = origineService.findByNom(nom);
        return Response.ok(origine).build();
    }

    @POST
    public Response create(Origine origine,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        Origine saved = origineService.save(origine);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Origine origine,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        origineService.findById(id);
        origine.setId(id);
        Origine updated = origineService.save(origine);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        origineService.deleteById(id);
        return Response.noContent().build();
    }
}
