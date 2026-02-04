package tg.univlome.epl.cookplusserver.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.OrigineDAO;
import tg.univlome.epl.cookplusserver.entities.Origine;
import tg.univlome.epl.cookplusserver.exceptions.EntityNotFoundException;

@Stateless
public class OrigineService {

    @Inject
    private OrigineDAO origineDAO;

    public List<Origine> findAll() {
        return origineDAO.findAll();
    }

    public Origine findById(Long id) {
        return origineDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Origine", id));
    }

    public Origine findByNom(String nom) {
        return origineDAO.findByNom(nom)
                .orElseThrow(() -> new EntityNotFoundException("Origine avec le nom " + nom + " non trouvée"));
    }

    public Origine save(Origine origine) {
        return origineDAO.save(origine);
    }

    public void deleteById(Long id) {
        findById(id);
        origineDAO.deleteById(id);
    }
}
