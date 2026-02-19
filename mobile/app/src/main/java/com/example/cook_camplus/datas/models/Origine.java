package com.example.cook_camplus.datas.models;

public class Origine {
    private Long id;
    private String nom;
    private String description;
    private String pays;
    private String region;

    public Origine() {}

    public Origine(String nom, String pays) {
        this.nom = nom;
        this.pays = pays;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    // Helper
    public String getDisplayName() {
        if (region != null && !region.isEmpty()) {
            return nom + " (" + region + ", " + pays + ")";
        }
        return nom + " (" + pays + ")";
    }
}
