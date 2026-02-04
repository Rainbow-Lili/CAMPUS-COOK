package tg.univlome.epl.cookplusserver.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import tg.univlome.epl.cookplusserver.entities.Recette;

/**
 * Data Access Object for Recette entity. Provides database operations for
 * recipe management.
 *
 * @author DAKEY Ahoefa Light
 */
public class RecetteDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Recette> findById(Long id) {
        Recette recette = em.find(Recette.class, id);
        return Optional.ofNullable(recette);
    }

    @SuppressWarnings("unchecked")
    public List<Recette> findAll() {
        String jpql = "SELECT r FROM Recette r ORDER BY r.dateCreation DESC";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Recette> findByNom(String nom) {
        String jpql = "SELECT r FROM Recette r WHERE LOWER(r.nom) LIKE LOWER(:nom) ORDER BY r.nom ASC";
        Query query = em.createQuery(jpql);
        query.setParameter("nom", "%" + nom + "%");
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Recette> findByUtilisateurId(Long utilisateurId) {
        String jpql = "SELECT r FROM Recette r WHERE r.utilisateur.id = :userId ORDER BY r.dateCreation DESC";
        Query query = em.createQuery(jpql);
        query.setParameter("userId", utilisateurId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Recette> findPublic() {
        String jpql = "SELECT r FROM Recette r WHERE r.estPublic = true ORDER BY r.nombreVues DESC";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Recette> findByDifficulte(String difficulte) {
        String jpql = "SELECT r FROM Recette r WHERE r.difficulte = :difficulte ORDER BY r.dateCreation DESC";
        Query query = em.createQuery(jpql);
        query.setParameter("difficulte", difficulte);
        return query.getResultList();
    }

    @Transactional
    public Recette save(Recette recette) {
        if (recette.getId() == null) {
            em.persist(recette);
            return recette;
        }
        return em.merge(recette);
    }

    @Transactional
    public void deleteById(Long id) {
        Recette recette = em.find(Recette.class, id);
        if (recette != null) {
            em.remove(recette);
        }
    }
}
