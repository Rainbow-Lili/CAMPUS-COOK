-- =====================================================
-- Script de migration pour ajouter les colonnes manquantes
-- aux tables boutiques et packs existantes
-- =====================================================

-- Ajouter colonnes manquantes à BOUTIQUES
ALTER TABLE boutiques 
    ADD COLUMN IF NOT EXISTS type VARCHAR(100),
    ADD COLUMN IF NOT EXISTS categorie VARCHAR(100);

-- Créer les index pour les nouvelles colonnes
CREATE INDEX IF NOT EXISTS idx_boutique_categorie ON boutiques(categorie);
CREATE INDEX IF NOT EXISTS idx_boutique_type ON boutiques(type);

-- Ajouter colonnes manquantes à PACKS
ALTER TABLE packs 
    ADD COLUMN IF NOT EXISTS categorie VARCHAR(100),
    ADD COLUMN IF NOT EXISTS budget VARCHAR(50),
    ADD COLUMN IF NOT EXISTS type_pack VARCHAR(100);

-- Créer les index pour les nouvelles colonnes
CREATE INDEX IF NOT EXISTS idx_pack_categorie ON packs(categorie);
CREATE INDEX IF NOT EXISTS idx_pack_budget ON packs(budget);
CREATE INDEX IF NOT EXISTS idx_pack_type ON packs(type_pack);

-- Rendre fournisseur_id nullable (si nécessaire pour données de test)
ALTER TABLE boutiques ALTER COLUMN fournisseur_id DROP NOT NULL;

-- Vérification
SELECT 'Migration terminée ✅' as status;
SELECT 'Colonnes boutiques:' as info;
SELECT column_name, data_type, is_nullable 
FROM information_schema.columns 
WHERE table_name = 'boutiques' 
ORDER BY ordinal_position;

SELECT 'Colonnes packs:' as info;
SELECT column_name, data_type, is_nullable 
FROM information_schema.columns 
WHERE table_name = 'packs' 
ORDER BY ordinal_position;
