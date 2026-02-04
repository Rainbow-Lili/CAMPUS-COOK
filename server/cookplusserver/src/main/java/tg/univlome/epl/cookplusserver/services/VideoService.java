package tg.univlome.epl.cookplusserver.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.VideoDAO;
import tg.univlome.epl.cookplusserver.entities.Video;
import tg.univlome.epl.cookplusserver.exceptions.EntityNotFoundException;

@Stateless
public class VideoService {

    @Inject
    private VideoDAO videoDAO;

    public List<Video> findAll() {
        return videoDAO.findAll();
    }

    public Video findById(Long id) {
        return videoDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Video", id));
    }

    public List<Video> findByRecetteId(Long recetteId) {
        return videoDAO.findByRecetteId(recetteId);
    }

    public Video save(Video video) {
        return videoDAO.save(video);
    }

    public void deleteById(Long id) {
        findById(id);
        videoDAO.deleteById(id);
    }
}
