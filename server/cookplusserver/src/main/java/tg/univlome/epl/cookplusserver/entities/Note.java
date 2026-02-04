package tg.univlome.epl.cookplusserver.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Note entity. Represents a rating given to a recipe.
 *
 * @author DAKEY Ahoefa Light
 */
@Entity
@Table(name = "notes")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Min(value = 0, message = "La note doit être au moins 0")
    @Max(value = 5, message = "La note doit être au plus 5")
    @Column(name = "valeur", nullable = false)
    private Integer valeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recette_id")
    private Recette recette;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
        this.valeur = Objects.requireNonNullElse(this.valeur, 0);
    }

    public Note() {
        this.valeur = 0;
    }

    public Note(Integer valeur) {
        this();
        this.valeur = valeur;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValeur() {
        return this.valeur;
    }

    public void setValeur(Integer valeur) {
        this.valeur = valeur;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Recette getRecette() {
        return this.recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    public LocalDateTime getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Note)) {
            return this == o;
        }
        Note other = (Note) o;
        return Objects.nonNull(this.id) && Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return "Note{" + "id=" + this.id + ", valeur=" + this.valeur + '}';
    }
}
