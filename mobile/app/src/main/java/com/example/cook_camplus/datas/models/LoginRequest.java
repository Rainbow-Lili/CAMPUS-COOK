package com.example.cook_camplus.datas.models;

public class LoginRequest {
    private String email;
    private String motDePasse;

    public LoginRequest() {}

    public LoginRequest(String email, String motDePasse) {
        this.email = email;
        this.motDePasse = motDePasse;
    }

    // Getters & Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    // Validation
    public boolean isValid() {
        return email != null && !email.isEmpty() 
            && motDePasse != null && !motDePasse.isEmpty();
    }
}
