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
 * Etape entity. Represents a step in a recipe preparation.
 *
 * @author DAKEY Ahoefa Light
 */
@Entity
@Table(name = "etapes")
public class Etape implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recette_id", nullable = false)
    private Recette recette;

    @Column(name = "ordre", nullable = false)
    private Integer ordre;

    @NotBlank(message = "L'instruction est obligatoire")
    @Column(name = "instruction", columnDefinition = "TEXT", nullable = false)
    private String instruction;

    @Column(name = "duree_minutes")
    private Integer dureeMinutes;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
        this.ordre = Objects.requireNonNullElse(this.ordre, 1);
    }

    public Etape() {
        this.ordre = 1;
    }

    public Etape(Integer ordre, String instruction) {
        this();
        this.ordre = ordre;
        this.instruction = instruction;
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

    public Integer getOrdre() {
        return this.ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public String getInstruction() {
        return this.instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Integer getDureeMinutes() {
        return this.dureeMinutes;
    }

    public void setDureeMinutes(Integer dureeMinutes) {
        this.dureeMinutes = dureeMinutes;
    }

    public LocalDateTime getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Etape)) {
            return this == o;
        }
        Etape other = (Etape) o;
        return Objects.nonNull(this.id) && Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return "Etape{" + "id=" + this.id + ", ordre=" + this.ordre + '}';
    }
}
