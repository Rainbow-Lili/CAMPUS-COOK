-- ==========================================
-- RESTAURATION DES IMAGES LOCALES (au lieu des URLs Unsplash)
-- ==========================================
-- Cette requête restaure les noms de fichiers d'images locales
-- qui ont été uploadées dans /uploads/images/
-- au lieu des URLs Unsplash qui les avaient écrasées

UPDATE recettes SET image_url = CASE id
    WHEN 1 THEN 'ademe.jpg'            -- Adémé (feuilles vertes)
    WHEN 2 THEN 'gombo.jpg'            -- Gombo
    WHEN 3 THEN 'ayimolou.jpg'         -- Riz haricots
    WHEN 4 THEN 'sauce_arachide.jpg'   -- Sauce arachide
    WHEN 5 THEN 'alloco.png'           -- Alloco (plantain frit)
    WHEN 6 THEN 'attieke.png'          -- Attiéké poisson
    WHEN 7 THEN 'ablo.jpg'             -- Ablo (gâteau)
    WHEN 8 THEN 'koliko.jpg'           -- Koliko (igname frite)
    WHEN 9 THEN 'pignon.jpg'           -- Pignon (tomates)
    WHEN 10 THEN 'foufou.jpg'          -- Foufou
    WHEN 11 THEN 'oeufs_frits.jpg'     -- Œufs frits
    WHEN 12 THEN 'sauce_tripes.jpg'    -- Sauce mouton
    WHEN 13 THEN 'jollof.jpg'          -- Jollof rice
    WHEN 14 THEN 'riz_blanc.jpg'       -- Riz blanc
    WHEN 15 THEN 'salade.jpg'          -- Salade
    WHEN 16 THEN 'pates_salade.jpg'    -- Pâtes
    WHEN 17 THEN 'gboma.jpg'           -- Gboma (épinards)
    WHEN 18 THEN 'degue.jpg'           -- Dégué (dessert)
    WHEN 19 THEN 'kossam.jpg'          -- Yaourt
    ELSE image_url  -- Ne pas modifier les autres recettes
END
WHERE id BETWEEN 1 AND 19;

-- Vérification après mise à jour
SELECT id, nom, image_url FROM recettes WHERE id BETWEEN 1 AND 19 ORDER BY id;
