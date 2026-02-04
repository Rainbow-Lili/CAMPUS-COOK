package tg.univlome.epl.cookplusserver.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import tg.univlome.epl.cookplusserver.entities.Video;

/**
 * Data Access Object for Video entity. Provides database operations for video
 * management.
 *
 * @author DAKEY Ahoefa Light
 */
public class VideoDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Video> findById(Long id) {
        Video video = em.find(Video.class, id);
        return Optional.ofNullable(video);
    }

    @SuppressWarnings("unchecked")
    public List<Video> findAll() {
        String jpql = "SELECT v FROM Video v ORDER BY v.dateCreation DESC";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Video> findByRecetteId(Long recetteId) {
        String jpql = "SELECT v FROM Video v WHERE v.recette.id = :recetteId";
        Query query = em.createQuery(jpql);
        query.setParameter("recetteId", recetteId);
        return query.getResultList();
    }

    @Transactional
    public Video save(Video video) {
        if (video.getId() == null) {
            em.persist(video);
            return video;
        }
        return em.merge(video);
    }

    @Transactional
    public void deleteById(Long id) {
        Video video = em.find(Video.class, id);
        if (video != null) {
            em.remove(video);
        }
    }
}
