-- Script SQL pour initialiser la base de données Cook'Campus
-- 19 Recettes Africaines Complètes (Cuisine Togolaise & Ivoirienne)
-- Toutes les quantités sont pour 1 personne
-- Généré à partir de notes.md

-- =======================
-- 1. NETTOYAGE DES TABLES (PostgreSQL)
-- =======================
TRUNCATE TABLE etapes, recette_ingredients, videos, medias, notes, commentaires, 
               favoris, notifications, recettes, ingredients, origines, utilisateurs 
RESTART IDENTITY CASCADE;

-- =======================
-- 2. UTILISATEURS
-- =======================
-- Mot de passe: "Password123!" hashé en SHA-256
INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe_hash, role, actif, date_creation) VALUES
('KOUASSI', 'Amani', 'amani.kouassi@etudiant.univlome.tg', 
 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 
 'utilisateur', true, CURRENT_TIMESTAMP);

-- =======================
-- 3. ORIGINES
-- =======================
INSERT INTO origines (nom, description, date_creation) VALUES
('Togo', 'Cuisine traditionnelle togolaise', CURRENT_TIMESTAMP),
('Côte d''Ivoire', 'Cuisine traditionnelle ivoirienne', CURRENT_TIMESTAMP),
('Afrique de l''Ouest', 'Cuisine partagée de l''Afrique de l''Ouest', CURRENT_TIMESTAMP);

-- =======================
-- 4. INGRÉDIENTS (avec prix en FCFA par unité)
-- =======================
INSERT INTO ingredients (nom, description, unite_mesure, prix_unitaire, disponible, date_creation) VALUES
-- Féculents et céréales
('Riz long grain', 'Riz blanc de qualité', 'kg', 500.00, true, CURRENT_TIMESTAMP),
('Attiéké', 'Semoule de manioc fermentée', 'kg', 600.00, true, CURRENT_TIMESTAMP),
('Gari', 'Semoule de manioc grillée', 'kg', 400.00, true, CURRENT_TIMESTAMP),
('Farine de riz', 'Farine de riz blanche', 'kg', 700.00, true, CURRENT_TIMESTAMP),
('Farine de maïs', 'Farine de maïs jaune', 'kg', 400.00, true, CURRENT_TIMESTAMP),
('Maïzena', 'Fécule de maïs', 'kg', 1200.00, true, CURRENT_TIMESTAMP),
('Couscous de mil', 'Thiakry pour dégué', 'kg', 800.00, true, CURRENT_TIMESTAMP),
('Pâtes coquillages', 'Pâtes alimentaires', 'kg', 600.00, true, CURRENT_TIMESTAMP),
('Haricots rouges', 'Haricots secs', 'kg', 500.00, true, CURRENT_TIMESTAMP),

-- Légumes
('Feuilles d''adémé', 'Feuilles de corète', 'kg', 300.00, true, CURRENT_TIMESTAMP),
('Épinards', 'Épinards frais', 'kg', 400.00, true, CURRENT_TIMESTAMP),
('Gombo', 'Gombo frais', 'kg', 600.00, true, CURRENT_TIMESTAMP),
('Aubergine africaine', 'Aubergine ronde africaine', 'kg', 400.00, true, CURRENT_TIMESTAMP),
('Tomate', 'Tomates fraîches', 'kg', 400.00, true, CURRENT_TIMESTAMP),
('Concentré de tomate', 'Concentré de tomate en boîte', 'kg', 1500.00, true, CURRENT_TIMESTAMP),
('Oignon', 'Oignons frais', 'kg', 300.00, true, CURRENT_TIMESTAMP),
('Oignon rouge', 'Oignon rouge', 'kg', 350.00, true, CURRENT_TIMESTAMP),
('Ail', 'Gousses d''ail', 'kg', 800.00, true, CURRENT_TIMESTAMP),
('Gingembre', 'Racine de gingembre', 'kg', 1000.00, true, CURRENT_TIMESTAMP),
('Piment vert', 'Piment frais vert', 'kg', 500.00, true, CURRENT_TIMESTAMP),
('Piment rouge', 'Piment frais rouge', 'kg', 500.00, true, CURRENT_TIMESTAMP),
('Piment en poudre', 'Piment moulu', 'kg', 2000.00, true, CURRENT_TIMESTAMP),
('Poivron rouge', 'Poivron rouge frais', 'kg', 600.00, true, CURRENT_TIMESTAMP),
('Carotte', 'Carottes fraîches', 'kg', 300.00, true, CURRENT_TIMESTAMP),
('Petits pois', 'Petits pois frais ou surgelés', 'kg', 400.00, true, CURRENT_TIMESTAMP),
('Concombre', 'Concombre frais', 'kg', 300.00, true, CURRENT_TIMESTAMP),
('Betterave', 'Betterave cuite', 'kg', 400.00, true, CURRENT_TIMESTAMP),

-- Tubercules
('Igname', 'Igname fraîche', 'kg', 400.00, true, CURRENT_TIMESTAMP),
('Banane plantain', 'Banane plantain mûre', 'unité', 150.00, true, CURRENT_TIMESTAMP),

-- Protéines
('Viande de boeuf', 'Viande de boeuf fraîche', 'kg', 2500.00, true, CURRENT_TIMESTAMP),
('Viande de mouton', 'Viande de mouton fraîche', 'kg', 3000.00, true, CURRENT_TIMESTAMP),
('Tripes de boeuf', 'Gras double nettoyé', 'kg', 1500.00, true, CURRENT_TIMESTAMP),
('Poisson fumé', 'Poisson fumé (thon)', 'kg', 3000.00, true, CURRENT_TIMESTAMP),
('Poisson frais', 'Poisson entier frais', 'kg', 2000.00, true, CURRENT_TIMESTAMP),
('Crevettes', 'Crevettes décortiquées', 'kg', 4000.00, true, CURRENT_TIMESTAMP),
('Œufs', 'Œufs frais', 'unité', 75.00, true, CURRENT_TIMESTAMP),
('Poudre de crevettes', 'Crevettes séchées moulues', 'kg', 5000.00, true, CURRENT_TIMESTAMP),

