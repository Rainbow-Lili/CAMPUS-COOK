package tg.univlome.epl.cookplusserver.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * Ingredient entity. Represents an ingredient that can be used in recipes.
 *
 * @author DAKEY Ahoefa Light
 */
@Entity
@Table(name = "ingredients", indexes = {
    @Index(name = "idx_ingredient_nom", columnList = "nom"),
    @Index(name = "idx_ingredient_categorie", columnList = "categorie")
})
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "Le nom de l'ingrédient est obligatoire")
    @Column(name = "nom", nullable = false, unique = true, length = 100)
    private String nom;

    @NotBlank(message = "L'unité de mesure est obligatoire")
    @Column(name = "unite_mesure", nullable = false, length = 50)
    private String uniteMesure;

    @Positive(message = "Le prix unitaire doit être positif")
    @Column(name = "prix_unitaire", nullable = false)
    private Double prixUnitaire;

    @Column(name = "categorie", length = 100)
    private String categorie;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "disponible", nullable = false)
    private Boolean disponible;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RecetteIngredient> recetteIngredients;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Media> medias;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
        this.disponible = Objects.requireNonNullElse(this.disponible, Boolean.TRUE);
        this.recetteIngredients = Objects.requireNonNullElse(this.recetteIngredients, new ArrayList<>());
        this.medias = Objects.requireNonNullElse(this.medias, new ArrayList<>());
    }

    public Ingredient() {
        this.recetteIngredients = new ArrayList<>();
        this.medias = new ArrayList<>();
        this.disponible = Boolean.TRUE;
    }

    public Ingredient(String nom, String uniteMesure, Double prixUnitaire) {
        this();
        this.nom = nom;
        this.uniteMesure = uniteMesure;
        this.prixUnitaire = prixUnitaire;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUniteMesure() {
        return this.uniteMesure;
    }

    public void setUniteMesure(String uniteMesure) {
        this.uniteMesure = uniteMesure;
    }

    public Double getPrixUnitaire() {
        return this.prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDisponible() {
        return this.disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public LocalDateTime getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public List<RecetteIngredient> getRecetteIngredients() {
        return this.recetteIngredients;
    }

    public void setRecetteIngredients(List<RecetteIngredient> recetteIngredients) {
        this.recetteIngredients = recetteIngredients;
    }

    public List<Media> getMedias() {
        return this.medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ingredient)) {
            return this == o;
        }
        Ingredient other = (Ingredient) o;
        return Objects.nonNull(this.id)
                && Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return "Ingredient{"
                + "id=" + this.id
                + ", nom='" + this.nom + '\''
                + ", uniteMesure='" + this.uniteMesure + '\''
                + ", prixUnitaire=" + this.prixUnitaire
                + '}';
    }
}
