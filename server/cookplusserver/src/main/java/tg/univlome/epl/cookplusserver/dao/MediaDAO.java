package tg.univlome.epl.cookplusserver.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import tg.univlome.epl.cookplusserver.entities.Media;

/**
 * Data Access Object for Media entity. Provides database operations for media
 * management.
 *
 * @author DAKEY Ahoefa Light
 */
public class MediaDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Media> findById(Long id) {
        Media media = em.find(Media.class, id);
        return Optional.ofNullable(media);
    }

    @SuppressWarnings("unchecked")
    public List<Media> findAll() {
        String jpql = "SELECT m FROM Media m ORDER BY m.dateCreation DESC";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Media> findByRecetteId(Long recetteId) {
        String jpql = "SELECT m FROM Media m WHERE m.recette.id = :recetteId";
        Query query = em.createQuery(jpql);
        query.setParameter("recetteId", recetteId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Media> findByIngredientId(Long ingredientId) {
        String jpql = "SELECT m FROM Media m WHERE m.ingredient.id = :ingredientId";
        Query query = em.createQuery(jpql);
        query.setParameter("ingredientId", ingredientId);
        return query.getResultList();
    }

    @Transactional
    public Media save(Media media) {
        if (media.getId() == null) {
            em.persist(media);
            return media;
        }
        return em.merge(media);
    }

    @Transactional
    public void deleteById(Long id) {
        Media media = em.find(Media.class, id);
        if (media != null) {
            em.remove(media);
        }
    }
}
