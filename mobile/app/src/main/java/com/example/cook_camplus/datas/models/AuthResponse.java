package com.example.cook_camplus.datas.models;

public class AuthResponse {
    private String token;
    private Long id;
    private String nom;
    private String email;
    private String role;
    private Long expiresIn;
    private String message;

    public AuthResponse() {}

    public AuthResponse(String token, Long id, String nom, String email, String role) {
        this.token = token;
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.role = role;
    }

    // Getters & Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public Long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(Long expiresIn) { this.expiresIn = expiresIn; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    // Helper
    public boolean isSuccess() {
        return token != null && !token.isEmpty();
    }
}
