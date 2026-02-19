package com.example.cook_camplus.datas.models;

public class Note {
    private Long id;
    private Integer valeur;
    private String dateCreation;
    private Long utilisateurId;
    private Long recetteId;

    public Note() {}

    public Note(Integer valeur, Long utilisateurId, Long recetteId) {
        this.valeur = valeur;
        this.utilisateurId = utilisateurId;
        this.recetteId = recetteId;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getValeur() { return valeur; }
    public void setValeur(Integer valeur) { this.valeur = valeur; }

    public String getDateCreation() { return dateCreation; }
    public void setDateCreation(String dateCreation) { this.dateCreation = dateCreation; }

    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }

    public Long getRecetteId() { return recetteId; }
    public void setRecetteId(Long recetteId) { this.recetteId = recetteId; }

    // Helper
    public boolean isValid() {
        return valeur != null && valeur >= 1 && valeur <= 5;
    }
}
