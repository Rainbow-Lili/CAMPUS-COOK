package tg.univlome.epl.cookplusserver.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import tg.univlome.epl.cookplusserver.entities.Pack;

/**
 * Data Access Object for Pack entity. Provides database operations for pack
 * management.
 *
 * @author DAKEY Ahoefa Light
 */
public class PackDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Pack> findById(Long id) {
        Pack pack = em.find(Pack.class, id);
        return Optional.ofNullable(pack);
    }

    public List<Pack> findAll() {
        String jpql = "SELECT p FROM Pack p ORDER BY p.dateCreation DESC";
        TypedQuery<Pack> query = em.createQuery(jpql, Pack.class);
        return query.getResultList();
    }

    public List<Pack> findByBoutiqueId(Long boutiqueId) {
        String jpql = "SELECT p FROM Pack p WHERE p.boutique.id = :boutiqueId ORDER BY p.dateCreation DESC";
        TypedQuery<Pack> query = em.createQuery(jpql, Pack.class);
        query.setParameter("boutiqueId", boutiqueId);
        return query.getResultList();
    }

    public List<Pack> findAvailable() {
        String jpql = "SELECT p FROM Pack p WHERE p.disponible = true AND p.stock > 0 ORDER BY p.nombreVentes DESC";
        TypedQuery<Pack> query = em.createQuery(jpql, Pack.class);
        return query.getResultList();
    }

    public List<Pack> findByRecetteId(Long recetteId) {
        String jpql = "SELECT p FROM Pack p WHERE p.recetteId = :recetteId AND p.disponible = true ORDER BY p.prix ASC";
        TypedQuery<Pack> query = em.createQuery(jpql, Pack.class);
        query.setParameter("recetteId", recetteId);
        return query.getResultList();
    }

    public List<Pack> searchByNom(String nom) {
        String jpql = "SELECT p FROM Pack p WHERE LOWER(p.nom) LIKE LOWER(:nom) ORDER BY p.nom ASC";
        TypedQuery<Pack> query = em.createQuery(jpql, Pack.class);
        query.setParameter("nom", "%" + nom + "%");
        return query.getResultList();
    }

    public List<Pack> findTopSelling(int limit) {
        String jpql = "SELECT p FROM Pack p WHERE p.disponible = true ORDER BY p.nombreVentes DESC";
        TypedQuery<Pack> query = em.createQuery(jpql, Pack.class);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List<Pack> findTopRated(int limit) {
        String jpql = "SELECT p FROM Pack p WHERE p.disponible = true AND p.nombreAvis > 0 ORDER BY p.noteMoyenne DESC";
        TypedQuery<Pack> query = em.createQuery(jpql, Pack.class);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List<Pack> findByPriceRange(Double minPrix, Double maxPrix) {
        String jpql = "SELECT p FROM Pack p WHERE p.prix BETWEEN :minPrix AND :maxPrix AND p.disponible = true ORDER BY p.prix ASC";
        TypedQuery<Pack> query = em.createQuery(jpql, Pack.class);
        query.setParameter("minPrix", minPrix);
        query.setParameter("maxPrix", maxPrix);
        return query.getResultList();
    }

    @Transactional
    public Pack save(Pack pack) {
        if (pack.getId() == null) {
            em.persist(pack);
            return pack;
        }
        return em.merge(pack);
    }

    @Transactional
    public void deleteById(Long id) {
        Pack pack = em.find(Pack.class, id);
        if (pack != null) {
            em.remove(pack);
        }
    }

    @Transactional
    public void updateStock(Long packId, int quantite) {
        Pack pack = em.find(Pack.class, packId);
        if (pack != null) {
            pack.setStock(pack.getStock() + quantite);
            if (pack.getStock() > 0 && !pack.getDisponible()) {
                pack.setDisponible(true);
            }
        }
    }

    @Transactional
    public void incrementVentes(Long packId) {
        Pack pack = em.find(Pack.class, packId);
        if (pack != null) {
            pack.incrementerVentes();
        }
    }

    /**
     * Récupère les packs par catégorie
     */
    public List<Pack> findByCategorie(String categorie) {
        String jpql = "SELECT p FROM Pack p WHERE p.categorie = :categorie AND p.disponible = true ORDER BY p.dateCreation DESC";
        TypedQuery<Pack> query = em.createQuery(jpql, Pack.class);
        query.setParameter("categorie", categorie);
        return query.getResultList();
    }

    /**
     * Récupère les packs par budget
     */
    public List<Pack> findByBudget(String budget) {
        String jpql = "SELECT p FROM Pack p WHERE p.budget = :budget AND p.disponible = true ORDER BY p.dateCreation DESC";
        TypedQuery<Pack> query = em.createQuery(jpql, Pack.class);
        query.setParameter("budget", budget);
        return query.getResultList();
    }

    /**
     * Récupère les packs par catégorie et budget
     */
    public List<Pack> findByCategorieAndBudget(String categorie, String budget) {
        String jpql = "SELECT p FROM Pack p WHERE p.categorie = :categorie AND p.budget = :budget AND p.disponible = true ORDER BY p.dateCreation DESC";
        TypedQuery<Pack> query = em.createQuery(jpql, Pack.class);
        query.setParameter("categorie", categorie);
        query.setParameter("budget", budget);
        return query.getResultList();
    }

    /**
     * Récupère les packs par type
     */
    public List<Pack> findByTypePack(String typePack) {
        String jpql = "SELECT p FROM Pack p WHERE p.typePack = :typePack AND p.disponible = true ORDER BY p.dateCreation DESC";
        TypedQuery<Pack> query = em.createQuery(jpql, Pack.class);
        query.setParameter("typePack", typePack);
        return query.getResultList();
    }
}