-- Matières grasses
('Huile de palme', 'Huile de palme rouge', 'litre', 1500.00, true, CURRENT_TIMESTAMP),
('Huile végétale', 'Huile d''arachide ou tournesol', 'litre', 1200.00, true, CURRENT_TIMESTAMP),
('Beurre', 'Beurre doux', 'kg', 3000.00, true, CURRENT_TIMESTAMP),

-- Sauces et condiments
('Pâte d''arachide', 'Pâte d''arachide naturelle', 'kg', 800.00, true, CURRENT_TIMESTAMP),
('Cube Maggi', 'Cube bouillon', 'unité', 50.00, true, CURRENT_TIMESTAMP),
('Mayonnaise', 'Mayonnaise', 'kg', 2000.00, true, CURRENT_TIMESTAMP),
('Moutarde', 'Moutarde', 'kg', 1500.00, true, CURRENT_TIMESTAMP),

-- Produits laitiers
('Lait entier', 'Lait frais entier', 'litre', 800.00, true, CURRENT_TIMESTAMP),
('Lait en poudre', 'Lait en poudre entier', 'kg', 3000.00, true, CURRENT_TIMESTAMP),
('Yaourt nature', 'Yaourt nature bio', 'kg', 1500.00, true, CURRENT_TIMESTAMP),
('Fromage blanc', 'Fromage blanc', 'kg', 2000.00, true, CURRENT_TIMESTAMP),
('Lait concentré non sucré', 'Lait concentré', 'kg', 1800.00, true, CURRENT_TIMESTAMP),
('Lait concentré sucré', 'Lait concentré sucré', 'kg', 2000.00, true, CURRENT_TIMESTAMP),

-- Épices et aromates
('Sel', 'Sel de cuisine', 'kg', 100.00, true, CURRENT_TIMESTAMP),
('Poivre noir', 'Poivre noir moulu', 'kg', 2000.00, true, CURRENT_TIMESTAMP),
('Bicarbonate de soude', 'Bicarbonate alimentaire', 'kg', 500.00, true, CURRENT_TIMESTAMP),
('Kanwa', 'Potasse alimentaire', 'kg', 1000.00, true, CURRENT_TIMESTAMP),
('Feuille de laurier', 'Laurier séché', 'kg', 1500.00, true, CURRENT_TIMESTAMP),
('Thym', 'Thym séché', 'kg', 2000.00, true, CURRENT_TIMESTAMP),
('Curry', 'Poudre de curry', 'kg', 2500.00, true, CURRENT_TIMESTAMP),
('Muscade', 'Muscade moulue', 'kg', 3000.00, true, CURRENT_TIMESTAMP),
('Vanille', 'Extrait de vanille', 'litre', 5000.00, true, CURRENT_TIMESTAMP),
('Persil', 'Persil frais', 'kg', 500.00, true, CURRENT_TIMESTAMP),

-- Autres
('Citron', 'Citron vert', 'unité', 50.00, true, CURRENT_TIMESTAMP),
('Levure boulangère', 'Levure fraîche', 'kg', 2000.00, true, CURRENT_TIMESTAMP),
('Levure chimique', 'Poudre à lever', 'kg', 1500.00, true, CURRENT_TIMESTAMP),
('Sucre', 'Sucre blanc', 'kg', 500.00, true, CURRENT_TIMESTAMP),
('Raisins secs', 'Raisins secs', 'kg', 1500.00, true, CURRENT_TIMESTAMP),
('Arachides', 'Arachides grillées', 'kg', 700.00, true, CURRENT_TIMESTAMP);

-- =======================
-- 4.5 AJOUT COLONNE IMAGE_URL
-- =======================
ALTER TABLE recettes ADD COLUMN IF NOT EXISTS image_url VARCHAR(500);

-- =======================
-- 5. RECETTES (19 recettes complètes)
-- =======================
INSERT INTO recettes (nom, description, temps_preparation, difficulte, nombre_personnes_base, 
                      est_public, nombre_vues, image_url, origine_id, utilisateur_id, date_creation) VALUES
-- 1. ADÉMÉ
('Adémé (Sauce aux Feuilles de Corète)', 
 'Sauce onctueuse aux feuilles d''adémé ou épinards, poisson fumé et crevettes', 
 50, 'Moyen', 1, true, 0, 'ademe.jpg', 1, 1, CURRENT_TIMESTAMP),

-- 2. SAUCE GOMBO  
('Sauce Gombo (Fétri Déssi)', 
 'Sauce gluante traditionnelle à base de gombos frais, viande et poisson fumé', 
 60, 'Moyen', 1, true, 0, 'gombo.jpg', 1, 1, CURRENT_TIMESTAMP),

-- 3. AYIMOLOU
('Ayimolou (Riz aux Haricots)', 
 'Riz cuit avec des haricots rouges, un plat complet et nourrissant', 
 60, 'Facile', 1, true, 0, 'ayimolou.jpg', 1, 1, CURRENT_TIMESTAMP),

-- 4. SAUCE PISTACHE
('Sauce Pistache (Egoussi/Arachide)', 
 'Sauce onctueuse à base de pâte d''arachide, viande et légumes', 
 70, 'Moyen', 1, true, 0, 'sauce_arachide.jpg', 1, 1, CURRENT_TIMESTAMP),

-- 5. ALLOCO
('Alloco (Bananes Plantains Frites)', 
 'Bananes plantains bien mûres frites, croustillantes et dorées', 
 18, 'Facile', 1, true, 0, 'alloco.png', 2, 1, CURRENT_TIMESTAMP),

-- 6. ATTIÉKÉ POISSON
('Attiéké Poisson', 
 'Semoule de manioc accompagnée de poisson braisé et salade fraîche', 
 55, 'Facile', 1, true, 0, 'attieke.png', 2, 1, CURRENT_TIMESTAMP),

-- 7. ABLO
('Ablo (Gâteaux de Riz à la Vapeur)', 
 'Petits gâteaux moelleux de riz cuits à la vapeur', 
 95, 'Moyen', 1, true, 0, 'ablo.jpg', 1, 1, CURRENT_TIMESTAMP),

-- 8. KOLIKO
('Koliko (Igname Frite)', 
 'Igname coupée en bâtonnets et frite jusqu''à obtenir une texture croustillante', 
 22, 'Facile', 1, true, 0, 'koliko.jpg', 1, 1, CURRENT_TIMESTAMP),

