package com.example.cook_camplus.datas.models;

public class Ingredient {
    private Long id;
    private String nom;
    private String uniteMesure;
    private Double prixUnitaire;
    private String categorie;
    private String description;
    private Boolean disponible;
    private String dateCreation;

    public Ingredient() {}

    public Ingredient(String nom, String uniteMesure, Double prixUnitaire) {
        this.nom = nom;
        this.uniteMesure = uniteMesure;
        this.prixUnitaire = prixUnitaire;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getUniteMesure() { return uniteMesure; }
    public void setUniteMesure(String uniteMesure) { this.uniteMesure = uniteMesure; }

    public Double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(Double prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public String getDateCreation() { return dateCreation; }
    public void setDateCreation(String dateCreation) { this.dateCreation = dateCreation; }

    // Helper
    public String getPrixFormate() {
        if (prixUnitaire == null) return "0 FCFA";
        return String.format("%.0f FCFA/%s", prixUnitaire, uniteMesure);
    }
}
