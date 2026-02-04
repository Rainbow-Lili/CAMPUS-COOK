package tg.univlome.epl.cookplusserver.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.UtilisateurDAO;
import tg.univlome.epl.cookplusserver.entities.Utilisateur;
import tg.univlome.epl.cookplusserver.exceptions.EntityNotFoundException;

/**
 * Service for user operations. Handles all business logic related to user
 * management.
 *
 * @author DAKEY Ahoefa Light
 */
@Stateless
public class UtilisateurService {

    @Inject
    private UtilisateurDAO utilisateurDAO;

    public List<Utilisateur> findAll() {
        return utilisateurDAO.findAll();
    }

    public Utilisateur findById(Long id) {
        return utilisateurDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur", id));
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurDAO.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur avec l'email " + email + " non trouvé"));
    }

    public List<Utilisateur> findByRole(String role) {
        return utilisateurDAO.findByRole(role);
    }

    public Utilisateur save(Utilisateur utilisateur) {
        return utilisateurDAO.save(utilisateur);
    }

    public void deleteById(Long id) {
        findById(id);
        utilisateurDAO.deleteById(id);
    }

    public boolean estAdmin(Long id) {
        return findById(id).estAdmin();
    }

    public void activateUser(Long id) {
        Utilisateur user = findById(id);
        user.activer();
        utilisateurDAO.save(user);
    }

    public void deactivateUser(Long id) {
        Utilisateur user = findById(id);
        user.desactiver();
        utilisateurDAO.save(user);
    }
}
