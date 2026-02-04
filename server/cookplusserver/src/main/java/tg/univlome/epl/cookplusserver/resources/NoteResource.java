package tg.univlome.epl.cookplusserver.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tg.univlome.epl.cookplusserver.entities.Note;
import tg.univlome.epl.cookplusserver.services.NoteService;
import tg.univlome.epl.cookplusserver.utils.SecurityContext;

import java.util.List;

@Path("/notes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NoteResource {

    @Inject
    private NoteService noteService;

    @Inject
    private SecurityContext securityContext;

    @GET
    public Response findAll() {
        List<Note> notes = noteService.findAll();
        return Response.ok(notes).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Note note = noteService.findById(id);
        return Response.ok(note).build();
    }

    @GET
    @Path("/recette/{recetteId}")
    public Response findByRecetteId(@PathParam("recetteId") Long recetteId) {
        List<Note> notes = noteService.findByRecetteId(recetteId);
        return Response.ok(notes).build();
    }

    @GET
    @Path("/recette/{recetteId}/utilisateur/{userId}")
    public Response findByRecetteIdAndUtilisateurId(@PathParam("recetteId") Long recetteId,
            @PathParam("userId") Long utilisateurId) {
        Note note = noteService.findByRecetteIdAndUtilisateurId(recetteId, utilisateurId);
        return Response.ok(note).build();
    }

    @POST
    public Response create(Note note,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        Note saved = noteService.save(note);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Note note,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        noteService.findById(id);
        note.setId(id);
        Note updated = noteService.save(note);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id,
            @HeaderParam("Authorization") String authHeader) {
        securityContext.validateAndGetPayload(authHeader);
        noteService.deleteById(id);
        return Response.noContent().build();
    }
}
