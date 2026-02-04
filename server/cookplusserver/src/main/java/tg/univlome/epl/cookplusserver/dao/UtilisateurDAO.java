package tg.univlome.epl.cookplusserver.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import tg.univlome.epl.cookplusserver.entities.Utilisateur;

/**
 * Data Access Object for Utilisateur entity. Provides database operations for
 * user management.
 *
 * @author DAKEY Ahoefa Light
 */
public class UtilisateurDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Utilisateur> findById(Long id) {
        Utilisateur user = em.find(Utilisateur.class, id);
        return Optional.ofNullable(user);
    }

    public Optional<Utilisateur> findByEmail(String email) {
        String jpql = "SELECT u FROM Utilisateur u WHERE u.email = :email";
        Query query = em.createQuery(jpql, Utilisateur.class);
        query.setParameter("email", email);
        List<Utilisateur> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @SuppressWarnings("unchecked")
    public List<Utilisateur> findAll() {
        String jpql = "SELECT u FROM Utilisateur u";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Utilisateur> findByRole(String role) {
        String jpql = "SELECT u FROM Utilisateur u WHERE u.role = :role";
        Query query = em.createQuery(jpql);
        query.setParameter("role", role);
        return query.getResultList();
    }

    @Transactional
    public Utilisateur save(Utilisateur utilisateur) {
        if (utilisateur.getId() == null) {
            em.persist(utilisateur);
            return utilisateur;
        }
        return em.merge(utilisateur);
    }

    @Transactional
    public void deleteById(Long id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }
}
