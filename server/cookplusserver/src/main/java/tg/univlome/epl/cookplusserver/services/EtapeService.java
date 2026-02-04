package tg.univlome.epl.cookplusserver.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.EtapeDAO;
import tg.univlome.epl.cookplusserver.entities.Etape;
import tg.univlome.epl.cookplusserver.exceptions.EntityNotFoundException;

@Stateless
public class EtapeService {

    @Inject
    private EtapeDAO etapeDAO;

    public List<Etape> findAll() {
        return etapeDAO.findAll();
    }

    public Etape findById(Long id) {
        return etapeDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Etape", id));
    }

    public List<Etape> findByRecetteId(Long recetteId) {
        return etapeDAO.findByRecetteId(recetteId);
    }

    public Etape save(Etape etape) {
        return etapeDAO.save(etape);
    }

    public void deleteById(Long id) {
        findById(id);
        etapeDAO.deleteById(id);
    }
}
