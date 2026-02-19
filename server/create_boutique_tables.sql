-- =====================================================
-- Script de création des tables Boutique/Pack/ContenuPack
-- Pour la fonctionnalité de vente de packs d'ingrédients
-- =====================================================

-- Table BOUTIQUES
CREATE TABLE IF NOT EXISTS boutiques (
    id BIGSERIAL PRIMARY KEY,
    fournisseur_id BIGINT UNIQUE,  -- Nullable pour boutiques de test
    nom VARCHAR(200) NOT NULL,
    description TEXT,
    type VARCHAR(100),  -- SPECIALISEE, GENERALISTE
    categorie VARCHAR(100),  -- VIANDES, LEGUMES, CEREALES, CONSERVES, FRUITS
    adresse VARCHAR(500),
    telephone VARCHAR(20),
    email VARCHAR(150),
    logo_url VARCHAR(500),
    horaires_ouverture VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    note_moyenne DOUBLE PRECISION DEFAULT 0.0,
    nombre_avis INTEGER DEFAULT 0,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP,
    
    CONSTRAINT fk_boutique_fournisseur FOREIGN KEY (fournisseur_id) 
        REFERENCES utilisateurs(id) ON DELETE CASCADE
);

-- Index pour optimiser les requêtes
CREATE INDEX IF NOT EXISTS idx_boutique_fournisseur ON boutiques(fournisseur_id);
CREATE INDEX IF NOT EXISTS idx_boutique_active ON boutiques(active);
CREATE INDEX IF NOT EXISTS idx_boutique_categorie ON boutiques(categorie);
CREATE INDEX IF NOT EXISTS idx_boutique_type ON boutiques(type);

-- Table PACKS
CREATE TABLE IF NOT EXISTS packs (
    id BIGSERIAL PRIMARY KEY,
    boutique_id BIGINT NOT NULL,
    nom VARCHAR(200) NOT NULL,
    description TEXT,
    prix DOUBLE PRECISION NOT NULL CHECK (prix > 0),
    stock INTEGER NOT NULL DEFAULT 0,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    categorie VARCHAR(100),  -- VIANDES, LEGUMES, CEREALES, CONSERVES, FRUITS
    budget VARCHAR(50),  -- 500F, 1000F, 2000F, 3000F, 5000F, 10000F
    type_pack VARCHAR(100),  -- STANDARD, COMPLET_RECETTE, SEMAINE, COLOCATION
    image_url VARCHAR(500),
    recette_id BIGINT,
    note_moyenne DOUBLE PRECISION DEFAULT 0.0,
    nombre_avis INTEGER DEFAULT 0,
    nombre_ventes INTEGER DEFAULT 0,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP,
    
    CONSTRAINT fk_pack_boutique FOREIGN KEY (boutique_id) 
        REFERENCES boutiques(id) ON DELETE CASCADE,
    CONSTRAINT fk_pack_recette FOREIGN KEY (recette_id) 
        REFERENCES recettes(id) ON DELETE SET NULL
);

-- Index pour optimiser les requêtes
CREATE INDEX IF NOT EXISTS idx_pack_boutique ON packs(boutique_id);
CREATE INDEX IF NOT EXISTS idx_pack_disponible ON packs(disponible);
CREATE INDEX IF NOT EXISTS idx_pack_recette ON packs(recette_id);
CREATE INDEX IF NOT EXISTS idx_pack_categorie ON packs(categorie);
CREATE INDEX IF NOT EXISTS idx_pack_budget ON packs(budget);
CREATE INDEX IF NOT EXISTS idx_pack_type ON packs(type_pack);

-- Table CONTENU_PACKS
CREATE TABLE IF NOT EXISTS contenu_packs (
    id BIGSERIAL PRIMARY KEY,
    pack_id BIGINT NOT NULL,
    ingredient_id BIGINT NOT NULL,
    quantite DOUBLE PRECISION NOT NULL CHECK (quantite > 0),
    unite VARCHAR(20) NOT NULL DEFAULT 'g',
    ordre INTEGER,
    
    CONSTRAINT fk_contenu_pack FOREIGN KEY (pack_id) 
        REFERENCES packs(id) ON DELETE CASCADE,
    CONSTRAINT fk_contenu_ingredient FOREIGN KEY (ingredient_id) 
        REFERENCES ingredients(id) ON DELETE CASCADE,
    CONSTRAINT uk_pack_ingredient UNIQUE (pack_id, ingredient_id)
);

-- Index pour optimiser les requêtes
CREATE INDEX IF NOT EXISTS idx_contenu_pack ON contenu_packs(pack_id);
CREATE INDEX IF NOT EXISTS idx_contenu_ingredient ON contenu_packs(ingredient_id);

-- =====================================================
-- Données de test
-- =====================================================

-- Création de boutiques de test (à adapter selon vos utilisateurs fournisseurs)
-- Exemple: si vous avez un utilisateur fournisseur avec id=3
-- INSERT INTO boutiques (fournisseur_id, nom, description, adresse, telephone, email, logo_url, horaires_ouverture) 
-- VALUES 
-- (3, 'Marché Bio Campus', 'Vos ingrédients frais livrés directement sur le campus', 
--  'Campus Universitaire, Lomé', '+228 90 12 34 56', 'contact@marchebio.tg', 
--  'logo_marche_bio.png', 'Lun-Ven: 8h-18h, Sam: 9h-14h');

-- Exemple de pack d'ingrédients
-- INSERT INTO packs (boutique_id, nom, description, prix, stock, recette_id, image_url)
-- VALUES 
-- (1, 'Pack Gombo Sauce', 'Tous les ingrédients pour préparer du Gombo', 
--  7500, 20, 4, 2, 'pack_gombo.png');

-- Exemple de contenu de pack
-- INSERT INTO contenu_packs (pack_id, ingredient_id, quantite, unite, ordre)
-- VALUES 
-- (1, 1, 500, 'g', 1),  -- Gombo
-- (1, 2, 300, 'g', 2),  -- Tomates
-- (1, 3, 150, 'g', 3),  -- Oignons
-- (1, 4, 2, 'cubes', 4); -- Cubes Maggi

COMMENT ON TABLE boutiques IS 'Table des boutiques des fournisseurs';
COMMENT ON TABLE packs IS 'Table des packs d''ingrédients vendus par les boutiques';
COMMENT ON TABLE contenu_packs IS 'Table de liaison entre packs et ingrédients avec quantités';

COMMENT ON COLUMN boutiques.fournisseur_id IS 'Référence à un utilisateur avec le rôle FOURNISSEUR (nullable pour données de test)';
COMMENT ON COLUMN boutiques.type IS 'Type: SPECIALISEE (produits spécifiques) ou GENERALISTE (multi-produits)';
COMMENT ON COLUMN boutiques.categorie IS 'Catégorie principale: VIANDES, LEGUMES, CEREALES, CONSERVES, FRUITS';
COMMENT ON COLUMN packs.categorie IS 'Catégorie du pack: VIANDES, LEGUMES, CEREALES, CONSERVES, FRUITS';
COMMENT ON COLUMN packs.budget IS 'Budget du pack: 500F, 1000F, 2000F, 3000F, 5000F, 10000F';
COMMENT ON COLUMN packs.type_pack IS 'Type: STANDARD, COMPLET_RECETTE, SEMAINE, COLOCATION';
COMMENT ON COLUMN packs.recette_id IS 'Recette associée (optionnel) pour montrer ce qu''on peut cuisiner';
COMMENT ON COLUMN contenu_packs.ordre IS 'Ordre d''affichage des ingrédients dans la liste';
