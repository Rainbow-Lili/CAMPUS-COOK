package tg.univlome.epl.cookplusserver.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.ContenuPackDAO;
import tg.univlome.epl.cookplusserver.entities.ContenuPack;
import tg.univlome.epl.cookplusserver.entities.Ingredient;
import tg.univlome.epl.cookplusserver.entities.Pack;
import tg.univlome.epl.cookplusserver.exceptions.BusinessException;
import tg.univlome.epl.cookplusserver.exceptions.EntityNotFoundException;

/**
 * Service for contenu pack operations. Handles all business logic related to
 * pack contents.
 *
 * @author DAKEY Ahoefa Light
 */
@Stateless
public class ContenuPackService {

    @Inject
    private ContenuPackDAO contenuPackDAO;

    @Inject
    private PackService packService;

    @Inject
    private IngredientService ingredientService;

    public ContenuPack findById(Long id) {
        return contenuPackDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ContenuPack", id));
    }

    public List<ContenuPack> findByPackId(Long packId) {
        packService.findById(packId); // Vérifier que le pack existe
        return contenuPackDAO.findByPackId(packId);
    }

    public List<ContenuPack> findByIngredientId(Long ingredientId) {
        ingredientService.findById(ingredientId); // Vérifier que l'ingrédient existe
        return contenuPackDAO.findByIngredientId(ingredientId);
    }

    public ContenuPack create(Long packId, Long ingredientId, Double quantite, String unite) {
        Pack pack = packService.findById(packId);
        Ingredient ingredient = ingredientService.findById(ingredientId);

        // Vérifier qu'il n'existe pas déjà
        if (contenuPackDAO.findByPackAndIngredient(packId, ingredientId).isPresent()) {
            throw new BusinessException("Cet ingrédient existe déjà dans le pack");
        }

        if (quantite == null || quantite <= 0) {
            throw new BusinessException("La quantité doit être positive");
        }

        ContenuPack contenu = new ContenuPack(pack, ingredient, quantite, unite != null ? unite : "g");
        return contenuPackDAO.save(contenu);
    }

    public ContenuPack update(Long id, ContenuPack contenuPack) {
        ContenuPack existing = findById(id);

        if (contenuPack.getQuantite() != null) {
            if (contenuPack.getQuantite() <= 0) {
                throw new BusinessException("La quantité doit être positive");
            }
            existing.setQuantite(contenuPack.getQuantite());
        }

        if (contenuPack.getUnite() != null) {
            existing.setUnite(contenuPack.getUnite());
        }

        if (contenuPack.getOrdre() != null) {
            existing.setOrdre(contenuPack.getOrdre());
        }

        return contenuPackDAO.save(existing);
    }

    public void deleteById(Long id) {
        findById(id);
        contenuPackDAO.deleteById(id);
    }

    public void deleteByPackId(Long packId) {
        packService.findById(packId); // Vérifier que le pack existe
        contenuPackDAO.deleteByPackId(packId);
    }

    public int countByPackId(Long packId) {
        packService.findById(packId); // Vérifier que le pack existe
        return contenuPackDAO.countByPackId(packId);
    }

    public Double calculateTotalPrice(Long packId) {
        packService.findById(packId); // Vérifier que le pack existe
        return contenuPackDAO.calculateTotalPrice(packId);
    }

    /**
     * Calcule la marge du pack (prix de vente - coût des ingrédients)
     */
    public Double calculateMargin(Long packId) {
        Pack pack = packService.findById(packId);
        Double coutIngredients = calculateTotalPrice(packId);
        return pack.getPrix() - coutIngredients;
    }

    /**
     * Calcule le pourcentage de marge du pack
     */
    public Double calculateMarginPercentage(Long packId) {
        Pack pack = packService.findById(packId);
        Double coutIngredients = calculateTotalPrice(packId);
        
        if (coutIngredients == 0) {
            return 0.0;
        }
        
        return ((pack.getPrix() - coutIngredients) / coutIngredients) * 100;
    }
}
