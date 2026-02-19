-- =====================================================
-- Script de mise à jour des chemins d'images marketplace
-- Correspondance avec les fichiers réellement uploadés
-- =====================================================

-- =====================================================
-- MISE À JOUR DES IMAGES DES PACKS
-- =====================================================

-- CATÉGORIE VIANDES (poisson_*.png)
UPDATE packs SET image_url = 'packs/viandes/poisson_500.png' 
WHERE categorie = 'VIANDES' AND (budget = '500' OR budget = '500F');

UPDATE packs SET image_url = 'packs/viandes/poisson_1000.png' 
WHERE categorie = 'VIANDES' AND (budget = '1000' OR budget = '1000F');

UPDATE packs SET image_url = 'packs/viandes/poisson_2000.png' 
WHERE categorie = 'VIANDES' AND (budget = '2000' OR budget = '2000F');

UPDATE packs SET image_url = 'packs/viandes/poisson_3000.png' 
WHERE categorie = 'VIANDES' AND (budget = '3000' OR budget = '3000F');

UPDATE packs SET image_url = 'packs/viandes/poisson_5000.png' 
WHERE categorie = 'VIANDES' AND (budget = '5000' OR budget = '5000F');

UPDATE packs SET image_url = 'packs/viandes/poisson_10000.png' 
WHERE categorie = 'VIANDES' AND (budget = '10000' OR budget = '10000F');

-- CATÉGORIE LEGUMES (legumes_*.png)
UPDATE packs SET image_url = 'packs/legumes/legumes_500.png' 
WHERE categorie = 'LEGUMES' AND (budget = '500' OR budget = '500F');

UPDATE packs SET image_url = 'packs/legumes/legumes_1000.png' 
WHERE categorie = 'LEGUMES' AND (budget = '1000' OR budget = '1000F');

UPDATE packs SET image_url = 'packs/legumes/legumes_2000.png' 
WHERE categorie = 'LEGUMES' AND (budget = '2000' OR budget = '2000F');

UPDATE packs SET image_url = 'packs/legumes/legumes_3000.png' 
WHERE categorie = 'LEGUMES' AND (budget = '3000' OR budget = '3000F');

UPDATE packs SET image_url = 'packs/legumes/legumes_5000.png' 
WHERE categorie = 'LEGUMES' AND (budget = '5000' OR budget = '5000F');

UPDATE packs SET image_url = 'packs/legumes/legumes_10000.png' 
WHERE categorie = 'LEGUMES' AND (budget = '10000' OR budget = '10000F');

-- CATÉGORIE CEREALES (feculents_*.jpeg/png)
UPDATE packs SET image_url = 'packs/cereales/feculents_500.jpeg' 
WHERE categorie = 'CEREALES' AND (budget = '500' OR budget = '500F');

UPDATE packs SET image_url = 'packs/cereales/feculents_1000.jpeg' 
WHERE categorie = 'CEREALES' AND (budget = '1000' OR budget = '1000F');

UPDATE packs SET image_url = 'packs/cereales/feculents_2000.jpeg' 
WHERE categorie = 'CEREALES' AND (budget = '2000' OR budget = '2000F');

UPDATE packs SET image_url = 'packs/cereales/feculents_3000.png' 
WHERE categorie = 'CEREALES' AND (budget = '3000' OR budget = '3000F');

UPDATE packs SET image_url = 'packs/cereales/feculents_5000.jpeg' 
WHERE categorie = 'CEREALES' AND (budget = '5000' OR budget = '5000F');

UPDATE packs SET image_url = 'packs/cereales/feculents_10000.jpeg' 
WHERE categorie = 'CEREALES' AND (budget = '10000' OR budget = '10000F');

-- CATÉGORIE CONSERVES (conserves_*.jpeg/png)
UPDATE packs SET image_url = 'packs/conserves/conserves_500.jpeg' 
WHERE categorie = 'CONSERVES' AND (budget = '500' OR budget = '500F');

UPDATE packs SET image_url = 'packs/conserves/conserves_1000.jpeg' 
WHERE categorie = 'CONSERVES' AND (budget = '1000' OR budget = '1000F');

UPDATE packs SET image_url = 'packs/conserves/conserves_2000.jpeg' 
WHERE categorie = 'CONSERVES' AND (budget = '2000' OR budget = '2000F');

