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
 * Video entity. Represents a video associated with a recipe.
 *
 * @author DAKEY Ahoefa Light
 */
@Entity
@Table(name = "videos")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "L'URL est obligatoire")
    @Column(name = "url", nullable = false, length = 1000)
    private String url;

    @Column(name = "titre", length = 200)
    private String titre;

    @Column(name = "duree_seconds")
    private Integer dureeSeconds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recette_id")
    private Recette recette;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
    }

    public Video() {
    }

    public Video(String url) {
        this.url = url;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Integer getDureeSeconds() {
        return this.dureeSeconds;
    }

    public void setDureeSeconds(Integer dureeSeconds) {
        this.dureeSeconds = dureeSeconds;
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
        if (!(o instanceof Video)) {
            return this == o;
        }
        Video other = (Video) o;
        return Objects.nonNull(this.id) && Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return "Video{" + "id=" + this.id + ", url='" + this.url + '\'' + '}';
    }
}
