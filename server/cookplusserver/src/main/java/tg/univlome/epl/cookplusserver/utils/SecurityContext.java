package tg.univlome.epl.cookplusserver.utils;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import tg.univlome.epl.cookplusserver.services.AuthService;
import tg.univlome.epl.cookplusserver.exceptions.AuthenticationException;

/**
 * Utility class for security context and authentication. Validates tokens and
 * authorization headers.
 *
 * @author DAKEY Ahoefa Light
 */
public class SecurityContext {

    @Inject
    private AuthService authService;

    public static final String BEARER_PREFIX = "Bearer ";

    public TokenUtil.TokenPayload validateAndGetPayload(String authHeader) {
        validateAuthHeader(authHeader);
        String token = extractToken(authHeader);
        return authService.validateToken(token);
    }

    public TokenUtil.TokenPayload validateAndGetPayload(ContainerRequestContext requestContext) {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        return validateAndGetPayload(authHeader);
    }

    public void validateAdminRole(String authHeader) {
        TokenUtil.TokenPayload payload = validateAndGetPayload(authHeader);
        if (!isAdmin(payload.role)) {
            throw new AuthenticationException("Accès refusé. Rôle admin requis");
        }
    }

    public void validateAdminRole(ContainerRequestContext requestContext) {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        validateAdminRole(authHeader);
    }

    public boolean isAdmin(String role) {
        return "admin".equalsIgnoreCase(role);
    }

    public boolean isStudent(String role) {
        return "etudiant".equalsIgnoreCase(role);
    }

    private void validateAuthHeader(String authHeader) {
        java.util.Optional.of(authHeader)
                .filter(h -> h != null && h.startsWith(BEARER_PREFIX))
                .orElseThrow(() -> new AuthenticationException("Token manquant ou format invalide"));
    }

    private String extractToken(String authHeader) {
        return authHeader.substring(BEARER_PREFIX.length());
    }
}
