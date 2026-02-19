package com.example.cook_camplus.datas.models;

public class User {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String role;
    private String photoProfil;
    private String bio;
    private Boolean actif;
    private String dateCreation;
    private String derniereConnexion;

    public User() {}

    public User(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPhotoProfil() { return photoProfil; }
    public void setPhotoProfil(String photoProfil) { this.photoProfil = photoProfil; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public Boolean getActif() { return actif; }
    public void setActif(Boolean actif) { this.actif = actif; }

    public String getDateCreation() { return dateCreation; }
    public void setDateCreation(String dateCreation) { this.dateCreation = dateCreation; }

    public String getDerniereConnexion() { return derniereConnexion; }
    public void setDerniereConnexion(String derniereConnexion) { 
        this.derniereConnexion = derniereConnexion; 
    }

    // Helper method
    public String getFullName() {
        if (prenom != null && !prenom.isEmpty()) {
            return prenom + " " + nom;
        }
        return nom;
    }

    public String getInitials() {
        String initials = "";
        if (prenom != null && !prenom.isEmpty()) {
            initials += prenom.charAt(0);
        }
        if (nom != null && !nom.isEmpty()) {
            initials += nom.charAt(0);
        }
        return initials.toUpperCase();
    }
}
