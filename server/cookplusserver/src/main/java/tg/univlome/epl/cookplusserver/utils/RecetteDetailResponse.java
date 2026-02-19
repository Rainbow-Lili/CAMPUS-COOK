package tg.univlome.epl.cookplusserver.utils;

import tg.univlome.epl.cookplusserver.entities.Etape;
import tg.univlome.epl.cookplusserver.entities.Origine;
import tg.univlome.epl.cookplusserver.entities.Recette;
import tg.univlome.epl.cookplusserver.entities.RecetteIngredient;
import tg.univlome.epl.cookplusserver.entities.Utilisateur;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for detailed recipe response including ingredients and steps
 */
public class RecetteDetailResponse {
    
    private Long id;
    private String nom;
    private String description;
    private Integer tempsPreparation;
    private String difficulte;
    private Integer nombrePersonnesBase;
    private Integer nombreVues;
    private LocalDateTime dateCreation;
    private Boolean estPublic;
    private String imageUrl;
    private Origine origine;
    private UtilisateurSimple utilisateur;
    private List<IngredientDetail> ingredients;
    private List<EtapeDetail> etapes;
    
    public RecetteDetailResponse() {
    }
    
    public RecetteDetailResponse(Recette recette) {
        this.id = recette.getId();
        this.nom = recette.getNom();
        this.description = recette.getDescription();
        this.tempsPreparation = recette.getTempsPreparation();
        this.difficulte = recette.getDifficulte();
        this.nombrePersonnesBase = recette.getNombrePersonnesBase();
        this.nombreVues = recette.getNombreVues();
        this.dateCreation = recette.getDateCreation();
        this.estPublic = recette.getEstPublic();
        this.imageUrl = recette.getImageUrl();
        this.origine = recette.getOrigine();
        
        if (recette.getUtilisateur() != null) {
            this.utilisateur = new UtilisateurSimple(
                recette.getUtilisateur().getId(),
                recette.getUtilisateur().getNom(),
                recette.getUtilisateur().getPrenom(),
                recette.getUtilisateur().getEmail()
            );
        }
        
        this.ingredients = new ArrayList<>();
        if (recette.getIngredients() != null) {
            for (RecetteIngredient ri : recette.getIngredients()) {
                this.ingredients.add(new IngredientDetail(ri));
            }
        }
        
        this.etapes = new ArrayList<>();
        if (recette.getEtapes() != null) {
            for (Etape e : recette.getEtapes()) {
                this.etapes.add(new EtapeDetail(e));
            }
        }
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getTempsPreparation() {
        return tempsPreparation;
    }
    
    public void setTempsPreparation(Integer tempsPreparation) {
        this.tempsPreparation = tempsPreparation;
    }
    
    public String getDifficulte() {
        return difficulte;
    }
    
    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }
    
    public Integer getNombrePersonnesBase() {
        return nombrePersonnesBase;
    }
    
    public void setNombrePersonnesBase(Integer nombrePersonnesBase) {
        this.nombrePersonnesBase = nombrePersonnesBase;
    }
    
    public Integer getNombreVues() {
        return nombreVues;
    }
    
    public void setNombreVues(Integer nombreVues) {
        this.nombreVues = nombreVues;
    }
    
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
    
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public Boolean getEstPublic() {
        return estPublic;
    }
    
    public void setEstPublic(Boolean estPublic) {
        this.estPublic = estPublic;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public Origine getOrigine() {
        return origine;
    }
    
    public void setOrigine(Origine origine) {
        this.origine = origine;
    }
    
    public UtilisateurSimple getUtilisateur() {
        return utilisateur;
    }
    
    public void setUtilisateur(UtilisateurSimple utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    public List<IngredientDetail> getIngredients() {
        return ingredients;
    }
    
    public void setIngredients(List<IngredientDetail> ingredients) {
        this.ingredients = ingredients;
    }
    
    public List<EtapeDetail> getEtapes() {
        return etapes;
    }
    
    public void setEtapes(List<EtapeDetail> etapes) {
        this.etapes = etapes;
    }
    
    // Inner classes for simplified nested objects
    public static class UtilisateurSimple {
        private Long id;
        private String nom;
        private String prenom;
        private String email;
        
        public UtilisateurSimple() {
        }
        
        public UtilisateurSimple(Long id, String nom, String prenom, String email) {
            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
            this.email = email;
        }
        
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getNom() {
            return nom;
        }
        
        public void setNom(String nom) {
            this.nom = nom;
        }
        
        public String getPrenom() {
            return prenom;
        }
        
        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
    }
    
    public static class IngredientDetail {
        private Long id;
        private String nom;
        private String uniteMesure;
        private Double prixUnitaire;
        private Double quantiteBase;
        
        public IngredientDetail() {
        }
        
        public IngredientDetail(RecetteIngredient ri) {
            this.id = ri.getIngredient().getId();
            this.nom = ri.getIngredient().getNom();
            this.uniteMesure = ri.getIngredient().getUniteMesure();
            this.prixUnitaire = ri.getIngredient().getPrixUnitaire();
            this.quantiteBase = ri.getQuantiteBase();
        }
        
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getNom() {
            return nom;
        }
        
        public void setNom(String nom) {
            this.nom = nom;
        }
        
        public String getUniteMesure() {
            return uniteMesure;
        }
        
        public void setUniteMesure(String uniteMesure) {
            this.uniteMesure = uniteMesure;
        }
        
        public Double getPrixUnitaire() {
            return prixUnitaire;
        }
        
        public void setPrixUnitaire(Double prixUnitaire) {
            this.prixUnitaire = prixUnitaire;
        }
        
        public Double getQuantiteBase() {
            return quantiteBase;
        }
        
        public void setQuantiteBase(Double quantiteBase) {
            this.quantiteBase = quantiteBase;
        }
    }
    
    public static class EtapeDetail {
        private Long id;
        private Integer ordre;
        private String instruction;
        private Integer dureeMinutes;
        
        public EtapeDetail() {
        }
        
        public EtapeDetail(Etape etape) {
            this.id = etape.getId();
            this.ordre = etape.getOrdre();
            this.instruction = etape.getInstruction();
            this.dureeMinutes = etape.getDureeMinutes();
        }
        
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public Integer getOrdre() {
            return ordre;
        }
        
        public void setOrdre(Integer ordre) {
            this.ordre = ordre;
        }
        
        public String getInstruction() {
            return instruction;
        }
        
        public void setInstruction(String instruction) {
            this.instruction = instruction;
        }
        
        public Integer getDureeMinutes() {
            return dureeMinutes;
        }
        
        public void setDureeMinutes(Integer dureeMinutes) {
            this.dureeMinutes = dureeMinutes;
        }
    }
}
