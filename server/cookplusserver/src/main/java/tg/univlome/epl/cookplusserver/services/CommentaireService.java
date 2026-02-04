package tg.univlome.epl.cookplusserver.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.CommentaireDAO;
import tg.univlome.epl.cookplusserver.entities.Commentaire;
import tg.univlome.epl.cookplusserver.exceptions.EntityNotFoundException;

@Stateless
public class CommentaireService {

    @Inject
    private CommentaireDAO commentaireDAO;

    public List<Commentaire> findAll() {
        return commentaireDAO.findAll();
    }

    public Commentaire findById(Long id) {
        return commentaireDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commentaire", id));
    }

    public List<Commentaire> findByRecetteIdAndActive(Long recetteId) {
        return commentaireDAO.findByRecetteIdAndActive(recetteId);
    }

    public List<Commentaire> findByUtilisateurId(Long utilisateurId) {
        return commentaireDAO.findByUtilisateurId(utilisateurId);
    }

    public Commentaire save(Commentaire commentaire) {
        return commentaireDAO.save(commentaire);
    }

    public void deleteById(Long id) {
        findById(id);
        commentaireDAO.deleteById(id);
    }

    public void markAsInactive(Long id) {
        Commentaire commentaire = findById(id);
        commentaire.setActif(Boolean.FALSE);
        commentaireDAO.save(commentaire);
    }
}
