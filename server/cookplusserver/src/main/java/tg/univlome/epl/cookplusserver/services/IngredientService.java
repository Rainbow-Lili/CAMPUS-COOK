package tg.univlome.epl.cookplusserver.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.IngredientDAO;
import tg.univlome.epl.cookplusserver.entities.Ingredient;
import tg.univlome.epl.cookplusserver.exceptions.EntityNotFoundException;

/**
 * Service for ingredient operations. Handles all business logic related to
 * ingredients.
 *
 * @author DAKEY Ahoefa Light
 */
@Stateless
public class IngredientService {

    @Inject
    private IngredientDAO ingredientDAO;

    public List<Ingredient> findAll() {
        return ingredientDAO.findAll();
    }

    public Ingredient findById(Long id) {
        return ingredientDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient", id));
    }

    public Ingredient findByNom(String nom) {
        return ingredientDAO.findByNom(nom)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient avec le nom " + nom + " non trouvé"));
    }

    public List<Ingredient> findByCategorie(String categorie) {
        return ingredientDAO.findByCategorie(categorie);
    }

    public List<Ingredient> findAvailable() {
        return ingredientDAO.findAvailable();
    }

    public Ingredient save(Ingredient ingredient) {
        return ingredientDAO.save(ingredient);
    }

    public void deleteById(Long id) {
        findById(id);
        ingredientDAO.deleteById(id);
    }

    public void toggleDisponibilite(Long id) {
        Ingredient ingredient = findById(id);
        ingredient.setDisponible(!ingredient.getDisponible());
        ingredientDAO.save(ingredient);
    }
}
