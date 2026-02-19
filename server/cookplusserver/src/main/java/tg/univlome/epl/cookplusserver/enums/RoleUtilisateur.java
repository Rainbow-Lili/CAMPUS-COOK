package tg.univlome.epl.cookplusserver.enums;

/**
 * Énumération des rôles utilisateurs dans le système CookCamplus.
 * Utilise le pattern Enum pour garantir la type-safety et éviter les erreurs de chaînes.
 * 
 * @author DAKEY Ahoefa Light
 */
public enum RoleUtilisateur {
    
    /**
     * Étudiant - Utilisateur standard qui consulte et crée des recettes
     */
    ETUDIANT("etudiant", "Étudiant"),
    
    /**
     * Fournisseur - Gère les stocks d'ingrédients et les packs
     */
    FOURNISSEUR("fournisseur", "Fournisseur"),
    
    /**
     * Administrateur - Gestion complète du système
     */
    ADMIN("admin", "Administrateur");
    
    private final String code;
    private final String libelle;
    
    /**
     * Constructeur privé de l'énumération
     * @param code Code technique du rôle (utilisé en base de données)
     * @param libelle Libellé affiché à l'utilisateur
     */
    RoleUtilisateur(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }
    
    /**
     * Obtient le code technique du rôle
     * @return Le code (ex: "etudiant", "admin")
     */
    public String getCode() {
        return code;
    }
    
    /**
     * Obtient le libellé lisible du rôle
     * @return Le libellé (ex: "Étudiant", "Administrateur")
     */
    public String getLibelle() {
        return libelle;
    }
    
    /**
     * Convertit un code String en énumération RoleUtilisateur
     * @param code Le code à convertir
     * @return L'énumération correspondante, ou ETUDIANT par défaut
     */
    public static RoleUtilisateur fromCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return ETUDIANT;
        }
        
        for (RoleUtilisateur role : values()) {
            if (role.code.equalsIgnoreCase(code.trim())) {
                return role;
            }
        }
        
        return ETUDIANT; // Rôle par défaut
    }
    
    /**
     * Vérifie si le rôle est administrateur
     * @return true si c'est un admin
     */
    public boolean isAdmin() {
        return this == ADMIN;
    }
    
    /**
     * Vérifie si le rôle est fournisseur
     * @return true si c'est un fournisseur
     */
    public boolean isFournisseur() {
        return this == FOURNISSEUR;
    }
    
    /**
     * Vérifie si le rôle est étudiant
     * @return true si c'est un étudiant
     */
    public boolean isEtudiant() {
        return this == ETUDIANT;
    }
    
    @Override
    public String toString() {
        return libelle;
    }
}
