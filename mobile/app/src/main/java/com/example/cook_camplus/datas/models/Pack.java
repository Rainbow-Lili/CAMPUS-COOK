package com.example.cook_camplus.datas.models;

import java.io.Serializable;

public class Pack implements Serializable {
    private Long id;
    private String nom;
    private String description;
    private Double prix;
    private Integer stock;
    private String categorie; // VIANDES, LEGUMES, CEREALES, CONSERVES, FRUITS
    private String budget; // 500F, 1000F, 2000F, etc.
    private Boolean actif;
    private Long boutiqueId;
    private String typePack; // STANDARD, COMPLET_RECETTE, SEMAINE, COLOCATION
    private String imageUrl;
    
    // Données supplémentaires (optionnelles depuis le backend)
    private Double noteMoyenne;
    private Integer nombreVentes;
    private Integer nombreAvis;
    private Boutique boutique; // Relation

    public Pack() {
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Long getBoutiqueId() {
        return boutiqueId;
    }

    public void setBoutiqueId(Long boutiqueId) {
        this.boutiqueId = boutiqueId;
    }

    public String getTypePack() {
        return typePack;
    }

    public void setTypePack(String typePack) {
        this.typePack = typePack;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getNoteMoyenne() {
        return noteMoyenne;
    }

    public void setNoteMoyenne(Double noteMoyenne) {
        this.noteMoyenne = noteMoyenne;
    }

    public Integer getNombreVentes() {
        return nombreVentes;
    }

    public void setNombreVentes(Integer nombreVentes) {
        this.nombreVentes = nombreVentes;
    }

    public Integer getNombreAvis() {
        return nombreAvis;
    }

    public void setNombreAvis(Integer nombreAvis) {
        this.nombreAvis = nombreAvis;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    // Helper methods
    public String getPrixFormate() {
        if (prix != null) {
            return String.format("%.0f F", prix);
        }
        return "0 F";
    }

    public boolean estDisponible() {
        return actif != null && actif && stock != null && stock > 0;
    }
}