-- 9. PIGNON
('Pignon (Gari avec Sauce Tomate)', 
 'Gari (semoule de manioc) accompagné d''une sauce tomate épicée et viande grillée', 
 40, 'Facile', 1, true, 0, 'pignon.jpg', 1, 1, CURRENT_TIMESTAMP),

-- 10. FOUFOU
('Foufou (Pâte d''Igname Pilée)', 
 'Pâte lisse et élastique d''igname pilée, accompagnement de choix', 
 40, 'Difficile', 1, true, 0, 'foufou.jpg', 1, 1, CURRENT_TIMESTAMP),

-- 11. ŒUFS FRITS
('Œufs Frits à l''Africaine', 
 'Œufs frits avec oignons, tomates et piments, façon africaine', 
 12, 'Facile', 1, true, 0, 'oeufs_frits.jpg', 3, 1, CURRENT_TIMESTAMP),

-- 12. SAUCE MOUTON/TRIPES
('Sauce Mouton/Tripes (Sauce Rouge aux Abats)', 
 'Sauce rouge aux tripes de bœuf et viande de mouton, mijotée longuement', 
 120, 'Difficile', 1, true, 0, 'sauce_tripes.jpg', 1, 1, CURRENT_TIMESTAMP),

-- 13. RIZ AU GRAS ROUGE
('Riz au Gras Rouge (Jollof Rice)', 
 'Riz mijoté dans une sauce tomate épicée, plat emblématique d''Afrique de l''Ouest', 
 55, 'Moyen', 1, true, 0, 'jollof.jpg', 3, 1, CURRENT_TIMESTAMP),

-- 14. RIZ AU GRAS BLANC
('Riz au Gras Blanc', 
 'Riz cuisiné avec légumes et bouillon, version blanche du riz au gras', 
 35, 'Facile', 1, true, 0, 'riz_blanc.jpg', 3, 1, CURRENT_TIMESTAMP),

-- 15. SALADE COMPOSÉE
('Salade Composée Africaine', 
 'Salade fraîche de carottes, tomates, betteraves sans laitue', 
 15, 'Facile', 1, true, 0, 'salade.jpg', 3, 1, CURRENT_TIMESTAMP),

-- 16. PÂTES COQUILLAGES
('Pâtes Coquillages (Salade de Pâtes)', 
 'Salade de pâtes froide avec mayonnaise et crudités', 
 22, 'Facile', 1, true, 0, 'pates_salade.jpg', 3, 1, CURRENT_TIMESTAMP),

-- 17. GBOMA DESSI
('Gboma Dessi (Sauce aux Épinards)', 
 'Sauce aux épinards ou feuilles gboma avec viande et légumes', 
 70, 'Moyen', 1, true, 0, 'gboma.jpg', 1, 1, CURRENT_TIMESTAMP),

-- 18. DÉGUÉ
('Dégué (Thiakry - Dessert au Millet)', 
 'Dessert crémeux à base de couscous de mil, yaourt et lait', 
 70, 'Facile', 1, true, 0, 'degue.jpg', 3, 1, CURRENT_TIMESTAMP),

-- 19. YAOURT / LAIT CAILLÉ
('Yaourt / Lait Caillé (Kossam)', 
 'Yaourt maison fermenté traditionnellement', 
 490, 'Moyen', 1, true, 0, 'kossam.jpg', 3, 1, CURRENT_TIMESTAMP);

-- =======================
-- 6. RECETTE_INGREDIENTS (Quantités pour 1 personne)
-- =======================

-- 1. ADÉMÉ (Recette ID: 1)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(1, 10, 0.125),  -- Feuilles d'adémé: 125g
(1, 33, 0.075),  -- Poisson fumé: 75g
(1, 35, 0.050),  -- Crevettes: 50g
(1, 18, 0.005),  -- Ail: 1 gousse (5g)
(1, 16, 0.025),  -- Oignon: ¼ (25g)
(1, 19, 0.003),  -- Gingembre: 1cm (3g)
(1, 38, 0.040),  -- Huile de palme: 40ml
(1, 20, 0.010),  -- Piment: ½ (10g)
(1, 42, 0.25),   -- Cube Maggi: ¼
(1, 53, 0.001);  -- Bicarbonate: 1 pincée

-- 2. SAUCE GOMBO (Recette ID: 2)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(2, 12, 0.125),  -- Gombos: 125g
(2, 30, 0.075),  -- Viande de boeuf: 75g
(2, 35, 0.050),  -- Crevettes: 50g
(2, 13, 0.050),  -- Aubergine africaine: ½ (50g)
(2, 18, 0.005),  -- Ail: 1 gousse
(2, 16, 0.025),  -- Oignon: ¼
(2, 19, 0.003),  -- Gingembre: ½cm
(2, 38, 0.025),  -- Huile de palme: 25ml
(2, 20, 0.010),  -- Piment vert
(2, 42, 0.50),   -- Cube bouillon: ½
(2, 54, 0.001);  -- Kanwa: 1 pincée

-- 3. AYIMOLOU (Recette ID: 3)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(3, 1, 0.075),   -- Riz: 75g
(3, 9, 0.050),   -- Haricots rouges: 50g
(3, 16, 0.050),  -- Oignon: ½
(3, 38, 0.025),  -- Huile de palme: 25ml (facultatif)
(3, 42, 0.50);   -- Cube bouillon: ½

-- 4. SAUCE PISTACHE (Recette ID: 4)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(4, 41, 0.075),  -- Pâte d'arachide: 75g
(4, 30, 0.125),  -- Viande/poisson: 125g
(4, 16, 0.050),  -- Oignon: ½
(4, 14, 0.100),  -- Tomate: 1
(4, 18, 0.005),  -- Ail: 1 gousse
(4, 19, 0.003),  -- Gingembre: ½cm
(4, 15, 0.010),  -- Concentré tomate: ½ c.à.s (10g)
(4, 38, 0.040),  -- Huile de palme: 40ml
(4, 21, 0.010),  -- Piment: ½
(4, 36, 0.002),  -- Poudre crevette: 1 pincée (2g)
(4, 42, 0.50);   -- Cube Maggi: ½

