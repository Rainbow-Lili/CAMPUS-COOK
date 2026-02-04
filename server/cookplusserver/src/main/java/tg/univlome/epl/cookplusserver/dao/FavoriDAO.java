package tg.univlome.epl.cookplusserver.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import tg.univlome.epl.cookplusserver.entities.Favori;

/**
 * Data Access Object for Favori entity. Provides database operations for
 * favorites management.
 *
 * @author DAKEY Ahoefa Light
 */
public class FavoriDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Favori> findById(Long id) {
        Favori favori = em.find(Favori.class, id);
        return Optional.ofNullable(favori);
    }

    @SuppressWarnings("unchecked")
    public List<Favori> findAll() {
        String jpql = "SELECT f FROM Favori f ORDER BY f.dateCreation DESC";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Favori> findByUtilisateurId(Long utilisateurId) {
        String jpql = "SELECT f FROM Favori f WHERE f.utilisateur.id = :userId ORDER BY f.dateCreation DESC";
        Query query = em.createQuery(jpql);
        query.setParameter("userId", utilisateurId);
        return query.getResultList();
    }

    public Optional<Favori> findByUtilisateurIdAndRecetteId(Long utilisateurId, Long recetteId) {
        String jpql = "SELECT f FROM Favori f WHERE f.utilisateur.id = :userId AND f.recette.id = :recetteId";
        Query query = em.createQuery(jpql, Favori.class);
        query.setParameter("userId", utilisateurId);
        query.setParameter("recetteId", recetteId);
        List<Favori> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Transactional
    public Favori save(Favori favori) {
        if (favori.getId() == null) {
            em.persist(favori);
            return favori;
        }
        return em.merge(favori);
    }

    @Transactional
    public void deleteById(Long id) {
        Favori favori = em.find(Favori.class, id);
        if (favori != null) {
            em.remove(favori);
        }
    }
}
