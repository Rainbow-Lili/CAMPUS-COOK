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
import tg.univlome.epl.cookplusserver.entities.Etape;
import tg.univlome.epl.cookplusserver.services.EtapeService;
import tg.univlome.epl.cookplusserver.utils.SecurityContext;

import java.util.List;

@Path("/etapes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EtapeResource {

    @Inject
    private EtapeService etapeService;

    @Inject
    private SecurityContext securityContext;

    @GET
    public Response findAll() {
        List<Etape> etapes = etapeService.findAll();
        return Response.ok(etapes).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Etape etape = etapeService.findById(id);
        return Response.ok(etape).build();
    }

    @GET
    @Path("/recette/{recetteId}")
    public Response findByRecetteId(@PathParam("recetteId") Long recetteId) {
        List<Etape> etapes = etapeService.findByRecetteId(recetteId);
        return Response.ok(etapes).build();
    }

    @POST
    public Response create(Etape etape,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        Etape saved = etapeService.save(etape);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Etape etape,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        etapeService.findById(id);
        etape.setId(id);
        Etape updated = etapeService.save(etape);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        etapeService.deleteById(id);
        return Response.noContent().build();
    }
}
