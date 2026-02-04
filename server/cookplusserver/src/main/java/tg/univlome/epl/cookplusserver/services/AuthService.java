/**
 * Service for authentication and authorization.
 * Handles user login, token generation, and validation.
 *
 * @author DAKEY Ahoefa Light
 */
package tg.univlome.epl.cookplusserver.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.UtilisateurDAO;
import tg.univlome.epl.cookplusserver.entities.Utilisateur;
import tg.univlome.epl.cookplusserver.exceptions.AuthenticationException;
import tg.univlome.epl.cookplusserver.utils.AuthResponse;
import tg.univlome.epl.cookplusserver.utils.TokenUtil;

@Stateless
public class AuthService {

    @Inject
    private UtilisateurDAO utilisateurDAO;

    private static final long EXPIRATION_TIME = 3 * 24 * 60 * 60 * 1000; // 3 jours

    public AuthResponse login(String email, String motDePasse) {
        Utilisateur utilisateur = utilisateurDAO.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Email ou mot de passe incorrect"));

        verifyPassword(motDePasse, utilisateur.getMotDePasseHash())
                .orElseThrow(() -> new AuthenticationException("Email ou mot de passe incorrect"));

        validateUserActive(utilisateur);

        String token = TokenUtil.generateToken(utilisateur.getId(), utilisateur.getEmail(), utilisateur.getRole());
        utilisateur.mettreAJourDerniereConnexion();
        utilisateurDAO.save(utilisateur);

        return new AuthResponse(utilisateur.getId(), utilisateur.getEmail(), utilisateur.getNom(),
                utilisateur.getRole(), token, EXPIRATION_TIME / 1000);
    }

    public TokenUtil.TokenPayload validateToken(String token) {
        validateTokenPresent(token);
        TokenUtil.TokenPayload payload = TokenUtil.validateToken(token);
        return validateTokenPayload(payload);
    }

    public boolean isAdmin(String token) {
        TokenUtil.TokenPayload payload = validateToken(token);
        return "admin".equalsIgnoreCase(payload.role);
    }

    private Optional<Boolean> verifyPassword(String rawPassword, String hashedPassword) {
        return Optional.of(hashPassword(rawPassword).equals(hashedPassword));
    }

    private void validateUserActive(Utilisateur utilisateur) {
        if (!utilisateur.estActif()) {
            throw new AuthenticationException("Compte inactif. Veuillez contacter l'administrateur");
        }
    }

    private void validateTokenPresent(String token) {
        Optional.of(token)
                .filter(t -> t != null && !t.isEmpty())
                .orElseThrow(() -> new AuthenticationException("Token manquant"));
    }

    private TokenUtil.TokenPayload validateTokenPayload(TokenUtil.TokenPayload payload) {
        return Optional.ofNullable(payload)
                .orElseThrow(() -> new AuthenticationException("Token invalide ou expiré"));
    }

    private String hashPassword(String password) {
        // Simple hash - À remplacer par BCrypt en production
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}
