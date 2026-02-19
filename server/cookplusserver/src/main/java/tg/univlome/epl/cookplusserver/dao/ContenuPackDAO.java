package tg.univlome.epl.cookplusserver.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import tg.univlome.epl.cookplusserver.entities.ContenuPack;

/**
 * Data Access Object for ContenuPack entity. Provides database operations for
 * pack content management.
 *
 * @author DAKEY Ahoefa Light
 */
public class ContenuPackDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<ContenuPack> findById(Long id) {
        ContenuPack contenu = em.find(ContenuPack.class, id);
        return Optional.ofNullable(contenu);
    }

    public List<ContenuPack> findByPackId(Long packId) {
        String jpql = "SELECT c FROM ContenuPack c WHERE c.pack.id = :packId ORDER BY c.ordre ASC, c.id ASC";
        TypedQuery<ContenuPack> query = em.createQuery(jpql, ContenuPack.class);
        query.setParameter("packId", packId);
        return query.getResultList();
    }

    public List<ContenuPack> findByIngredientId(Long ingredientId) {
        String jpql = "SELECT c FROM ContenuPack c WHERE c.ingredient.id = :ingredientId";
        TypedQuery<ContenuPack> query = em.createQuery(jpql, ContenuPack.class);
        query.setParameter("ingredientId", ingredientId);
        return query.getResultList();
    }

    public Optional<ContenuPack> findByPackAndIngredient(Long packId, Long ingredientId) {
        String jpql = "SELECT c FROM ContenuPack c WHERE c.pack.id = :packId AND c.ingredient.id = :ingredientId";
        TypedQuery<ContenuPack> query = em.createQuery(jpql, ContenuPack.class);
        query.setParameter("packId", packId);
        query.setParameter("ingredientId", ingredientId);
        List<ContenuPack> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Transactional
    public ContenuPack save(ContenuPack contenu) {
        if (contenu.getId() == null) {
            em.persist(contenu);
            return contenu;
        }
        return em.merge(contenu);
    }

    @Transactional
    public void deleteById(Long id) {
        ContenuPack contenu = em.find(ContenuPack.class, id);
        if (contenu != null) {
            em.remove(contenu);
        }
    }

    @Transactional
    public void deleteByPackId(Long packId) {
        String jpql = "DELETE FROM ContenuPack c WHERE c.pack.id = :packId";
        em.createQuery(jpql)
                .setParameter("packId", packId)
                .executeUpdate();
    }

    public int countByPackId(Long packId) {
        String jpql = "SELECT COUNT(c) FROM ContenuPack c WHERE c.pack.id = :packId";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("packId", packId)
                .getSingleResult();
        return count.intValue();
    }

    public Double calculateTotalPrice(Long packId) {
        String jpql = "SELECT SUM(c.quantite * c.ingredient.prixUnitaire) FROM ContenuPack c WHERE c.pack.id = :packId";
        Double total = em.createQuery(jpql, Double.class)
                .setParameter("packId", packId)
                .getSingleResult();
        return total != null ? total : 0.0;
    }
}
