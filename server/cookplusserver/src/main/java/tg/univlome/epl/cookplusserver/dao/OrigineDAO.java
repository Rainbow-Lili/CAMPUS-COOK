package tg.univlome.epl.cookplusserver.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import tg.univlome.epl.cookplusserver.entities.Origine;

/**
 * Data Access Object for Origine entity. Provides database operations for
 * origin/source management.
 *
 * @author DAKEY Ahoefa Light
 */
public class OrigineDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Origine> findById(Long id) {
        Origine origine = em.find(Origine.class, id);
        return Optional.ofNullable(origine);
    }

    public Optional<Origine> findByNom(String nom) {
        String jpql = "SELECT o FROM Origine o WHERE o.nom = :nom";
        Query query = em.createQuery(jpql, Origine.class);
        query.setParameter("nom", nom);
        List<Origine> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @SuppressWarnings("unchecked")
    public List<Origine> findAll() {
        String jpql = "SELECT o FROM Origine o ORDER BY o.nom ASC";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    @Transactional
    public Origine save(Origine origine) {
        if (origine.getId() == null) {
            em.persist(origine);
            return origine;
        }
        return em.merge(origine);
    }

    @Transactional
    public void deleteById(Long id) {
        Origine origine = em.find(Origine.class, id);
        if (origine != null) {
            em.remove(origine);
        }
    }
}
