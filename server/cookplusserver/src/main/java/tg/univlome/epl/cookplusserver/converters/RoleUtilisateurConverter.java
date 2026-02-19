package tg.univlome.epl.cookplusserver.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tg.univlome.epl.cookplusserver.enums.RoleUtilisateur;

/**
 * Convertisseur JPA pour mapper l'énumération RoleUtilisateur en String dans la base de données.
 * Pattern: AttributeConverter (JPA 2.1+)
 * 
 * @author DAKEY Ahoefa Light
 */
@Converter(autoApply = true)
public class RoleUtilisateurConverter implements AttributeConverter<RoleUtilisateur, String> {
    
    /**
     * Convertit l'énumération en String pour la base de données
     * @param attribute L'énumération RoleUtilisateur
     * @return Le code String correspondant
     */
    @Override
    public String convertToDatabaseColumn(RoleUtilisateur attribute) {
        if (attribute == null) {
            return RoleUtilisateur.ETUDIANT.getCode();
        }
        return attribute.getCode();
    }
    
    /**
     * Convertit le String de la base de données en énumération
     * @param dbData Le code String depuis la base
     * @return L'énumération RoleUtilisateur correspondante
     */
    @Override
    public RoleUtilisateur convertToEntityAttribute(String dbData) {
        return RoleUtilisateur.fromCode(dbData);
    }
}
