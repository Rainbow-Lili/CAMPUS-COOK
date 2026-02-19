package tg.univlome.epl.cookplusserver.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.bind.annotation.JsonbTransient;
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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * Pack entity. Represents an ingredient package sold by a boutique.
 *
 * @author DAKEY Ahoefa Light
 */
@Entity
@Table(name = "packs", indexes = {
    @Index(name = "idx_pack_boutique", columnList = "boutique_id"),
    @Index(name = "idx_pack_disponible", columnList = "disponible")
})
public class Pack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "Le nom du pack est obligatoire")
    @Column(name = "nom", nullable = false, length = 200)
    private String nom;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Positive(message = "Le prix doit être positif")
    @Column(name = "prix", nullable = false)
    private Double prix;

    @Column(name = "stock", nullable = false)
    private Integer stock = 0;

    @Column(name = "disponible", nullable = false)
    private Boolean disponible = true;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "recette_id")
    private Long recetteId; // ID de la recette associée (optionnel)

    @Column(name = "note_moyenne")
    private Double noteMoyenne = 0.0;

    @Column(name = "nombre_avis")
    private Integer nombreAvis = 0;

    @Column(name = "nombre_ventes")
    private Integer nombreVentes = 0;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @Column(name = "categorie", length = 100)
    private String categorie; // VIANDES, LEGUMES, CEREALES, CONSERVES, FRUITS

    @Column(name = "budget", length = 50)
    private String budget; // 500F, 1000F, 2000F, etc.

    @Column(name = "type_pack", length = 100)
    private String typePack; // STANDARD, COMPLET_RECETTE, SEMAINE, COLOCATION

    // Relations
    @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boutique_id", nullable = false)
    private Boutique boutique;

    @JsonbTransient
    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ContenuPack> contenus = new ArrayList<>();

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
        if (this.disponible == null) {
            this.disponible = true;
        }
        if (this.stock == null) {
            this.stock = 0;
        }
        if (this.noteMoyenne == null) {
            this.noteMoyenne = 0.0;
        }
        if (this.nombreAvis == null) {
            this.nombreAvis = 0;
        }
        if (this.nombreVentes == null) {
            this.nombreVentes = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModification = LocalDateTime.now();
    }

    // Constructors
    public Pack() {
        this.contenus = new ArrayList<>();
    }

    public Pack(String nom, Double prix, Boutique boutique) {
        this();
        this.nom = nom;
        this.prix = prix;
        this.boutique = boutique;
    }

    // Getters & Setters
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

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getRecetteId() {
        return recetteId;
    }

    public void setRecetteId(Long recetteId) {
        this.recetteId = recetteId;
    }

    public Double getNoteMoyenne() {
        return noteMoyenne;
    }

    public void setNoteMoyenne(Double noteMoyenne) {
        this.noteMoyenne = noteMoyenne;
    }

    public Integer getNombreAvis() {
        return nombreAvis;
    }

    public void setNombreAvis(Integer nombreAvis) {
        this.nombreAvis = nombreAvis;
    }

    public Integer getNombreVentes() {
        return nombreVentes;
    }

    public void setNombreVentes(Integer nombreVentes) {
        this.nombreVentes = nombreVentes;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getTypePack() {
        return typePack;
    }

    public void setTypePack(String typePack) {
        this.typePack = typePack;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public List<ContenuPack> getContenus() {
        return contenus;
    }

    public void setContenus(List<ContenuPack> contenus) {
        this.contenus = contenus;
    }

    // Business methods
    public void ajouterContenu(ContenuPack contenu) {
        this.contenus.add(contenu);
        contenu.setPack(this);
    }

    public void supprimerContenu(ContenuPack contenu) {
        this.contenus.remove(contenu);
        contenu.setPack(null);
    }

    public boolean estDisponible() {
        return this.disponible && this.stock > 0;
    }

    public void incrementerVentes() {
        this.nombreVentes = (this.nombreVentes == null ? 0 : this.nombreVentes) + 1;
    }

    public void decrementerStock(int quantite) {
        if (this.stock >= quantite) {
            this.stock -= quantite;
            if (this.stock == 0) {
                this.disponible = false;
            }
        } else {
            throw new IllegalStateException("Stock insuffisant pour le pack: " + this.nom);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pack)) return false;
        Pack pack = (Pack) o;
        return id != null && id.equals(pack.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Pack{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", stock=" + stock +
                ", disponible=" + disponible +
                '}';
    }
}
