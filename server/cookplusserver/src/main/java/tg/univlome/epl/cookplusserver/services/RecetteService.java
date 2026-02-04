package tg.univlome.epl.cookplusserver.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.RecetteDAO;
import tg.univlome.epl.cookplusserver.entities.Recette;
import tg.univlome.epl.cookplusserver.exceptions.EntityNotFoundException;

/**
 * Service for recipe operations. Handles all business logic related to recipes.
 *
 * @author DAKEY Ahoefa Light
 */
@Stateless
public class RecetteService {

    @Inject
    private RecetteDAO recetteDAO;

    public List<Recette> findAll() {
        return recetteDAO.findAll();
    }

    public Recette findById(Long id) {
        return recetteDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recette", id));
    }

    public List<Recette> findByNom(String nom) {
        return recetteDAO.findByNom(nom);
    }

    public List<Recette> findByUtilisateurId(Long utilisateurId) {
        return recetteDAO.findByUtilisateurId(utilisateurId);
    }

    public List<Recette> findPublic() {
        return recetteDAO.findPublic();
    }

    public List<Recette> findByDifficulte(String difficulte) {
        return recetteDAO.findByDifficulte(difficulte);
    }

    public Recette save(Recette recette) {
        return recetteDAO.save(recette);
    }

    public void deleteById(Long id) {
        findById(id);
        recetteDAO.deleteById(id);
    }

    public double calculerCoutTotal(Long recetteId) {
        return findById(recetteId).calculerCoutTotal();
    }

    public double calculerNoteMoyenne(Long recetteId) {
        return findById(recetteId).calculerNoteMoyenne();
    }

    public void incrementerVues(Long recetteId) {
        Recette recette = findById(recetteId);
        recette.incrementerVues();
        recetteDAO.save(recette);
    }
}
