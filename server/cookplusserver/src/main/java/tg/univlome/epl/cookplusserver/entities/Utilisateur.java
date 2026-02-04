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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "utilisateurs",
        indexes = {
            @Index(name = "idx_utilisateur_email", columnList = "email"),
            @Index(name = "idx_utilisateur_role", columnList = "role")
        })
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    // Identifiant
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Size(max = 100, message = "Le prénom ne doit pas dépasser 100 caractères")
    @Column(name = "prenom", length = 100)
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Le format de l'email est invalide")
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Column(name = "mot_de_passe_hash", nullable = false, length = 255)
    private String motDePasseHash;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "derniere_connexion")
    private LocalDateTime derniereConnexion;

    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @Column(name = "photo_profil", length = 500)
    private String photoProfil;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "actif", nullable = false)
    private Boolean actif;

    // Relations
    @OneToMany(mappedBy = "utilisateur",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Recette> recettesCreees;

    @OneToMany(mappedBy = "utilisateur",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Favori> favoris;

    @OneToMany(mappedBy = "utilisateur",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Commentaire> commentaires;

    @OneToMany(mappedBy = "utilisateur",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Note> notes;

    @OneToMany(mappedBy = "utilisateur",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Notification> notifications;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
        this.role = Objects.requireNonNullElse(this.role, "etudiant");
        this.actif = Objects.requireNonNullElse(this.actif, Boolean.TRUE);
        this.recettesCreees = Objects.requireNonNullElse(this.recettesCreees, new ArrayList<>());
        this.favoris = Objects.requireNonNullElse(this.favoris, new ArrayList<>());
        this.commentaires = Objects.requireNonNullElse(this.commentaires, new ArrayList<>());
        this.notes = Objects.requireNonNullElse(this.notes, new ArrayList<>());
        this.notifications = Objects.requireNonNullElse(this.notifications, new ArrayList<>());
    }

    /**
     * Callback exécuté automatiquement avant la mise à jour de l'entité. Met à
     * jour la date de dernière connexion.
     */
    @PreUpdate
    protected void onUpdate() {
        this.derniereConnexion = LocalDateTime.now();
    }

    public Utilisateur() {
        this.recettesCreees = new ArrayList<>();
        this.favoris = new ArrayList<>();
        this.commentaires = new ArrayList<>();
        this.notes = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.role = "etudiant";
        this.actif = Boolean.TRUE;
    }

    /**
     * Constructeur avec paramètres minimaux requis.
     *
     * @param nom Le nom de l'utilisateur
     * @param email L'adresse email de l'utilisateur
     * @param motDePasseHash Le mot de passe hashé
     */
    public Utilisateur(String nom, String email, String motDePasseHash) {
        this();
        this.nom = nom;
        this.prenom = null;
        this.email = email;
        this.motDePasseHash = motDePasseHash;
    }

    /**
     * Constructeur complet.
     *
     * @param nom Le nom de l'utilisateur
     * @param email L'adresse email de l'utilisateur
     * @param motDePasseHash Le mot de passe hashé
     * @param role Le rôle de l'utilisateur
     */
    public Utilisateur(String nom, String email, String motDePasseHash, String role) {
        this(nom, email, motDePasseHash);
        this.role = role;
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

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasseHash() {
        return this.motDePasseHash;
    }

    public void setMotDePasseHash(String motDePasseHash) {
        this.motDePasseHash = motDePasseHash;
    }

    public LocalDateTime getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDerniereConnexion() {
        return this.derniereConnexion;
    }

    public void setDerniereConnexion(LocalDateTime derniereConnexion) {
        this.derniereConnexion = derniereConnexion;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhotoProfil() {
        return this.photoProfil;
    }

    public void setPhotoProfil(String photoProfil) {
        this.photoProfil = photoProfil;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Boolean getActif() {
        return this.actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public List<Recette> getRecettesCreees() {
        return this.recettesCreees;
    }

    public void setRecettesCreees(List<Recette> recettesCreees) {
        this.recettesCreees = recettesCreees;
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

    public List<Notification> getNotifications() {
        return this.notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public boolean estAdmin() {
        return "admin".equalsIgnoreCase(this.role);
    }

    public boolean estEtudiant() {
        return "etudiant".equalsIgnoreCase(this.role);
    }

    public boolean estActif() {
        return this.actif != null && this.actif.booleanValue();
    }

    public void activer() {
        this.actif = Boolean.TRUE;
    }

    public void desactiver() {
        this.actif = Boolean.FALSE;
    }

    public void mettreAJourDerniereConnexion() {
        this.derniereConnexion = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Utilisateur)) {
            return this == o;
        }
        Utilisateur other = (Utilisateur) o;
        return Objects.nonNull(this.id)
                && Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return "Utilisateur{"
                + "id=" + this.id
                + ", nom='" + this.nom + '\''
                + ", email='" + this.email + '\''
                + ", role='" + this.role + '\''
                + ", actif=" + this.actif
                + ", dateCreation=" + this.dateCreation
                + ", derniereConnexion=" + this.derniereConnexion
                + '}';
    }
}