UPDATE packs SET image_url = 'packs/conserves/conserves_3000.jpeg' 
WHERE categorie = 'CONSERVES' AND (budget = '3000' OR budget = '3000F');

UPDATE packs SET image_url = 'packs/conserves/conserves_5000.png' 
WHERE categorie = 'CONSERVES' AND (budget = '5000' OR budget = '5000F');

UPDATE packs SET image_url = 'packs/conserves/conserves_10000.png' 
WHERE categorie = 'CONSERVES' AND (budget = '10000' OR budget = '10000F');

-- CATÉGORIE FRUITS (fruits_*.png)
UPDATE packs SET image_url = 'packs/fruits/fruits_500.png' 
WHERE categorie = 'FRUITS' AND (budget = '500' OR budget = '500F');

UPDATE packs SET image_url = 'packs/fruits/fruits_1000.png' 
WHERE categorie = 'FRUITS' AND (budget = '1000' OR budget = '1000F');

UPDATE packs SET image_url = 'packs/fruits/fruits_2000.png' 
WHERE categorie = 'FRUITS' AND (budget = '2000' OR budget = '2000F');

UPDATE packs SET image_url = 'packs/fruits/fruits_3000.png' 
WHERE categorie = 'FRUITS' AND (budget = '3000' OR budget = '3000F');

UPDATE packs SET image_url = 'packs/fruits/fruits_5000.png' 
WHERE categorie = 'FRUITS' AND (budget = '5000' OR budget = '5000F');

UPDATE packs SET image_url = 'packs/fruits/fruits_10000.png' 
WHERE categorie = 'FRUITS' AND (budget = '10000' OR budget = '10000F');

-- =====================================================
-- MISE À JOUR DES LOGOS DES BOUTIQUES
-- =====================================================

-- Boutique 1: Boucherie Moderne Lomé
UPDATE boutiques SET logo_url = 'packs/boutiques/icone1.png' 
WHERE nom = 'Boucherie Moderne Lomé';

-- Boutique 2: Boucherie du Campus
UPDATE boutiques SET logo_url = 'packs/boutiques/icone2.png' 
WHERE nom = 'Boucherie du Campus';

-- Boutique 3: Poissonnerie Atlantique
UPDATE boutiques SET logo_url = 'packs/boutiques/icone3.png' 
WHERE nom = 'Poissonnerie Atlantique';

-- Boutique 4: Marché Bio Tokoin
UPDATE boutiques SET logo_url = 'packs/boutiques/icone4.png' 
WHERE nom = 'Marché Bio Tokoin';

-- Boutique 5: Grenier d'Afrique
UPDATE boutiques SET logo_url = 'packs/boutiques/icone5.png' 
WHERE nom = 'Grenier d''Afrique';

-- Boutique 6: Fraîcheur Express
UPDATE boutiques SET logo_url = 'packs/boutiques/icone6.png' 
WHERE nom = 'Fraîcheur Express';

-- Boutique 7: Épices du Monde
UPDATE boutiques SET logo_url = 'packs/boutiques/icone7.png' 
WHERE nom = 'Épices du Monde';

-- Boutique 8: Magasin Bio Campus
UPDATE boutiques SET logo_url = 'packs/boutiques/icone8.png' 
WHERE nom = 'Magasin Bio Campus';

-- =====================================================
-- VÉRIFICATION
-- =====================================================
SELECT 'Images des packs mises à jour ✅' as status;

-- Vérifier les packs par catégorie
SELECT categorie, budget, image_url 
FROM packs 
WHERE categorie = 'VIANDES' 
ORDER BY CAST(REPLACE(budget, 'F', '') AS INTEGER);

SELECT categorie, budget, image_url 
FROM packs 
WHERE categorie = 'LEGUMES' 
ORDER BY CAST(REPLACE(budget, 'F', '') AS INTEGER);

-- Vérifier les boutiques
SELECT 'Logos des boutiques mis à jour ✅' as status;
SELECT id, nom, logo_url FROM boutiques ORDER BY id;

-- Compter les mises à jour
SELECT 
    COUNT(*) as total_packs,
    COUNT(CASE WHEN image_url LIKE 'packs/%' THEN 1 END) as packs_with_images
FROM packs;

SELECT 
    COUNT(*) as total_boutiques,
    COUNT(CASE WHEN logo_url LIKE 'packs/%' THEN 1 END) as boutiques_with_logos
FROM boutiques;