-- 5. ALLOCO (Recette ID: 5)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(5, 29, 1.00),   -- Banane plantain: 1 unité
(5, 39, 0.100),  -- Huile de friture: 100ml
(5, 52, 0.002),  -- Sel: pincée
(5, 22, 0.002);  -- Piment en poudre: facultatif

-- 6. ATTIÉKÉ POISSON (Recette ID: 6)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(6, 2, 0.125),   -- Attiéké: 125g
(6, 34, 0.060),  -- Poisson entier: ½ (60g)
(6, 18, 0.005),  -- Ail: 1 gousse
(6, 19, 0.003),  -- Gingembre: ½cm
(6, 60, 0.010),  -- Persil
(6, 61, 0.50),   -- Citron: ½
(6, 14, 0.050),  -- Tomate: ½
(6, 26, 0.050),  -- Concombre: ¼
(6, 17, 0.025),  -- Oignon rouge: ¼
(6, 39, 0.020);  -- Huile: pour marinade

-- 7. ABLO (Recette ID: 7)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(7, 4, 0.060),   -- Farine de riz: 60g
(7, 5, 0.015),   -- Farine de maïs: 15g
(7, 6, 0.015),   -- Maïzena: 15g
(7, 66, 0.015),  -- Sucre: 15g
(7, 51, 0.001),  -- Sel: pincée
(7, 62, 0.005),  -- Levure boulangère: 1 c.à.c (5g)
(7, 63, 0.002);  -- Levure chimique: pincée

-- 8. KOLIKO (Recette ID: 8)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(8, 28, 0.200),  -- Igname: 200g
(8, 39, 0.100),  -- Huile de friture
(8, 52, 0.003),  -- Sel
(8, 22, 0.002);  -- Piment: facultatif

-- 9. PIGNON (Recette ID: 9)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(9, 3, 0.100),   -- Gari: 100g
(9, 14, 0.100),  -- Tomate: 1
(9, 16, 0.025),  -- Oignon: ¼
(9, 18, 0.005),  -- Ail: 1 gousse
(9, 30, 0.050),  -- Viande grillée: 50g
(9, 15, 0.010),  -- Concentré tomate: 1 c.à.s
(9, 39, 0.020),  -- Huile: 20ml
(9, 21, 0.010);  -- Piment: ½

-- 10. FOUFOU (Recette ID: 10)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(10, 28, 0.200), -- Igname: 200g
(10, 52, 0.003); -- Sel

-- 11. ŒUFS FRITS (Recette ID: 11)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(11, 37, 2.00),  -- Œufs: 2 unités
(11, 16, 0.025), -- Oignon: ¼
(11, 14, 0.025), -- Tomate: ¼
(11, 20, 0.010), -- Piment vert: ½
(11, 39, 0.015); -- Huile: 1 c.à.s

-- 12. SAUCE MOUTON/TRIPES (Recette ID: 12)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(12, 32, 0.150), -- Tripes de boeuf: 150g
(12, 31, 0.050), -- Viande de mouton: 50g
(12, 16, 0.050), -- Oignon: ½
(12, 14, 0.100), -- Tomate: 1
(12, 18, 0.005), -- Ail: 1 gousse
(12, 15, 0.010), -- Concentré tomate: 1 c.à.s
(12, 38, 0.040), -- Huile de palme: 40ml
(12, 21, 0.010), -- Piment: 1
(12, 42, 0.50);  -- Cube bouillon: ½

-- 13. RIZ AU GRAS ROUGE / JOLLOF (Recette ID: 13)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(13, 1, 0.100),  -- Riz: 100g
(13, 14, 0.100), -- Tomate: 1
(13, 23, 0.050), -- Poivron rouge: ½
(13, 16, 0.050), -- Oignon: ½
(13, 18, 0.005), -- Ail: 1 gousse
(13, 19, 0.003), -- Gingembre: ½cm
(13, 15, 0.010), -- Concentré tomate: 1 c.à.s
(13, 39, 0.030), -- Huile: 2 c.à.s
(13, 55, 0.002), -- Laurier: ½ feuille
(13, 56, 0.003), -- Thym: ½ c.à.c
(13, 57, 0.003), -- Curry: ½ c.à.c
(13, 21, 0.010); -- Piment: ½

-- 14. RIZ AU GRAS BLANC (Recette ID: 14)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(14, 1, 0.100),  -- Riz: 100g
(14, 16, 0.050), -- Oignon: ½
(14, 18, 0.005), -- Ail: 1 gousse
(14, 24, 0.050), -- Carotte: ½
(14, 25, 0.050), -- Petits pois: 50g
(14, 39, 0.030), -- Huile: 2 c.à.s
(14, 42, 1.00),  -- Cube bouillon: 1
(14, 55, 0.002); -- Laurier: ½ feuille

-- 15. SALADE COMPOSÉE (Recette ID: 15)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(15, 24, 0.050), -- Carotte: ½
(15, 14, 0.050), -- Tomate: ½
(15, 27, 0.050), -- Betterave: ¼
(15, 17, 0.025), -- Oignon rouge: ¼
(15, 60, 0.005), -- Persil: 1 branche
(15, 43, 0.030), -- Mayonnaise: 2 c.à.s
(15, 44, 0.005), -- Moutarde: 1 c.à.c
(15, 61, 0.50);  -- Citron: ½

-- 16. PÂTES COQUILLAGES (Recette ID: 16)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(16, 8, 0.100),  -- Pâtes coquillages: 100g
(16, 24, 0.050), -- Carotte: ½
(16, 14, 0.050), -- Tomate: ½
(16, 16, 0.025), -- Oignon: ¼
(16, 60, 0.005), -- Persil: 1 branche
(16, 43, 0.030), -- Mayonnaise: 2 c.à.s
(16, 44, 0.005); -- Moutarde: 1 c.à.c

