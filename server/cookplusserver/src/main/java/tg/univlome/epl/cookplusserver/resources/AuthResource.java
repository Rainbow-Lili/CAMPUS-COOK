package tg.univlome.epl.cookplusserver.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tg.univlome.epl.cookplusserver.exceptions.ValidationException;
import tg.univlome.epl.cookplusserver.services.AuthService;
import tg.univlome.epl.cookplusserver.utils.AuthResponse;
import tg.univlome.epl.cookplusserver.utils.LoginRequest;
import tg.univlome.epl.cookplusserver.utils.TokenUtil;

/**
 * REST endpoint for authentication. Provides login and token validation
 * endpoints.
 *
 * @author DAKEY Ahoefa Light
 */
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    private AuthService authService;

    private void validateLoginRequest(LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new ValidationException("Requête de connexion invalide");
        }
        if (loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email requis");
        }
        if (loginRequest.getMotDePasse() == null || loginRequest.getMotDePasse().trim().isEmpty()) {
            throw new ValidationException("Mot de passe requis");
        }
    }

    private void validateAuthHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ValidationException("Token manquant ou format invalide");
        }
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest loginRequest) {
        validateLoginRequest(loginRequest);
        AuthResponse authResponse = authService.login(loginRequest.getEmail(), loginRequest.getMotDePasse());
        return Response.ok(authResponse).build();
    }

    @POST
    @Path("/validate")
    public Response validateToken(@HeaderParam("Authorization") String authHeader) {
        validateAuthHeader(authHeader);
        String token = authHeader.substring("Bearer ".length());
        TokenUtil.TokenPayload payload = authService.validateToken(token);
        return Response.ok(payload).build();
    }

    @GET
    @Path("/check-admin")
    public Response checkAdmin(@HeaderParam("Authorization") String authHeader) {
        validateAuthHeader(authHeader);
        String token = authHeader.substring("Bearer ".length());
        boolean isAdmin = authService.isAdmin(token);
        return Response.ok("{\"isAdmin\": " + isAdmin + "}").build();
    }
}
