package tg.univlome.epl.cookplusserver.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import tg.univlome.epl.cookplusserver.entities.RecetteIngredient;

/**
 * Data Access Object for RecetteIngredient entity. Provides database operations
 * for recipe-ingredient relationships.
 *
 * @author DAKEY Ahoefa Light
 */
public class RecetteIngredientDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<RecetteIngredient> findById(Long id) {
        RecetteIngredient ri = em.find(RecetteIngredient.class, id);
        return Optional.ofNullable(ri);
    }

    @SuppressWarnings("unchecked")
    public List<RecetteIngredient> findAll() {
        String jpql = "SELECT ri FROM RecetteIngredient ri";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<RecetteIngredient> findByRecetteId(Long recetteId) {
        String jpql = "SELECT ri FROM RecetteIngredient ri WHERE ri.recette.id = :recetteId";
        Query query = em.createQuery(jpql);
        query.setParameter("recetteId", recetteId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<RecetteIngredient> findByIngredientId(Long ingredientId) {
        String jpql = "SELECT ri FROM RecetteIngredient ri WHERE ri.ingredient.id = :ingredientId";
        Query query = em.createQuery(jpql);
        query.setParameter("ingredientId", ingredientId);
        return query.getResultList();
    }

    @Transactional
    public RecetteIngredient save(RecetteIngredient recetteIngredient) {
        if (recetteIngredient.getId() == null) {
            em.persist(recetteIngredient);
            return recetteIngredient;
        }
        return em.merge(recetteIngredient);
    }

    @Transactional
    public void deleteById(Long id) {
        RecetteIngredient ri = em.find(RecetteIngredient.class, id);
        if (ri != null) {
            em.remove(ri);
        }
    }
}