-- 17. GBOMA DESSI (Recette ID: 17)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(17, 11, 0.125), -- Épinards: 125g
(17, 30, 0.075), -- Viande de boeuf: 75g
(17, 13, 0.050), -- Aubergine africaine: ½
(17, 16, 0.050), -- Oignon: ½
(17, 18, 0.005), -- Ail: 1 gousse
(17, 19, 0.003), -- Gingembre: ½cm
(17, 14, 0.100), -- Tomate: 1 ou concentré
(17, 38, 0.025), -- Huile de palme: 25ml
(17, 21, 0.010), -- Piment: ½
(17, 42, 0.50),  -- Cube bouillon: ½
(17, 53, 0.001); -- Bicarbonate: pincée

-- 18. DÉGUÉ (Recette ID: 18)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(18, 7, 0.050),  -- Couscous de mil: 50g
(18, 47, 0.075), -- Yaourt nature: 75g
(18, 48, 0.030), -- Fromage blanc: 2 c.à.s (30g)
(18, 49, 0.030), -- Lait concentré non sucré: 2 c.à.s
(18, 50, 0.015), -- Lait concentré sucré: 1 c.à.c
(18, 59, 0.003), -- Vanille: ½ c.à.c
(18, 58, 0.002), -- Muscade: pincée
(18, 64, 0.015), -- Sucre: 1 c.à.s (facultatif)
(18, 65, 0.020), -- Raisins secs: 1 poignée (20g)
(18, 40, 0.005), -- Beurre: 1 c.à.c (5g)
(18, 66, 0.015); -- Arachides: 1 c.à.s (15g)

-- 19. YAOURT / LAIT CAILLÉ (Recette ID: 19)
INSERT INTO recette_ingredients (recette_id, ingredient_id, quantite_base) VALUES
(19, 45, 0.250), -- Lait entier: 250ml (ou lait en poudre)
(19, 46, 0.060), -- Lait en poudre: 60g (alternatif)
(19, 47, 0.030), -- Yaourt nature: 2 c.à.s (30g) pour ferment
(19, 58, 0.001); -- Muscade: pincée (facultatif)

-- =======================
-- 7. ÉTAPES DE PRÉPARATION
-- =======================

-- 1. ADÉMÉ
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(1, 1, 'Préparation: Laver les feuilles d''adémé et les hacher finement. Mixer ensemble l''ail, l''oignon et le gingembre. Nettoyer le poisson fumé.', 20, CURRENT_TIMESTAMP),
(1, 2, 'Cuisson feuilles: Faire bouillir ¼ verre d''eau avec une pincée de bicarbonate. Ajouter les feuilles hachées, remuer constamment pendant 10 minutes.', 10, CURRENT_TIMESTAMP),
(1, 3, 'Ajout protéines: Ajouter le mélange ail-oignon-gingembre et les crevettes. Faire bouillir 5 minutes. Verser l''huile de palme et le cube bouillon, laisser mijoter 10 minutes.', 15, CURRENT_TIMESTAMP),
(1, 4, 'Finition: Ajouter le piment et le poisson fumé. Mijoter encore 5 minutes. Servir chaud avec akoumé, foufou ou riz blanc.', 5, CURRENT_TIMESTAMP);

-- 2. SAUCE GOMBO
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(2, 1, 'Préparation gombos: Laver et couper les gombos en rondelles fines. Mixer ensemble l''ail, l''oignon et le gingembre.', 25, CURRENT_TIMESTAMP),
(2, 2, 'Cuisson base: Faire bouillir ¼ verre d''eau avec une pincée de kanwa. Ajouter les gombos, remuer vigoureusement pendant 3 minutes. Ajouter les aubergines coupées, laisser mijoter 5 minutes.', 8, CURRENT_TIMESTAMP),
(2, 3, 'Protéines: Incorporer le mélange ail-oignon-gingembre, le cube bouillon, la viande/poisson et les crevettes. Mijoter 5 minutes.', 5, CURRENT_TIMESTAMP),
(2, 4, 'Finition: Ajouter les piments, cuire 2-3 minutes. Verser l''huile de palme et continuer la cuisson jusqu''à ce que l''huile remonte à la surface.', 7, CURRENT_TIMESTAMP);

-- 3. AYIMOLOU
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(3, 1, 'Trempage: Faire tremper les haricots rouges dans l''eau froide pendant au moins 4 heures ou toute la nuit. Bien rincer.', 240, CURRENT_TIMESTAMP),
(3, 2, 'Cuisson haricots: Faire bouillir les haricots dans 400ml d''eau pendant 25-30 minutes jusqu''à mi-cuisson.', 30, CURRENT_TIMESTAMP),
(3, 3, 'Ajout riz: Laver le riz et l''ajouter aux haricots à mi-cuisson. Ajouter l''huile de palme, le cube bouillon, sel, poivre et de l''eau si nécessaire. Cuire à feu doux pendant 20-25 minutes.', 25, CURRENT_TIMESTAMP),
(3, 4, 'Finition: Faire revenir ¼ d''oignon émincé dans un peu d''huile jusqu''à ce qu''il soit doré. Ajouter sur le plat. Servir chaud avec poisson frit ou sauce au choix.', 5, CURRENT_TIMESTAMP);

-- 4. SAUCE PISTACHE
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(4, 1, 'Cuisson viande: Faire bouillir la viande avec ¼ oignon dans 125ml d''eau pendant 20 minutes. Réserver le bouillon.', 20, CURRENT_TIMESTAMP),
(4, 2, 'Préparation base: Mélanger la pâte d''arachide avec ¼ verre d''eau pour la diluer. Mixer ensemble les tomates, l''ail, le gingembre et le reste de l''oignon.', 10, CURRENT_TIMESTAMP),
(4, 3, 'Cuisson sauce: Faire dorer la viande dans l''huile de palme. Ajouter le mélange de tomates et le concentré. Faire revenir 5-10 minutes.', 10, CURRENT_TIMESTAMP),
(4, 4, 'Ajout pistache: Verser la pâte d''arachide diluée. Ajouter le piment, la poudre de crevettes, les cubes bouillon et 250ml d''eau. Laisser mijoter 30 minutes.', 30, CURRENT_TIMESTAMP),
(4, 5, 'Finition: Ajuster le sel. Continuer la cuisson jusqu''à obtenir une sauce épaisse avec l''huile qui remonte à la surface. Servir avec foufou, riz ou akoumé.', 10, CURRENT_TIMESTAMP);

