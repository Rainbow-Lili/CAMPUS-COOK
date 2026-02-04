package tg.univlome.epl.cookplusserver.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.RecetteIngredientDAO;
import tg.univlome.epl.cookplusserver.entities.RecetteIngredient;
import tg.univlome.epl.cookplusserver.exceptions.EntityNotFoundException;

@Stateless
public class RecetteIngredientService {

    @Inject
    private RecetteIngredientDAO recetteIngredientDAO;

    public List<RecetteIngredient> findAll() {
        return recetteIngredientDAO.findAll();
    }

    public RecetteIngredient findById(Long id) {
        return recetteIngredientDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RecetteIngredient", id));
    }

    public List<RecetteIngredient> findByRecetteId(Long recetteId) {
        return recetteIngredientDAO.findByRecetteId(recetteId);
    }

    public List<RecetteIngredient> findByIngredientId(Long ingredientId) {
        return recetteIngredientDAO.findByIngredientId(ingredientId);
    }

    public RecetteIngredient save(RecetteIngredient recetteIngredient) {
        return recetteIngredientDAO.save(recetteIngredient);
    }

    public void deleteById(Long id) {
        findById(id);
        recetteIngredientDAO.deleteById(id);
    }
}
