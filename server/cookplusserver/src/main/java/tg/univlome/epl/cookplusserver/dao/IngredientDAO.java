package tg.univlome.epl.cookplusserver.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import tg.univlome.epl.cookplusserver.entities.Ingredient;

/**
 * Data Access Object for Ingredient entity. Provides database operations for
 * ingredient management.
 *
 * @author DAKEY Ahoefa Light
 */
public class IngredientDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Ingredient> findById(Long id) {
        Ingredient ingredient = em.find(Ingredient.class, id);
        return Optional.ofNullable(ingredient);
    }

    public Optional<Ingredient> findByNom(String nom) {
        String jpql = "SELECT i FROM Ingredient i WHERE i.nom = :nom";
        Query query = em.createQuery(jpql, Ingredient.class);
        query.setParameter("nom", nom);
        List<Ingredient> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @SuppressWarnings("unchecked")
    public List<Ingredient> findAll() {
        String jpql = "SELECT i FROM Ingredient i ORDER BY i.nom ASC";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Ingredient> findByCategorie(String categorie) {
        String jpql = "SELECT i FROM Ingredient i WHERE i.categorie = :categorie ORDER BY i.nom ASC";
        Query query = em.createQuery(jpql);
        query.setParameter("categorie", categorie);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Ingredient> findAvailable() {
        String jpql = "SELECT i FROM Ingredient i WHERE i.disponible = true ORDER BY i.nom ASC";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    @Transactional
    public Ingredient save(Ingredient ingredient) {
        if (ingredient.getId() == null) {
            em.persist(ingredient);
            return ingredient;
        }
        return em.merge(ingredient);
    }

    @Transactional
    public void deleteById(Long id) {
        Ingredient ingredient = em.find(Ingredient.class, id);
        if (ingredient != null) {
            em.remove(ingredient);
        }
    }
}
