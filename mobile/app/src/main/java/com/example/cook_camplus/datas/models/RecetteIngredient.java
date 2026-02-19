package com.example.cook_camplus.datas.models;

public class RecetteIngredient {
    private Long id;
    private Ingredient ingredient;
    private Double quantite;
    private String unite;
    private String notes;

    public RecetteIngredient() {}

    public RecetteIngredient(Ingredient ingredient, Double quantite, String unite) {
        this.ingredient = ingredient;
        this.quantite = quantite;
        this.unite = unite;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Ingredient getIngredient() { return ingredient; }
    public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }

    public Double getQuantite() { return quantite; }
    public void setQuantite(Double quantite) { this.quantite = quantite; }

    public String getUnite() { return unite; }
    public void setUnite(String unite) { this.unite = unite; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    // Helpers
    public String getQuantiteFormatee() {
        if (quantite == null) return "";
        
        // Si c'est un nombre entier
        if (quantite == Math.floor(quantite)) {
            return String.format("%.0f", quantite);
        }
        // Si c'est une fraction simple
        if (quantite == 0.25) return "¼";
        if (quantite == 0.5) return "½";
        if (quantite == 0.75) return "¾";
        
        // Sinon afficher avec 2 décimales
        return String.format("%.2f", quantite);
    }

    public String getDisplayText() {
        String text = getQuantiteFormatee();
        if (unite != null && !unite.isEmpty()) {
            text += " " + unite;
        }
        if (ingredient != null) {
            text += " " + ingredient.getNom();
        }
        return text;
    }

    public Double calculerPrix() {
        if (ingredient == null || ingredient.getPrixUnitaire() == null || quantite == null) {
            return 0.0;
        }
        return ingredient.getPrixUnitaire() * quantite;
    }

    public RecetteIngredient ajusterQuantite(int nombrePersonnes, int nombrePersonnesBase) {
        RecetteIngredient adjusted = new RecetteIngredient();
        adjusted.setId(this.id);
        adjusted.setIngredient(this.ingredient);
        adjusted.setUnite(this.unite);
        adjusted.setNotes(this.notes);
        
        if (this.quantite != null && nombrePersonnesBase > 0) {
            double nouvelleQuantite = (this.quantite * nombrePersonnes) / (double) nombrePersonnesBase;
            adjusted.setQuantite(nouvelleQuantite);
        }
        
        return adjusted;
    }
}
