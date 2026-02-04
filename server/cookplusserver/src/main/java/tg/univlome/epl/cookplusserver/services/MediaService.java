package tg.univlome.epl.cookplusserver.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.MediaDAO;
import tg.univlome.epl.cookplusserver.entities.Media;
import tg.univlome.epl.cookplusserver.exceptions.EntityNotFoundException;

@Stateless
public class MediaService {

    @Inject
    private MediaDAO mediaDAO;

    public List<Media> findAll() {
        return mediaDAO.findAll();
    }

    public Media findById(Long id) {
        return mediaDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Media", id));
    }

    public List<Media> findByRecetteId(Long recetteId) {
        return mediaDAO.findByRecetteId(recetteId);
    }

    public List<Media> findByIngredientId(Long ingredientId) {
        return mediaDAO.findByIngredientId(ingredientId);
    }

    public Media save(Media media) {
        return mediaDAO.save(media);
    }

    public void deleteById(Long id) {
        findById(id);
        mediaDAO.deleteById(id);
    }
}
