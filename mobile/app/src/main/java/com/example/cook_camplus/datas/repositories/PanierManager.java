package com.example.cook_camplus.datas.repositories;

import com.example.cook_camplus.datas.models.Pack;
import com.example.cook_camplus.datas.models.PanierItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanierManager {
    private static PanierManager instance;
    private Map<Long, List<PanierItem>> panierParBoutique; // Regroupé par boutique
    
    private PanierManager() {
        panierParBoutique = new HashMap<>();
    }
    
    public static synchronized PanierManager getInstance() {
        if (instance == null) {
            instance = new PanierManager();
        }
        return instance;
    }
    
    // Ajouter un pack au panier
    public void ajouterPack(Pack pack) {
        Long boutiqueId = pack.getBoutiqueId();
        
        if (!panierParBoutique.containsKey(boutiqueId)) {
            panierParBoutique.put(boutiqueId, new ArrayList<>());
        }
        
        List<PanierItem> items = panierParBoutique.get(boutiqueId);
        
        // Vérifier si le pack existe déjà
        boolean trouve = false;
        for (PanierItem item : items) {
            if (item.getPack().getId().equals(pack.getId())) {
                item.setQuantite(item.getQuantite() + 1);
                trouve = true;
                break;
            }
        }
        
        if (!trouve) {
            items.add(new PanierItem(pack, 1));
        }
    }
    
    // Retirer un pack du panier
    public void retirerPack(Pack pack) {
        Long boutiqueId = pack.getBoutiqueId();
        
        if (panierParBoutique.containsKey(boutiqueId)) {
            List<PanierItem> items = panierParBoutique.get(boutiqueId);
            items.removeIf(item -> item.getPack().getId().equals(pack.getId()));
            
            // Si la liste est vide, retirer la boutique
            if (items.isEmpty()) {
                panierParBoutique.remove(boutiqueId);
            }
        }
    }
    
    // Modifier la quantité
    public void modifierQuantite(Pack pack, int nouvelleQuantite) {
        if (nouvelleQuantite <= 0) {
            retirerPack(pack);
            return;
        }
        
        Long boutiqueId = pack.getBoutiqueId();
        
        if (panierParBoutique.containsKey(boutiqueId)) {
            List<PanierItem> items = panierParBoutique.get(boutiqueId);
            for (PanierItem item : items) {
                if (item.getPack().getId().equals(pack.getId())) {
                    item.setQuantite(nouvelleQuantite);
                    break;
                }
            }
        }
    }
    
    // Obtenir tous les items du panier
    public List<PanierItem> getTousLesItems() {
        List<PanierItem> tousItems = new ArrayList<>();
        for (List<PanierItem> items : panierParBoutique.values()) {
            tousItems.addAll(items);
        }
        return tousItems;
    }
    
    // Obtenir les items d'une boutique spécifique
    public List<PanierItem> getItemsParBoutique(Long boutiqueId) {
        return panierParBoutique.getOrDefault(boutiqueId, new ArrayList<>());
    }
    
    // Obtenir toutes les boutiques du panier
    public List<Long> getBoutiquesIds() {
        return new ArrayList<>(panierParBoutique.keySet());
    }
    
    // Calculer le total
    public double getTotal() {
        double total = 0;
        for (PanierItem item : getTousLesItems()) {
            total += item.getSousTotal();
        }
        return total;
    }
    
    // Calculer le total par boutique
    public double getTotalParBoutique(Long boutiqueId) {
        double total = 0;
        for (PanierItem item : getItemsParBoutique(boutiqueId)) {
            total += item.getSousTotal();
        }
        return total;
    }
    
    // Obtenir le nombre total d'articles
    public int getNombreArticles() {
        int total = 0;
        for (PanierItem item : getTousLesItems()) {
            total += item.getQuantite();
        }
        return total;
    }
    
    // Vider le panier
    public void viderPanier() {
        panierParBoutique.clear();
    }
    
    // Vider le panier pour une boutique
    public void viderPanierBoutique(Long boutiqueId) {
        panierParBoutique.remove(boutiqueId);
    }
    
    // Vérifier si le panier est vide
    public boolean estVide() {
        return panierParBoutique.isEmpty();
    }
}
