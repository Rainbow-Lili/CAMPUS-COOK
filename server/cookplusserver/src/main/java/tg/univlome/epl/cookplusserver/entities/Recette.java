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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

/**
 * Recette entity. Represents a recipe with ingredients, steps, and media.
 *
 * @author DAKEY Ahoefa Light
 */
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "recettes", indexes = {
    @Index(name = "idx_recette_nom", columnList = "nom"),
    @Index(name = "idx_recette_difficulte", columnList = "difficulte")
})
public class Recette implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "Le nom de la recette est obligatoire")
    @Column(name = "nom", nullable = false, length = 200)
    private String nom;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Positive(message = "Le temps de préparation doit être positif")
    @Column(name = "temps_preparation")
    private Integer tempsPreparation;

    @Column(name = "difficulte", length = 50)
    private String difficulte;

    @Column(name = "nombre_personnes_base")
    private Integer nombrePersonnesBase;

    @Column(name = "nombre_vues")
    private Integer nombreVues;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @Column(name = "est_public", nullable = false)
    private Boolean estPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origine_id")
    private Origine origine;

    @OneToMany(mappedBy = "recette", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Etape> etapes;

    @OneToMany(mappedBy = "recette", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RecetteIngredient> ingredients;

    @OneToMany(mappedBy = "recette", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Media> medias;

    @OneToMany(mappedBy = "recette", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Video> videos;

    @OneToMany(mappedBy = "recette", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Favori> favoris;

    @OneToMany(mappedBy = "recette", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Commentaire> commentaires;

    @OneToMany(mappedBy = "recette", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Note> notes;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
        this.dateModification = LocalDateTime.now();
        this.nombrePersonnesBase = Objects.requireNonNullElse(this.nombrePersonnesBase, 4);
        this.nombreVues = Objects.requireNonNullElse(this.nombreVues, 0);
        this.estPublic = Objects.requireNonNullElse(this.estPublic, Boolean.TRUE);
        this.etapes = Objects.requireNonNullElse(this.etapes, new ArrayList<>());
        this.ingredients = Objects.requireNonNullElse(this.ingredients, new ArrayList<>());
        this.medias = Objects.requireNonNullElse(this.medias, new ArrayList<>());
        this.videos = Objects.requireNonNullElse(this.videos, new ArrayList<>());
        this.favoris = Objects.requireNonNullElse(this.favoris, new ArrayList<>());
        this.commentaires = Objects.requireNonNullElse(this.commentaires, new ArrayList<>());
        this.notes = Objects.requireNonNullElse(this.notes, new ArrayList<>());
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModification = LocalDateTime.now();
    }

    public Recette() {
        this.etapes = new ArrayList<>();
        this.ingredients = new ArrayList<>();
        this.medias = new ArrayList<>();
        this.videos = new ArrayList<>();
        this.favoris = new ArrayList<>();
        this.commentaires = new ArrayList<>();
        this.notes = new ArrayList<>();
        this.nombrePersonnesBase = 4;
        this.nombreVues = 0;
        this.estPublic = Boolean.TRUE;
    }

    public Recette(String nom, String description, Integer tempsPreparation) {
        this();
        this.nom = nom;
        this.description = description;
        this.tempsPreparation = tempsPreparation;
    }

    /**
     * Calcule le coût total de la recette pour le nombre de personnes de base.
     */
    public double calculerCoutTotal() {
        return this.ingredients.stream()
                .mapToDouble(RecetteIngredient::calculerCout)
                .sum();
    }

    /**
     * Calcule le coût ajusté pour un nombre de personnes donné.
     */
    public double calculerCoutPourNPersonnes(int nombrePersonnes) {
        int base = Objects.requireNonNullElse(this.nombrePersonnesBase, 4);
        double facteur = (double) nombrePersonnes / base;
        return this.calculerCoutTotal() * facteur;
    }

    /**
     * Calcule la note moyenne attribuée à cette recette.
     */
    public double calculerNoteMoyenne() {
        return this.notes.stream()
                .mapToDouble(Note::getValeur)
                .average()
                .orElse(0.0);
    }

    /**
     * Incrémente le compteur de vues de la recette.
     */
    public void incrementerVues() {
        this.nombreVues = Objects.requireNonNullElse(this.nombreVues, 0) + 1;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTempsPreparation() {
        return this.tempsPreparation;
    }

    public void setTempsPreparation(Integer tempsPreparation) {
        this.tempsPreparation = tempsPreparation;
    }

    public String getDifficulte() {
        return this.difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public Integer getNombrePersonnesBase() {
        return this.nombrePersonnesBase;
    }

    public void setNombrePersonnesBase(Integer nombrePersonnesBase) {
        this.nombrePersonnesBase = nombrePersonnesBase;
    }

    public Integer getNombreVues() {
        return this.nombreVues;
    }

    public void setNombreVues(Integer nombreVues) {
        this.nombreVues = nombreVues;
    }

    public LocalDateTime getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateModification() {
        return this.dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    public Boolean getEstPublic() {
        return this.estPublic;
    }

    public void setEstPublic(Boolean estPublic) {
        this.estPublic = estPublic;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Origine getOrigine() {
        return this.origine;
    }

    public void setOrigine(Origine origine) {
        this.origine = origine;
    }

    public List<Etape> getEtapes() {
        return this.etapes;
    }

    public void setEtapes(List<Etape> etapes) {
        this.etapes = etapes;
    }

    public List<RecetteIngredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<RecetteIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Media> getMedias() {
        return this.medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public List<Video> getVideos() {
        return this.videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Favori> getFavoris() {
        return this.favoris;
    }

    public void setFavoris(List<Favori> favoris) {
        this.favoris = favoris;
    }

    public List<Commentaire> getCommentaires() {
        return this.commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public List<Note> getNotes() {
        return this.notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Recette)) {
            return this == o;
        }
        Recette other = (Recette) o;
        return Objects.nonNull(this.id)
                && Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return "Recette{"
                + "id=" + this.id
                + ", nom='" + this.nom + '\''
                + ", difficulte='" + this.difficulte + '\''
                + ", tempsPreparation=" + this.tempsPreparation
                + '}';
    }
}
