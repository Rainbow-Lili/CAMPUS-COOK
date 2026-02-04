package tg.univlome.epl.cookplusserver.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.NoteDAO;
import tg.univlome.epl.cookplusserver.entities.Note;
import tg.univlome.epl.cookplusserver.exceptions.EntityNotFoundException;

@Stateless
public class NoteService {

    @Inject
    private NoteDAO noteDAO;

    public List<Note> findAll() {
        return noteDAO.findAll();
    }

    public Note findById(Long id) {
        return noteDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note", id));
    }

    public List<Note> findByRecetteId(Long recetteId) {
        return noteDAO.findByRecetteId(recetteId);
    }

    public Note findByRecetteIdAndUtilisateurId(Long recetteId, Long utilisateurId) {
        return noteDAO.findByRecetteIdAndUtilisateurId(recetteId, utilisateurId)
                .orElseThrow(() -> new EntityNotFoundException("Note pour cette recette et cet utilisateur non trouvée"));
    }

    public Note save(Note note) {
        return noteDAO.save(note);
    }

    public void deleteById(Long id) {
        findById(id);
        noteDAO.deleteById(id);
    }
}
