package tg.univlome.epl.cookplusserver.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import tg.univlome.epl.cookplusserver.entities.Etape;

/**
 * Data Access Object for Etape entity. Provides database operations for recipe
 * steps management.
 *
 * @author DAKEY Ahoefa Light
 */
public class EtapeDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Etape> findById(Long id) {
        Etape etape = em.find(Etape.class, id);
        return Optional.ofNullable(etape);
    }

    @SuppressWarnings("unchecked")
    public List<Etape> findAll() {
        String jpql = "SELECT e FROM Etape e ORDER BY e.dateCreation DESC";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Etape> findByRecetteId(Long recetteId) {
        String jpql = "SELECT e FROM Etape e WHERE e.recette.id = :recetteId ORDER BY e.ordre ASC";
        Query query = em.createQuery(jpql);
        query.setParameter("recetteId", recetteId);
        return query.getResultList();
    }

    @Transactional
    public Etape save(Etape etape) {
        if (etape.getId() == null) {
            em.persist(etape);
            return etape;
        }
        return em.merge(etape);
    }

    @Transactional
    public void deleteById(Long id) {
        Etape etape = em.find(Etape.class, id);
        if (etape != null) {
            em.remove(etape);
        }
    }
}
