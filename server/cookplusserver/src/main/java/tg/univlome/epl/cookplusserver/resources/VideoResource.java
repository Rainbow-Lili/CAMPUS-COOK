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
import tg.univlome.epl.cookplusserver.entities.Video;
import tg.univlome.epl.cookplusserver.services.VideoService;
import tg.univlome.epl.cookplusserver.utils.SecurityContext;

import java.util.List;

@Path("/videos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VideoResource {

    @Inject
    private VideoService videoService;

    @Inject
    private SecurityContext securityContext;

    @GET
    public Response findAll() {
        List<Video> videos = videoService.findAll();
        return Response.ok(videos).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Video video = videoService.findById(id);
        return Response.ok(video).build();
    }

    @GET
    @Path("/recette/{recetteId}")
    public Response findByRecetteId(@PathParam("recetteId") Long recetteId) {
        List<Video> videos = videoService.findByRecetteId(recetteId);
        return Response.ok(videos).build();
    }

    @POST
    public Response create(Video video,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        Video saved = videoService.save(video);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Video video,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        videoService.findById(id);
        video.setId(id);
        Video updated = videoService.save(video);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAdminRole(authHeader);
        videoService.deleteById(id);
        return Response.noContent().build();
    }
}
