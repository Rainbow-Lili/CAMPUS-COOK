package tg.univlome.epl.cookplusserver.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import tg.univlome.epl.cookplusserver.entities.Note;

/**
 * Data Access Object for Note entity. Provides database operations for ratings
 * management.
 *
 * @author DAKEY Ahoefa Light
 */
public class NoteDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Note> findById(Long id) {
        Note note = em.find(Note.class, id);
        return Optional.ofNullable(note);
    }

    @SuppressWarnings("unchecked")
    public List<Note> findAll() {
        String jpql = "SELECT n FROM Note n ORDER BY n.dateCreation DESC";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Note> findByRecetteId(Long recetteId) {
        String jpql = "SELECT n FROM Note n WHERE n.recette.id = :recetteId ORDER BY n.dateCreation DESC";
        Query query = em.createQuery(jpql);
        query.setParameter("recetteId", recetteId);
        return query.getResultList();
    }

    public Optional<Note> findByRecetteIdAndUtilisateurId(Long recetteId, Long utilisateurId) {
        String jpql = "SELECT n FROM Note n WHERE n.recette.id = :recetteId AND n.utilisateur.id = :userId";
        Query query = em.createQuery(jpql, Note.class);
        query.setParameter("recetteId", recetteId);
        query.setParameter("userId", utilisateurId);
        List<Note> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Transactional
    public Note save(Note note) {
        if (note.getId() == null) {
            em.persist(note);
            return note;
        }
        return em.merge(note);
    }

    @Transactional
    public void deleteById(Long id) {
        Note note = em.find(Note.class, id);
        if (note != null) {
            em.remove(note);
        }
    }
}
