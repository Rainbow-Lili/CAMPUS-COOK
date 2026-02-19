package tg.univlome.epl.cookplusserver.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.PackDAO;
import tg.univlome.epl.cookplusserver.entities.Boutique;
import tg.univlome.epl.cookplusserver.entities.Pack;
import tg.univlome.epl.cookplusserver.exceptions.BusinessException;
import tg.univlome.epl.cookplusserver.exceptions.EntityNotFoundException;

/**
 * Service for pack operations. Handles all business logic related to packs.
 *
 * @author DAKEY Ahoefa Light
 */
@Stateless
public class PackService {

    @Inject
    private PackDAO packDAO;

    @Inject
    private BoutiqueService boutiqueService;

    public List<Pack> findAll() {
        return packDAO.findAll();
    }

    public List<Pack> findAvailable() {
        return packDAO.findAvailable();
    }

    public Pack findById(Long id) {
        return packDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pack", id));
    }

    public List<Pack> findByBoutiqueId(Long boutiqueId) {
        boutiqueService.findById(boutiqueId); // Vérifier que la boutique existe
        return packDAO.findByBoutiqueId(boutiqueId);
    }

    public List<Pack> findByRecetteId(Long recetteId) {
        return packDAO.findByRecetteId(recetteId);
    }

    public List<Pack> searchByNom(String nom) {
        return packDAO.searchByNom(nom);
    }

    public List<Pack> findTopSelling(int limit) {
        return packDAO.findTopSelling(limit);
    }

    public List<Pack> findTopRated(int limit) {
        return packDAO.findTopRated(limit);
    }

    public List<Pack> findByPriceRange(Double minPrix, Double maxPrix) {
        if (minPrix < 0 || maxPrix < 0 || minPrix > maxPrix) {
            throw new BusinessException("Plage de prix invalide");
        }
        return packDAO.findByPriceRange(minPrix, maxPrix);
    }

    public Pack create(Pack pack, Long boutiqueId) {
        Boutique boutique = boutiqueService.findById(boutiqueId);
        
        if (!boutique.getActive()) {
            throw new BusinessException("Impossible de créer un pack pour une boutique désactivée");
        }

        pack.setBoutique(boutique);
        return packDAO.save(pack);
    }

    public Pack update(Long id, Pack pack) {
        Pack existing = findById(id);
        
        // Mise à jour des champs modifiables
        if (pack.getNom() != null) {
            existing.setNom(pack.getNom());
        }
        if (pack.getDescription() != null) {
            existing.setDescription(pack.getDescription());
        }
        if (pack.getPrix() != null) {
            if (pack.getPrix() <= 0) {
                throw new BusinessException("Le prix doit être positif");
            }
            existing.setPrix(pack.getPrix());
        }
        if (pack.getStock() != null) {
            existing.setStock(pack.getStock());
        }
        if (pack.getDisponible() != null) {
            existing.setDisponible(pack.getDisponible());
        }
        if (pack.getImageUrl() != null) {
            existing.setImageUrl(pack.getImageUrl());
        }
        if (pack.getRecetteId() != null) {
            existing.setRecetteId(pack.getRecetteId());
        }

        return packDAO.save(existing);
    }

    public void deleteById(Long id) {
        findById(id);
        packDAO.deleteById(id);
    }

    public void toggleDisponibilite(Long id) {
        Pack pack = findById(id);
        pack.setDisponible(!pack.getDisponible());
        packDAO.save(pack);
    }

    public void updateStock(Long id, int quantite) {
        Pack pack = findById(id);
        int newStock = pack.getStock() + quantite;
        
        if (newStock < 0) {
            throw new BusinessException("Stock insuffisant");
        }
        
        packDAO.updateStock(id, quantite);
    }

    public void processPurchase(Long id, int quantite) {
        Pack pack = findById(id);
        
        if (!pack.estDisponible()) {
            throw new BusinessException("Ce pack n'est pas disponible");
        }
        
        if (pack.getStock() < quantite) {
            throw new BusinessException("Stock insuffisant. Stock disponible: " + pack.getStock());
        }
        
        // Décrémenter le stock
        pack.decrementerStock(quantite);
        packDAO.save(pack);
        
        // Incrémenter le compteur de ventes
        packDAO.incrementVentes(id);
    }

    /**
     * Vérifie si un utilisateur possède ce pack (via sa boutique)
     */
    public boolean isPackOwner(Long packId, Long utilisateurId) {
        Pack pack = findById(packId);
        return boutiqueService.isBoutiqueOwner(pack.getBoutique().getId(), utilisateurId);
    }

    /**
     * Récupère les packs par catégorie
     */
    public List<Pack> findByCategorie(String categorie) {
        return packDAO.findByCategorie(categorie);
    }

    /**
     * Récupère les packs par budget
     */
    public List<Pack> findByBudget(String budget) {
        return packDAO.findByBudget(budget);
    }

    /**
     * Récupère les packs par catégorie et budget
     */
    public List<Pack> findByCategorieAndBudget(String categorie, String budget) {
        return packDAO.findByCategorieAndBudget(categorie, budget);
    }

    /**
     * Récupère les packs par type
     */
    public List<Pack> findByTypePack(String typePack) {
        return packDAO.findByTypePack(typePack);
    }
}
