package tg.univlome.epl.cookplusserver.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;

/**
 * RecetteIngredient entity. Represents the relationship between a recipe and
 * its ingredients.
 *
 * @author DAKEY Ahoefa Light
 */
@Entity
@Table(name = "recette_ingredients")
public class RecetteIngredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recette_id", nullable = false)
    private Recette recette;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Positive(message = "La quantité de base doit être positive")
    @Column(name = "quantite_base", nullable = false)
    private Double quantiteBase;

    @Column(name = "optionnel", nullable = false)
    private Boolean optionnel;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @PrePersist
    protected void onCreate() {
        this.optionnel = Objects.requireNonNullElse(this.optionnel, Boolean.FALSE);
    }

    public RecetteIngredient() {
        this.optionnel = Boolean.FALSE;
    }

    public RecetteIngredient(Recette recette, Ingredient ingredient, Double quantiteBase) {
        this();
        this.recette = recette;
        this.ingredient = ingredient;
        this.quantiteBase = quantiteBase;
    }

    public double calculerCout() {
        Double prix = Objects.requireNonNullElse(this.ingredient.getPrixUnitaire(), 0.0);
        Double quantite = Objects.requireNonNullElse(this.quantiteBase, 0.0);
        return quantite * prix;
    }

    public double ajusterQuantite(int nombrePersonnes, int nombrePersonnesBase) {
        int base = Math.max(nombrePersonnesBase, 1);
        double facteur = (double) nombrePersonnes / base;
        return Objects.requireNonNullElse(this.quantiteBase, 0.0) * facteur;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recette getRecette() {
        return this.recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Double getQuantiteBase() {
        return this.quantiteBase;
    }

    public void setQuantiteBase(Double quantiteBase) {
        this.quantiteBase = quantiteBase;
    }

    public Boolean getOptionnel() {
        return this.optionnel;
    }

    public void setOptionnel(Boolean optionnel) {
        this.optionnel = optionnel;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RecetteIngredient)) {
            return this == o;
        }
        RecetteIngredient other = (RecetteIngredient) o;
        return Objects.nonNull(this.id)
                && Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return "RecetteIngredient{"
                + "id=" + this.id
                + ", quantiteBase=" + this.quantiteBase
                + ", optionnel=" + this.optionnel
                + '}';
    }
}
