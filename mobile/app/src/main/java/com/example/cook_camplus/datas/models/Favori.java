package com.example.cook_camplus.datas.models;

public class Favori {
    private Long id;
    private String dateAjout;
    private Long utilisateurId;
    private Recette recette;

    public Favori() {}

    public Favori(Long utilisateurId, Recette recette) {
        this.utilisateurId = utilisateurId;
        this.recette = recette;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDateAjout() { return dateAjout; }
    public void setDateAjout(String dateAjout) { this.dateAjout = dateAjout; }

    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }

    public Recette getRecette() { return recette; }
    public void setRecette(Recette recette) { this.recette = recette; }
}