-- 5. ALLOCO
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(5, 1, 'Préparation: Éplucher la banane plantain en coupant les extrémités et en entaillant la peau sur toute la longueur. Couper en rondelles de 5mm à 1cm d''épaisseur.', 10, CURRENT_TIMESTAMP),
(5, 2, 'Assaisonnement: Saupoudrer légèrement les tranches de sel et/ou de piment en poudre (facultatif selon votre goût).', 2, CURRENT_TIMESTAMP),
(5, 3, 'Friture: Chauffer l''huile dans une poêle à feu moyen-vif. Frire les tranches de banane 4-5 minutes de chaque côté jusqu''à ce qu''elles soient bien dorées.', 10, CURRENT_TIMESTAMP),
(5, 4, 'Service: Égoutter sur du papier absorbant. Servir chaud en accompagnement de poisson frit, poulet ou avec une sauce pimentée.', 2, CURRENT_TIMESTAMP);

-- 6. ATTIÉKÉ POISSON
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(6, 1, 'Marinade poisson: Mixer l''ail, le gingembre, le persil, ¼ oignon et le piment. Ajouter sel, poivre, jus de citron et un peu d''huile. Mariner le poisson pendant 2 heures au frais.', 130, CURRENT_TIMESTAMP),
(6, 2, 'Attiéké: Égrainer l''attiéké dans un plat. Ajouter l''eau tiède et le sel. Laisser reposer 5-10 minutes. Faire cuire à la vapeur 10-15 minutes. Incorporer l''huile et mélanger.', 25, CURRENT_TIMESTAMP),
(6, 3, 'Poisson: Faire frire le poisson mariné dans l''huile chaude 5-7 minutes de chaque côté jusqu''à ce qu''il soit bien doré. Égoutter.', 14, CURRENT_TIMESTAMP),
(6, 4, 'Salade: Couper la tomate, le concombre et l''oignon rouge en petits dés. Assaisonner avec du jus de citron, de l''huile, sel et poivre.', 10, CURRENT_TIMESTAMP),
(6, 5, 'Service: Dresser l''attiéké dans une assiette, disposer le poisson par-dessus et la salade à côté. Servir avec une sauce pimentée.', 5, CURRENT_TIMESTAMP);

-- 7. ABLO
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(7, 1, 'Bouillie: Faire cuire la farine de maïs dans 100ml d''eau en remuant constamment jusqu''à obtenir une bouillie. Retirer du feu et laisser refroidir.', 10, CURRENT_TIMESTAMP),
(7, 2, 'Pâte: Ajouter progressivement la farine de riz et la Maïzena à la bouillie refroidie. Diluer la levure boulangère dans de l''eau tiède. Ajouter à la pâte avec le sucre, le sel et la levure chimique. Bien mélanger.', 15, CURRENT_TIMESTAMP),
(7, 3, 'Repos: Couvrir le mélange et laisser reposer dans un endroit chaud pendant 1-2 heures jusqu''à ce que des bulles apparaissent à la surface.', 90, CURRENT_TIMESTAMP),
(7, 4, 'Cuisson: Remplir des petits moules à ½ ou ¾ de leur hauteur. Faire cuire à la vapeur pendant 15-20 minutes jusqu''à ce que les ablos soient fermes au toucher. Démouler et servir avec sauce.', 20, CURRENT_TIMESTAMP);

-- 8. KOLIKO
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(8, 1, 'Préparation: Éplucher l''igname avec précaution. Couper en bâtonnets épais ou en tranches épaisses. Rincer à l''eau froide pour enlever l''excès d''amidon.', 10, CURRENT_TIMESTAMP),
(8, 2, 'Assaisonnement: Saupoudrer de sel et de piment si désiré. Bien mélanger.', 2, CURRENT_TIMESTAMP),
(8, 3, 'Friture: Chauffer abondamment l''huile dans une poêle profonde. Frire les morceaux d''igname pendant 10-12 minutes jusqu''à ce qu''ils soient dorés et croustillants à l''extérieur.', 12, CURRENT_TIMESTAMP),
(8, 4, 'Service: Égoutter sur du papier absorbant. Servir chaud avec une sauce pimentée ou des oignons émincés.', 2, CURRENT_TIMESTAMP);

-- 9. PIGNON
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(9, 1, 'Sauce: Mixer la tomate, l''ail et le piment ensemble. Faire revenir l''oignon émincé dans l''huile chaude jusqu''à translucide. Ajouter le mélange mixé et le concentré de tomate. Faire cuire 15 minutes jusqu''à épaississement.', 20, CURRENT_TIMESTAMP),
(9, 2, 'Gari: Verser le gari dans un grand bol. Mélanger légèrement à sec (ne pas mouiller).', 2, CURRENT_TIMESTAMP),
(9, 3, 'Viande: Griller la viande ou la réchauffer si elle est déjà cuite.', 5, CURRENT_TIMESTAMP),
(9, 4, 'Service: Dresser le gari dans une assiette, faire un puits au centre et y verser la sauce. Ajouter la viande grillée et des oignons crus émincés par-dessus.', 3, CURRENT_TIMESTAMP);

-- 10. FOUFOU
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(10, 1, 'Cuisson: Éplucher et couper l''igname (ou le manioc) en gros morceaux. Faire bouillir dans de l''eau salée pendant 20-25 minutes jusqu''à ce qu''ils soient très tendres.', 25, CURRENT_TIMESTAMP),
(10, 2, 'Pilage: Égoutter les morceaux en gardant l''eau de cuisson. Piler vigoureusement avec un pilon ou mixer en ajoutant petit à petit l''eau de cuisson jusqu''à obtenir une pâte lisse, élastique et très collante.', 10, CURRENT_TIMESTAMP),
(10, 3, 'Façonnage: Façonner la pâte en boule avec les mains mouillées d''eau.', 2, CURRENT_TIMESTAMP),
(10, 4, 'Service: Servir immédiatement tant que c''est chaud avec sauce gombo, sauce pistache ou adémé.', 3, CURRENT_TIMESTAMP);

