package tg.univlome.epl.cookplusserver.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import tg.univlome.epl.cookplusserver.entities.Boutique;

/**
 * Data Access Object for Boutique entity. Provides database operations for
 * boutique management.
 *
 * @author DAKEY Ahoefa Light
 */
public class BoutiqueDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Boutique> findById(Long id) {
        Boutique boutique = em.find(Boutique.class, id);
        return Optional.ofNullable(boutique);
    }

    public Optional<Boutique> findByFournisseurId(Long fournisseurId) {
        String jpql = "SELECT b FROM Boutique b WHERE b.fournisseur.id = :fournisseurId";
        TypedQuery<Boutique> query = em.createQuery(jpql, Boutique.class);
        query.setParameter("fournisseurId", fournisseurId);
        List<Boutique> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public List<Boutique> findAll() {
        String jpql = "SELECT b FROM Boutique b ORDER BY b.dateCreation DESC";
        TypedQuery<Boutique> query = em.createQuery(jpql, Boutique.class);
        return query.getResultList();
    }

    public List<Boutique> findActive() {
        String jpql = "SELECT b FROM Boutique b WHERE b.active = true ORDER BY b.noteMoyenne DESC";
        TypedQuery<Boutique> query = em.createQuery(jpql, Boutique.class);
        return query.getResultList();
    }

    public List<Boutique> searchByNom(String nom) {
        String jpql = "SELECT b FROM Boutique b WHERE LOWER(b.nom) LIKE LOWER(:nom) ORDER BY b.nom ASC";
        TypedQuery<Boutique> query = em.createQuery(jpql, Boutique.class);
        query.setParameter("nom", "%" + nom + "%");
        return query.getResultList();
    }

    public List<Boutique> findTopRated(int limit) {
        String jpql = "SELECT b FROM Boutique b WHERE b.active = true AND b.nombreAvis > 0 ORDER BY b.noteMoyenne DESC";
        TypedQuery<Boutique> query = em.createQuery(jpql, Boutique.class);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional
    public Boutique save(Boutique boutique) {
        if (boutique.getId() == null) {
            em.persist(boutique);
            return boutique;
        }
        return em.merge(boutique);
    }

    @Transactional
    public void deleteById(Long id) {
        Boutique boutique = em.find(Boutique.class, id);
        if (boutique != null) {
            em.remove(boutique);
        }
    }

    @Transactional
    public void updateNoteMoyenne(Long boutiqueId) {
        String jpql = "SELECT AVG(p.noteMoyenne) FROM Pack p WHERE p.boutique.id = :boutiqueId AND p.nombreAvis > 0";
        Query query = em.createQuery(jpql);
        query.setParameter("boutiqueId", boutiqueId);
        Double moyenne = (Double) query.getSingleResult();
        
        Boutique boutique = em.find(Boutique.class, boutiqueId);
        if (boutique != null) {
            boutique.setNoteMoyenne(moyenne != null ? moyenne : 0.0);
        }
    }

    public Long countActive() {
        String jpql = "SELECT COUNT(b) FROM Boutique b WHERE b.active = true";
        Query query = em.createQuery(jpql);
        return (Long) query.getSingleResult();
    }

    /**
     * Récupère les boutiques par catégorie
     */
    public List<Boutique> findByCategorie(String categorie) {
        String jpql = "SELECT b FROM Boutique b WHERE b.categorie = :categorie AND b.active = true ORDER BY b.noteMoyenne DESC";
        TypedQuery<Boutique> query = em.createQuery(jpql, Boutique.class);
        query.setParameter("categorie", categorie);
        return query.getResultList();
    }

    /**
     * Récupère les boutiques par type
     */
    public List<Boutique> findByType(String type) {
        String jpql = "SELECT b FROM Boutique b WHERE b.type = :type AND b.active = true ORDER BY b.noteMoyenne DESC";
        TypedQuery<Boutique> query = em.createQuery(jpql, Boutique.class);
        query.setParameter("type", type);
        return query.getResultList();
    }
}
