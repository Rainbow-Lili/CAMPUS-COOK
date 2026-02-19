package tg.univlome.epl.cookplusserver.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * ContenuPack entity. Represents the content/ingredients of a pack.
 * This is an association entity between Pack and Ingredient with quantity.
 *
 * @author DAKEY Ahoefa Light
 */
@Entity
@Table(name = "contenu_packs", 
    indexes = {
        @Index(name = "idx_contenu_pack", columnList = "pack_id"),
        @Index(name = "idx_contenu_ingredient", columnList = "ingredient_id")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_pack_ingredient", columnNames = {"pack_id", "ingredient_id"})
    }
)
public class ContenuPack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "Le pack est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pack_id", nullable = false)
    private Pack pack;

    @NotNull(message = "L'ingrédient est obligatoire")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Positive(message = "La quantité doit être positive")
    @Column(name = "quantite", nullable = false)
    private Double quantite;

    @Column(name = "unite", length = 20, nullable = false)
    private String unite = "g"; // Grammes par défaut

    @Column(name = "ordre")
    private Integer ordre; // Pour l'affichage ordonné des ingrédients

    // Constructors
    public ContenuPack() {
    }

    public ContenuPack(Pack pack, Ingredient ingredient, Double quantite, String unite) {
        this.pack = pack;
        this.ingredient = ingredient;
        this.quantite = quantite;
        this.unite = unite;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    // Business methods
    public String getQuantiteFormatee() {
        if (quantite == null) return "N/A";
        
        // Format selon l'unité
        if (quantite >= 1000 && "g".equals(unite)) {
            return String.format("%.1f kg", quantite / 1000);
        } else if (quantite >= 1000 && "ml".equals(unite)) {
            return String.format("%.1f L", quantite / 1000);
        } else {
            // Format avec ou sans décimales selon le cas
            if (quantite % 1 == 0) {
                return String.format("%.0f %s", quantite, unite);
            } else {
                return String.format("%.1f %s", quantite, unite);
            }
        }
    }

    public Double calculerPrix() {
        if (ingredient == null || ingredient.getPrixUnitaire() == null || quantite == null) {
            return 0.0;
        }
        return ingredient.getPrixUnitaire() * quantite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContenuPack)) return false;
        ContenuPack that = (ContenuPack) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "ContenuPack{" +
                "id=" + id +
                ", ingredient=" + (ingredient != null ? ingredient.getNom() : "null") +
                ", quantite=" + quantite +
                ", unite='" + unite + '\'' +
                '}';
    }
}