-- 11. ŒUFS FRITS
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(11, 1, 'Préparation: Émincer finement l''oignon, la tomate et le piment vert.', 5, CURRENT_TIMESTAMP),
(11, 2, 'Cuisson légumes: Chauffer l''huile dans une poêle. Faire revenir l''oignon, la tomate et le piment pendant 2 minutes.', 2, CURRENT_TIMESTAMP),
(11, 3, 'Œufs: Casser les œufs directement dans la poêle sur les légumes. Saler et poivrer. Cuire à feu moyen jusqu''à la cuisson désirée (jaune coulant ou bien cuit).', 5, CURRENT_TIMESTAMP),
(11, 4, 'Service: Servir immédiatement chaud avec du pain frais, de l''ablo ou de l''alloco.', 1, CURRENT_TIMESTAMP);

-- 12. SAUCE MOUTON/TRIPES
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(12, 1, 'Nettoyage tripes: Nettoyer soigneusement les tripes à l''eau froide et au citron. Frotter énergiquement. Couper en morceaux moyens.', 30, CURRENT_TIMESTAMP),
(12, 2, 'Cuisson abats: Faire bouillir les tripes et la viande de mouton avec ¼ oignon et l''ail dans 300ml d''eau pendant 45 minutes. Réserver le bouillon.', 45, CURRENT_TIMESTAMP),
(12, 3, 'Sauce: Mixer la tomate, le reste de l''oignon et l''ail. Faire revenir ce mélange dans l''huile de palme avec le concentré de tomate pendant 10 minutes.', 10, CURRENT_TIMESTAMP),
(12, 4, 'Mijotage: Ajouter les abats cuits, le bouillon réservé, le cube bouillon, le piment, sel et poivre. Laisser mijoter à feu doux 20-30 minutes jusqu''à obtenir une sauce épaisse et rouge.', 30, CURRENT_TIMESTAMP),
(12, 5, 'Service: Servir très chaud avec du riz blanc, de l''akoumé ou du foufou.', 5, CURRENT_TIMESTAMP);

-- 13. RIZ AU GRAS ROUGE / JOLLOF
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(13, 1, 'Sauce base: Mixer ensemble la tomate, le poivron rouge, ¼ oignon, l''ail, le gingembre et le piment. Dans une casserole, faire revenir ¼ oignon émincé dans l''huile jusqu''à doré.', 10, CURRENT_TIMESTAMP),
(13, 2, 'Cuisson sauce: Ajouter le mélange mixé et le concentré de tomate. Faire mijoter 15 minutes en remuant jusqu''à épaississement et jusqu''à ce que l''huile remonte.', 15, CURRENT_TIMESTAMP),
(13, 3, 'Ajout riz: Laver le riz et l''ajouter à la sauce. Ajouter le bouillon de poulet, la feuille de laurier, le thym, le curry, sel et poivre. Bien mélanger pour enrober le riz.', 5, CURRENT_TIMESTAMP),
(13, 4, 'Cuisson riz: Couvrir hermétiquement la casserole. Cuire à feu très doux pendant 20-25 minutes sans ouvrir le couvercle. Vérifier que le riz est cuit et que l''eau est absorbée.', 25, CURRENT_TIMESTAMP),
(13, 5, 'Service: Laisser reposer 5 minutes avant de servir. Accompagner de poulet grillé, poisson frit ou bananes plantains frites.', 5, CURRENT_TIMESTAMP);

-- 14. RIZ AU GRAS BLANC
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(14, 1, 'Préparation: Laver le riz abondamment. Émincer l''oignon et l''ail. Couper la carotte en petits dés.', 10, CURRENT_TIMESTAMP),
(14, 2, 'Base: Faire revenir l''oignon et l''ail dans l''huile pendant 2 minutes. Ajouter les carottes et les petits pois, faire sauter 3 minutes.', 5, CURRENT_TIMESTAMP),
(14, 3, 'Cuisson riz: Ajouter le riz lavé, mélanger 1 minute pour bien enrober. Verser l''eau (200ml), ajouter le cube bouillon, la feuille de laurier, sel et poivre.', 2, CURRENT_TIMESTAMP),
(14, 4, 'Mijotage: Porter à ébullition, puis couvrir et cuire à feu doux pendant 20-25 minutes jusqu''à ce que toute l''eau soit absorbée et le riz tendre.', 25, CURRENT_TIMESTAMP),
(14, 5, 'Service: Laisser reposer 5 minutes à couvert. Servir chaud avec viande, poisson ou sauce au choix.', 5, CURRENT_TIMESTAMP);

-- 15. SALADE COMPOSÉE
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(15, 1, 'Préparation légumes: Râper finement la carotte crue. Couper la tomate et la betterave cuite en petits dés. Émincer finement l''oignon rouge. Hacher le persil frais.', 15, CURRENT_TIMESTAMP),
(15, 2, 'Sauce: Dans un bol, mélanger la mayonnaise, la moutarde, le jus de citron, sel et poivre jusqu''à obtenir une sauce homogène.', 3, CURRENT_TIMESTAMP),
(15, 3, 'Assemblage: Disposer harmonieusement les légumes dans une assiette ou un bol. Verser la sauce sur les légumes ou la servir à part selon préférence.', 3, CURRENT_TIMESTAMP),
(15, 4, 'Service: Garnir de persil haché frais. Servir bien frais en accompagnement de viande, poisson ou en entrée.', 2, CURRENT_TIMESTAMP);

-- 16. PÂTES COQUILLAGES
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(16, 1, 'Cuisson pâtes: Faire bouillir une grande quantité d''eau salée. Cuire les pâtes selon le temps indiqué sur le paquet (généralement 10-12 minutes). Égoutter et rincer immédiatement à l''eau froide.', 12, CURRENT_TIMESTAMP),
(16, 2, 'Légumes: Pendant la cuisson des pâtes, râper la carotte, couper la tomate en petits dés, émincer finement l''oignon et hacher le persil.', 10, CURRENT_TIMESTAMP),
(16, 3, 'Sauce: Dans un grand saladier, mélanger la mayonnaise et la moutarde avec sel et poivre.', 3, CURRENT_TIMESTAMP),
(16, 4, 'Assemblage: Ajouter les pâtes froides et tous les légumes préparés à la sauce. Bien mélanger pour enrober uniformément. Rectifier l''assaisonnement si nécessaire.', 5, CURRENT_TIMESTAMP),
(16, 5, 'Service: Réfrigérer 30 minutes avant de servir (facultatif mais recommandé). Garnir de persil frais haché. Se conserve 2 jours au frigo.', 30, CURRENT_TIMESTAMP);

