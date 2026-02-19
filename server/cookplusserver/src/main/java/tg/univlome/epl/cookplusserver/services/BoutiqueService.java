package tg.univlome.epl.cookplusserver.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.BoutiqueDAO;
import tg.univlome.epl.cookplusserver.entities.Boutique;
import tg.univlome.epl.cookplusserver.entities.Utilisateur;
import tg.univlome.epl.cookplusserver.enums.RoleUtilisateur;
import tg.univlome.epl.cookplusserver.exceptions.BusinessException;
import tg.univlome.epl.cookplusserver.exceptions.EntityNotFoundException;

/**
 * Service for boutique operations. Handles all business logic related to
 * boutiques.
 *
 * @author DAKEY Ahoefa Light
 */
@Stateless
public class BoutiqueService {

    @Inject
    private BoutiqueDAO boutiqueDAO;

    @Inject
    private UtilisateurService utilisateurService;

    public List<Boutique> findAll() {
        return boutiqueDAO.findAll();
    }

    public List<Boutique> findActive() {
        return boutiqueDAO.findActive();
    }

    public Boutique findById(Long id) {
        return boutiqueDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Boutique", id));
    }

    public Boutique findByFournisseurId(Long fournisseurId) {
        return boutiqueDAO.findByFournisseurId(fournisseurId)
                .orElseThrow(() -> new EntityNotFoundException("Boutique du fournisseur " + fournisseurId + " non trouvée"));
    }

    public List<Boutique> searchByNom(String nom) {
        return boutiqueDAO.searchByNom(nom);
    }

    public List<Boutique> findTopRated(int limit) {
        return boutiqueDAO.findTopRated(limit);
    }

    public Boutique create(Boutique boutique) {
        // Vérifier que l'utilisateur est un fournisseur
        Utilisateur fournisseur = utilisateurService.findById(boutique.getFournisseur().getId());
        if (fournisseur.getRole() != RoleUtilisateur.FOURNISSEUR) {
            throw new BusinessException("Seuls les utilisateurs avec le rôle FOURNISSEUR peuvent créer une boutique");
        }

        // Vérifier qu'il n'a pas déjà une boutique
        if (boutiqueDAO.findByFournisseurId(fournisseur.getId()).isPresent()) {
            throw new BusinessException("Ce fournisseur possède déjà une boutique");
        }

        boutique.setFournisseur(fournisseur);
        return boutiqueDAO.save(boutique);
    }

    public Boutique update(Long id, Boutique boutique) {
        Boutique existing = findById(id);
        
        // Mise à jour des champs modifiables
        if (boutique.getNom() != null) {
            existing.setNom(boutique.getNom());
        }
        if (boutique.getDescription() != null) {
            existing.setDescription(boutique.getDescription());
        }
        if (boutique.getAdresse() != null) {
            existing.setAdresse(boutique.getAdresse());
        }
        if (boutique.getTelephone() != null) {
            existing.setTelephone(boutique.getTelephone());
        }
        if (boutique.getEmail() != null) {
            existing.setEmail(boutique.getEmail());
        }
        if (boutique.getLogoUrl() != null) {
            existing.setLogoUrl(boutique.getLogoUrl());
        }
        if (boutique.getHorairesOuverture() != null) {
            existing.setHorairesOuverture(boutique.getHorairesOuverture());
        }

        return boutiqueDAO.save(existing);
    }

    public void deleteById(Long id) {
        findById(id);
        boutiqueDAO.deleteById(id);
    }

    public void toggleActive(Long id) {
        Boutique boutique = findById(id);
        boutique.setActive(!boutique.getActive());
        boutiqueDAO.save(boutique);
    }

    public void updateNoteMoyenne(Long boutiqueId) {
        boutiqueDAO.updateNoteMoyenne(boutiqueId);
    }

    public Long countActive() {
        return boutiqueDAO.countActive();
    }

    /**
     * Vérifie si un utilisateur possède cette boutique
     */
    public boolean isBoutiqueOwner(Long boutiqueId, Long utilisateurId) {
        Boutique boutique = findById(boutiqueId);
        return boutique.getFournisseur().getId().equals(utilisateurId);
    }

    /**
     * Récupère les boutiques par catégorie
     */
    public List<Boutique> findByCategorie(String categorie) {
        return boutiqueDAO.findByCategorie(categorie);
    }

    /**
     * Récupère les boutiques par type
     */
    public List<Boutique> findByType(String type) {
        return boutiqueDAO.findByType(type);
    }
}
