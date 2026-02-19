package com.example.cook_camplus.datas.models;

public class Commentaire {
    private Long id;
    private String contenu;
    private String dateCreation;
    private User utilisateur;
    private Long recetteId;

    public Commentaire() {}

    public Commentaire(String contenu, User utilisateur, Long recetteId) {
        this.contenu = contenu;
        this.utilisateur = utilisateur;
        this.recetteId = recetteId;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public String getDateCreation() { return dateCreation; }
    public void setDateCreation(String dateCreation) { this.dateCreation = dateCreation; }

    public User getUtilisateur() { return utilisateur; }
    public void setUtilisateur(User utilisateur) { this.utilisateur = utilisateur; }

    public Long getRecetteId() { return recetteId; }
    public void setRecetteId(Long recetteId) { this.recetteId = recetteId; }

    // Helpers
    public String getAuteurNom() {
        return utilisateur != null ? utilisateur.getFullName() : "Anonyme";
    }

    public String getDateFormatee() {
        // TODO: Formatter la date en "il y a X minutes/heures/jours"
        return dateCreation != null ? dateCreation : "";
    }
}