-- 17. GBOMA DESSI
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(17, 1, 'Cuisson viande: Couper la viande en morceaux moyens. Faire bouillir avec ¼ oignon, l''ail et le gingembre dans 150ml d''eau pendant 15 minutes. Réserver le bouillon.', 15, CURRENT_TIMESTAMP),
(17, 2, 'Épinards: Laver soigneusement les épinards ou feuilles gboma. Les faire bouillir dans de l''eau avec du sel et une pincée de bicarbonate pendant 15 minutes. Égoutter et hacher grossièrement.', 15, CURRENT_TIMESTAMP),
(17, 3, 'Sauce: Faire revenir ¼ oignon et l''ail dans l''huile de palme. Ajouter la tomate mixée ou le concentré, faire dorer 15 minutes en remuant régulièrement. Assaisonner avec cube bouillon, piment, sel et poivre.', 15, CURRENT_TIMESTAMP),
(17, 4, 'Assemblage: Ajouter le bouillon de viande réservé, porter à ébullition et laisser bouillir 20 minutes. Ajouter les épinards bien essorés, puis la viande. Laisser mijoter 15-20 minutes.', 35, CURRENT_TIMESTAMP),
(17, 5, 'Service: Servir très chaud avec akoumé, foufou, ablo ou riz blanc.', 5, CURRENT_TIMESTAMP);

-- 18. DÉGUÉ
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(18, 1, 'Cuisson du millet: Méthode vapeur (traditionnelle): Faire tremper le couscous de mil dans l''eau chaude pendant 5 minutes. Cuire à la vapeur dans un couscoussier pendant 25 minutes. Ajouter le beurre, égrener à la fourchette et laisser refroidir complètement. Méthode rapide: Verser l''eau chaude sur le millet, couvrir 5-7 minutes. Égrener à la fourchette avec le beurre. Laisser refroidir.', 35, CURRENT_TIMESTAMP),
(18, 2, 'Mélange crémeux: Dans un grand bol, fouetter ensemble le yaourt nature, le fromage blanc, le lait concentré non sucré, le lait concentré sucré, la vanille et la muscade jusqu''à obtenir une texture bien lisse et homogène.', 10, CURRENT_TIMESTAMP),
(18, 3, 'Assemblage: Une fois le millet complètement froid, l''incorporer délicatement au mélange crémeux en mélangeant doucement. Ajouter les raisins secs si désiré. Goûter et ajouter du sucre supplémentaire si besoin selon votre goût.', 10, CURRENT_TIMESTAMP),
(18, 4, 'Repos et service: Couvrir le bol et placer au réfrigérateur minimum 1 heure (idéalement 2-3 heures) pour que toutes les saveurs se mélangent bien. Servir bien frais dans des coupes individuelles, garnir d''arachides concassées sur le dessus. Se conserve 2-3 jours au réfrigérateur.', 120, CURRENT_TIMESTAMP);

-- 19. YAOURT / LAIT CAILLÉ
INSERT INTO etapes (recette_id, ordre, instruction, duree_minutes, date_creation) VALUES
(19, 1, 'Préparation du lait: Si vous utilisez du lait en poudre: verser 60g de lait en poudre dans un bol. Ajouter 250ml d''eau tiède (40°C maximum) et mélanger jusqu''à dissolution complète. Si vous utilisez du lait liquide: faire chauffer le lait à 80-85°C pendant 5 minutes (ne pas faire bouillir). Laisser refroidir jusqu''à 40-42°C (doit être tiède au toucher).', 20, CURRENT_TIMESTAMP),
(19, 2, 'Ajout du ferment: Laisser le yaourt nature revenir à température ambiante pendant 20 minutes. Une fois le lait tiède (40°C), ajouter les 2 cuillères à soupe de yaourt nature et mélanger délicatement mais complètement pour bien répartir les ferments lactiques.', 25, CURRENT_TIMESTAMP),
(19, 3, 'Fermentation: Verser le mélange dans un récipient propre en plastique ou en verre (JAMAIS en aluminium). Fermer hermétiquement avec un couvercle. Envelopper le récipient dans 2 serviettes épaisses ou une couverture. Placer dans un endroit chaud et stable (four éteint, près d''un radiateur, ou dans une glacière avec de l''eau chaude). Laisser fermenter 8-12 heures sans y toucher (idéal de faire le soir, c''est prêt le matin).', 540, CURRENT_TIMESTAMP),
(19, 4, 'Vérification et conservation: Après 8-12h de fermentation, le lait doit avoir pris une consistance crémeuse et avoir un goût légèrement aigre. Si c''est encore liquide, laisser fermenter 2-3 heures de plus. Une fois fermenté, remuer délicatement avec une cuillère pour homogénéiser. Réfrigérer immédiatement.', 10, CURRENT_TIMESTAMP),
(19, 5, 'Service: Servir bien frais. Ajouter du sucre, de la muscade ou de la vanille selon votre goût personnel. Peut se boire comme une boisson rafraîchissante ou se manger à la cuillère comme un yaourt. Se conserve 7-10 jours au réfrigérateur. Important: Garder 2 cuillères à soupe de votre lait caillé pour refaire une nouvelle fournée!', 5, CURRENT_TIMESTAMP);

-- =======================
-- 8. VÉRIFICATION
-- =======================
SELECT 
    r.nom AS recette,
    r.temps_preparation AS "temps (min)",
    r.difficulte,
    COUNT(DISTINCT ri.ingredient_id) AS "nb_ingredients",
    COUNT(DISTINCT e.numero_ordre) AS "nb_etapes",
    SUM(i.prix_unitaire * ri.quantite_base) AS "cout_total_fcfa"
FROM recettes r
LEFT JOIN recette_ingredients ri ON r.id = ri.recette_id
LEFT JOIN ingredients i ON ri.ingredient_id = i.id
LEFT JOIN etapes e ON r.id = e.recette_id
GROUP BY r.id, r.nom, r.temps_preparation, r.difficulte
ORDER BY r.id;

-- FIN DU SCRIPT
