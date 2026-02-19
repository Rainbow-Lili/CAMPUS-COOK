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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * Boutique entity. Represents a shop owned by a supplier (fournisseur).
 * Each supplier can have one boutique to sell ingredient packs.
 *
 * @author DAKEY Ahoefa Light
 */
@Entity
@Table(name = "boutiques", indexes = {
    @Index(name = "idx_boutique_fournisseur", columnList = "fournisseur_id"),
    @Index(name = "idx_boutique_active", columnList = "active")
})
public class Boutique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "Le nom de la boutique est obligatoire")
    @Column(name = "nom", nullable = false, length = 200)
    private String nom;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "adresse", length = 500)
    private String adresse;

    @Column(name = "telephone", length = 20)
    private String telephone;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(name = "horaires_ouverture", length = 500)
    private String horairesOuverture;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "note_moyenne")
    private Double noteMoyenne = 0.0;

    @Column(name = "nombre_avis")
    private Integer nombreAvis = 0;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @Column(name = "categorie", length = 100)
    private String categorie; // VIANDES, LEGUMES, CEREALES, CONSERVES, FRUITS, etc.

    @Column(name = "type", length = 100)
    private String type; // PACK_STANDARD, BOUTIQUE_SPECIALISEE

    // Relations
    @JsonbTransient
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fournisseur_id", nullable = true, unique = true)
    private Utilisateur fournisseur;

    @JsonbTransient
    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Pack> packs = new ArrayList<>();

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
        if (this.active == null) {
            this.active = true;
        }
        if (this.noteMoyenne == null) {
            this.noteMoyenne = 0.0;
        }
        if (this.nombreAvis == null) {
            this.nombreAvis = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModification = LocalDateTime.now();
    }

    // Constructors
    public Boutique() {
        this.packs = new ArrayList<>();
    }

    public Boutique(String nom, Utilisateur fournisseur) {
        this();
        this.nom = nom;
        this.fournisseur = fournisseur;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getHorairesOuverture() {
        return horairesOuverture;
    }

    public void setHorairesOuverture(String horairesOuverture) {
        this.horairesOuverture = horairesOuverture;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Utilisateur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Utilisateur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public List<Pack> getPacks() {
        return packs;
    }

    public void setPacks(List<Pack> packs) {
        this.packs = packs;
    }

    // Business methods
    public void ajouterPack(Pack pack) {
        this.packs.add(pack);
        pack.setBoutique(this);
    }

    public void supprimerPack(Pack pack) {
        this.packs.remove(pack);
        pack.setBoutique(null);
    }

    public int getNombrePacksActifs() {
        return (int) this.packs.stream()
                .filter(pack -> pack.getDisponible() && pack.getStock() > 0)
                .count();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Boutique)) return false;
        Boutique boutique = (Boutique) o;
        return id != null && id.equals(boutique.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Boutique{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", active=" + active +
                ", nombrePacks=" + (packs != null ? packs.size() : 0) +
                '}';
    }
}
