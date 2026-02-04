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
import jakarta.validation.constraints.NotBlank;

/**
 * Commentaire entity. Represents a comment on a recipe.
 *
 * @author DAKEY Ahoefa Light
 */
@Entity
@Table(name = "commentaires")
public class Commentaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "Le contenu du commentaire est obligatoire")
    @Column(name = "contenu", columnDefinition = "TEXT", nullable = false)
    private String contenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recette_id")
    private Recette recette;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "actif", nullable = false)
    private Boolean actif;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
        this.actif = Objects.requireNonNullElse(this.actif, Boolean.TRUE);
    }

    public Commentaire() {
        this.actif = Boolean.TRUE;
    }

    public Commentaire(String contenu) {
        this();
        this.contenu = contenu;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return this.contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
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

    public Boolean getActif() {
        return this.actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Commentaire)) {
            return this == o;
        }
        Commentaire other = (Commentaire) o;
        return Objects.nonNull(this.id) && Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id=" + this.id + ", actif=" + this.actif + '}';
    }
}
