package com.example.cook_camplus.datas.models;

import com.example.cook_camplus.R;
import java.util.ArrayList;
import java.util.List;

public class Recette {
    private Long id;
    private String nom;
    private String description;
    private Integer tempsPreparation;
    private String difficulte;
    private Integer nombrePersonnesBase;
    private Integer nombreVues;
    private String dateCreation;
    private String dateModification;
    private Boolean estPublic;
    private String imageUrl;
    
    // Relations (simplifiées)
    private User utilisateur;
    private Origine origine;
    private List<Etape> etapes;
    private List<RecetteIngredient> ingredients;
    private List<Media> medias;
    private List<Video> videos;
    
    // Transient field for UI (not from API)
    private boolean hasUserAllergy = false;

    public Recette() {
        this.etapes = new ArrayList<>();
        this.ingredients = new ArrayList<>();
        this.medias = new ArrayList<>();
        this.videos = new ArrayList<>();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getTempsPreparation() { return tempsPreparation; }
    public void setTempsPreparation(Integer tempsPreparation) { 
        this.tempsPreparation = tempsPreparation; 
    }

    public String getDifficulte() { return difficulte; }
    public void setDifficulte(String difficulte) { this.difficulte = difficulte; }

    public Integer getNombrePersonnesBase() { return nombrePersonnesBase; }
    public void setNombrePersonnesBase(Integer nombrePersonnesBase) { 
        this.nombrePersonnesBase = nombrePersonnesBase; 
    }

    public Integer getNombreVues() { return nombreVues; }
    public void setNombreVues(Integer nombreVues) { this.nombreVues = nombreVues; }

    public String getDateCreation() { return dateCreation; }
    public void setDateCreation(String dateCreation) { this.dateCreation = dateCreation; }

    public String getDateModification() { return dateModification; }
    public void setDateModification(String dateModification) { 
        this.dateModification = dateModification; 
    }

    public Boolean getEstPublic() { return estPublic; }
    public void setEstPublic(Boolean estPublic) { this.estPublic = estPublic; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public User getUtilisateur() { return utilisateur; }
    public void setUtilisateur(User utilisateur) { this.utilisateur = utilisateur; }

    public Origine getOrigine() { return origine; }
    public void setOrigine(Origine origine) { this.origine = origine; }

    public List<Etape> getEtapes() { return etapes; }
    public void setEtapes(List<Etape> etapes) { this.etapes = etapes; }

    public List<RecetteIngredient> getIngredients() { return ingredients; }
    public void setIngredients(List<RecetteIngredient> ingredients) { 
        this.ingredients = ingredients; 
    }

    public List<Media> getMedias() { return medias; }
    public void setMedias(List<Media> medias) { this.medias = medias; }

    public List<Video> getVideos() { return videos; }
    public void setVideos(List<Video> videos) { this.videos = videos; }

    // Helpers
    public String getImagePrincipale() {
        // Priorité à imageUrl depuis l'API
        if (imageUrl != null && !imageUrl.isEmpty()) {
            return imageUrl;
        }
        // Fallback sur medias si disponible
        if (medias != null && !medias.isEmpty()) {
            return medias.get(0).getUrl();
        }
        return null;
    }

    public String getTempsFormate() {
        if (tempsPreparation == null) return "N/A";
        if (tempsPreparation < 60) {
            return tempsPreparation + " min";
        }
        int heures = tempsPreparation / 60;
        int minutes = tempsPreparation % 60;
        if (minutes == 0) {
            return heures + "h";
        }
        return heures + "h" + String.format("%02d", minutes);
    }

    public int getDifficulteIconResId() {
        if (difficulte == null) return R.drawable.ic_difficulty_unknown;
        switch (difficulte.toLowerCase()) {
            case "facile": return R.drawable.ic_difficulty_easy;
            case "moyen": return R.drawable.ic_difficulty_medium;
            case "difficile": return R.drawable.ic_difficulty_hard;
            default: return R.drawable.ic_difficulty_unknown;
        }
    }

    public Double calculerCoutTotal() {
        if (ingredients == null) return 0.0;
        double total = 0.0;
        for (RecetteIngredient ri : ingredients) {
            total += ri.calculerPrix();
        }
        return total;
    }

    public String getCoutTotalFormate() {
        return String.format("%.0f FCFA", calculerCoutTotal());
    }

    public List<RecetteIngredient> ajusterIngredientsQuantites(int nombrePersonnes) {
        if (ingredients == null || nombrePersonnesBase == null || nombrePersonnesBase == 0) {
            return ingredients;
        }
        
        List<RecetteIngredient> adjusted = new ArrayList<>();
        for (RecetteIngredient ri : ingredients) {
            adjusted.add(ri.ajusterQuantite(nombrePersonnes, nombrePersonnesBase));
        }
        return adjusted;
    }

    public boolean hasVideo() {
        return videos != null && !videos.isEmpty();
    }

    public String getAuteurNom() {
        return utilisateur != null ? utilisateur.getFullName() : "Anonyme";
    }
    
    public boolean hasUserAllergy() {
        return hasUserAllergy;
    }
    
    public void setHasUserAllergy(boolean hasUserAllergy) {
        this.hasUserAllergy = hasUserAllergy;
    }
}
