package tg.univlome.epl.cookplusserver.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import tg.univlome.epl.cookplusserver.entities.Commentaire;

/**
 * Data Access Object for Commentaire entity. Provides database operations for
 * comment management.
 *
 * @author DAKEY Ahoefa Light
 */
public class CommentaireDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Commentaire> findById(Long id) {
        Commentaire commentaire = em.find(Commentaire.class, id);
        return Optional.ofNullable(commentaire);
    }

    @SuppressWarnings("unchecked")
    public List<Commentaire> findAll() {
        String jpql = "SELECT c FROM Commentaire c ORDER BY c.dateCreation DESC";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Commentaire> findByRecetteIdAndActive(Long recetteId) {
        String jpql = "SELECT c FROM Commentaire c WHERE c.recette.id = :recetteId AND c.actif = true ORDER BY c.dateCreation DESC";
        Query query = em.createQuery(jpql);
        query.setParameter("recetteId", recetteId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Commentaire> findByUtilisateurId(Long utilisateurId) {
        String jpql = "SELECT c FROM Commentaire c WHERE c.utilisateur.id = :userId ORDER BY c.dateCreation DESC";
        Query query = em.createQuery(jpql);
        query.setParameter("userId", utilisateurId);
        return query.getResultList();
    }

    @Transactional
    public Commentaire save(Commentaire commentaire) {
        if (commentaire.getId() == null) {
            em.persist(commentaire);
            return commentaire;
        }
        return em.merge(commentaire);
    }

    @Transactional
    public void deleteById(Long id) {
        Commentaire commentaire = em.find(Commentaire.class, id);
        if (commentaire != null) {
            em.remove(commentaire);
        }
    }
}
