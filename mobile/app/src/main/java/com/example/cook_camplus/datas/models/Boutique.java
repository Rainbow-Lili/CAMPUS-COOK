package com.example.cook_camplus.datas.models;

import java.io.Serializable;

public class Boutique implements Serializable {
    private Long id;
    private String nom;
    private String description;
    private String type; // PACK_STANDARD, BOUTIQUE_SPECIALISEE
    private String categorie; // VIANDES, LEGUMES, CEREALES, CONSERVES, FRUITS, etc.
    private String adresse;
    private String telephone;
    private String email;
    private String logoUrl;
    private String horairesOuverture;
    private Boolean active;
    
    // Données supplémentaires (optionnelles)
    private Double noteMoyenne;
    private Integer nombreAvis;
    private Long fournisseurId;

    public Boutique() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getHorairesOuverture() {
        return horairesOuverture;
    }

    public void setHorairesOuverture(String horairesOuverture) {
        this.horairesOuverture = horairesOuverture;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Double getNoteMoyenne() {
        return noteMoyenne;
    }

    public void setNoteMoyenne(Double noteMoyenne) {
        this.noteMoyenne = noteMoyenne;
    }

    public Integer getNombreAvis() {
        return nombreAvis;
    }

    public void setNombreAvis(Integer nombreAvis) {
        this.nombreAvis = nombreAvis;
    }

    public Long getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    // Helper methods
    public boolean estBoutiqueSpecialisee() {
        return "BOUTIQUE_SPECIALISEE".equals(type);
    }

    public String getNoteFormatee() {
        if (noteMoyenne != null) {
            return String.format("%.1f ⭐", noteMoyenne);
        }
        return "Nouveau";
    }
}
