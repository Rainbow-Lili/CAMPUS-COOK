package tg.univlome.epl.cookplusserver.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.FavoriDAO;
import tg.univlome.epl.cookplusserver.entities.Favori;
import tg.univlome.epl.cookplusserver.exceptions.EntityNotFoundException;

/**
 * Service for favorites operations. Handles all business logic related to user
 * favorites.
 *
 * @author DAKEY Ahoefa Light
 */
@Stateless
public class FavoriService {

    @Inject
    private FavoriDAO favoriDAO;

    public List<Favori> findAll() {
        return favoriDAO.findAll();
    }

    public Favori findById(Long id) {
        return favoriDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Favori", id));
    }

    public List<Favori> findByUtilisateurId(Long utilisateurId) {
        return favoriDAO.findByUtilisateurId(utilisateurId);
    }

    public Favori findByUtilisateurIdAndRecetteId(Long utilisateurId, Long recetteId) {
        return favoriDAO.findByUtilisateurIdAndRecetteId(utilisateurId, recetteId)
                .orElseThrow(() -> new EntityNotFoundException("Favori pour cette recette non trouvé"));
    }

    public Favori save(Favori favori) {
        return favoriDAO.save(favori);
    }

    public void deleteById(Long id) {
        findById(id);
        favoriDAO.deleteById(id);
    }
}
