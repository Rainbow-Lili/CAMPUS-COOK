package com.example.cook_camplus.datas.models;

public class SignUpRequest {
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;

    public SignUpRequest() {}

    public SignUpRequest(String nom, String prenom, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    // Getters & Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    // Validation
    public boolean isValid() {
        return nom != null && !nom.isEmpty()
            && prenom != null && !prenom.isEmpty()
            && email != null && !email.isEmpty()
            && motDePasse != null && motDePasse.length() >= 8;
    }

    public String getValidationError() {
        if (nom == null || nom.isEmpty()) return "Le nom est requis";
        if (prenom == null || prenom.isEmpty()) return "Le prénom est requis";
        if (email == null || email.isEmpty()) return "L'email est requis";
        if (!isValidEmail(email)) return "Format d'email invalide";
        if (motDePasse == null || motDePasse.length() < 8) 
            return "Le mot de passe doit contenir au moins 8 caractères";
        return null;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
