package tg.univlome.epl.cookplusserver.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import tg.univlome.epl.cookplusserver.entities.Notification;

public class NotificationDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Notification> findById(Long id) {
        Notification notification = em.find(Notification.class, id);
        return Optional.ofNullable(notification);
    }

    @SuppressWarnings("unchecked")
    public List<Notification> findAll() {
        String jpql = "SELECT n FROM Notification n ORDER BY n.dateCreation DESC";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Notification> findByUtilisateurId(Long utilisateurId) {
        String jpql = "SELECT n FROM Notification n WHERE n.utilisateur.id = :userId ORDER BY n.dateCreation DESC";
        Query query = em.createQuery(jpql);
        query.setParameter("userId", utilisateurId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Notification> findUnreadByUtilisateurId(Long utilisateurId) {
        String jpql = "SELECT n FROM Notification n WHERE n.utilisateur.id = :userId AND n.lu = false ORDER BY n.dateCreation DESC";
        Query query = em.createQuery(jpql);
        query.setParameter("userId", utilisateurId);
        return query.getResultList();
    }

    @Transactional
    public Notification save(Notification notification) {
        if (notification.getId() == null) {
            em.persist(notification);
            return notification;
        }
        return em.merge(notification);
    }

    @Transactional
    public void deleteById(Long id) {
        Notification notification = em.find(Notification.class, id);
        if (notification != null) {
            em.remove(notification);
        }
    }
}
