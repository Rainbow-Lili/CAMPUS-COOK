package com.example.cook_camplus.datas.models;

import com.google.gson.annotations.SerializedName;

public class Etape {
    private Long id;
    
    @SerializedName("ordre")
    private Integer numeroEtape;
    
    @SerializedName("instruction")
    private String description;
    
    @SerializedName("dureeMinutes")
    private Integer dureeEstimee;
    
    private String imageUrl;

    public Etape() {}

    public Etape(Integer numeroEtape, String description) {
        this.numeroEtape = numeroEtape;
        this.description = description;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNumeroEtape() { return numeroEtape; }
    public void setNumeroEtape(Integer numeroEtape) { this.numeroEtape = numeroEtape; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getDureeEstimee() { return dureeEstimee; }
    public void setDureeEstimee(Integer dureeEstimee) { this.dureeEstimee = dureeEstimee; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    // Helper
    public String getDureeFormatee() {
        if (dureeEstimee == null) return "";
        if (dureeEstimee < 60) {
            return dureeEstimee + " min";
        }
        int heures = dureeEstimee / 60;
        int minutes = dureeEstimee % 60;
        return heures + "h" + (minutes > 0 ? String.format("%02d", minutes) : "");
    }

    public String getTitre() {
        return "Étape " + numeroEtape;
    }
}
