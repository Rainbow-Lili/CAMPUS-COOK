# COOK'CAMPUS MARKETPLACE - STRUCTURE DE DONNÉES

## ✅ PROGRESSION DE L'IMPLÉMENTATION

### BACKEND (Java JAX-RS + PostgreSQL) ✅ COMPLÉTÉ
- [x] Pack.java - Attributs ajoutés : categorie, budget, typePack
- [x] Boutique.java - Attributs ajoutés : categorie, type
- [x] ContenuPack.java - Déjà existant
- [x] PackDAO - Méthodes ajoutées : findByCategorie, findByBudget, findByCategorieAndBudget, findByTypePack
- [x] BoutiqueDAO - Méthodes ajoutées : findByCategorie, findByType
- [x] PackService - Méthodes ajoutées : findByCategorie, findByBudget, findByCategorieAndBudget, findByTypePack
- [x] BoutiqueService - Méthodes ajoutées : findByCategorie, findByType
- [x] PackResource - Endpoints : /categorie/{}, /budget/{}, /type/{}, etc.
- [x] BoutiqueResource - Endpoints : /categorie/{}, /type/{}, /specialisees
- [ ] Endpoints pour les commandes marketplace (à venir)

### MOBILE (Android) ✅ COMPLÉTÉ
- [x] Pack.java (modèle Android) - 11 attributs + méthodes helper
- [x] Boutique.java (modèle Android) - 11 attributs + méthodes helper
- [x] item_pack.xml - Layout carte pack avec image, badge budget, prix, stock
- [x] item_boutique.xml - Layout carte boutique horizontal avec logo
- [x] PackAdapter.java - RecyclerView adapter avec Coil pour les images
- [x] BoutiqueAdapter.java - RecyclerView adapter avec Coil
- [x] ApiService.java - Endpoints marketplace ajoutés (packs + boutiques)
- [x] PackRepository.java - Toutes les méthodes d'accès API
- [x] BoutiqueRepository.java - Toutes les méthodes d'accès API
- [x] MarketplaceFragment.java - Connecté aux vraies données API via repositories
- [ ] PackDetailActivity - Écran de détail d'un pack
- [ ] BoutiqueDetailActivity - Écran de détail d'une boutique
- [ ] Système de panier (cart) pour commander
- [ ] Interface de commande et paiement

### 🎉 MARKETPLACE FONCTIONNEL !
Le marketplace est maintenant opérationnel :
- ✅ Backend API prêt avec tous les endpoints nécessaires
- ✅ Repositories Android connectés à l'API
- ✅ Interface affichant les vraies données du serveur
- ✅ Adapters avec design cohérent (orange/beige)
- ✅ Gestion du chargement et des erreurs

### À FAIRE PROCHAINEMENT
1. ~~Créer les endpoints API backend pour récupérer packs et boutiques~~ ✅ FAIT
2. ~~Créer les repositories Android pour appels API~~ ✅ FAIT
3. ~~Remplacer les données de test par de vraies données API~~ ✅ FAIT
4. Créer les écrans de détails (PackDetailActivity, BoutiqueDetailActivity)
5. Implémenter le panier et le système de commande
6. Seeder la base de données avec les packs/boutiques de Lomé 2026

---

## 🗂️ ATTRIBUTS DES ENTITÉS

### 1. ENTITÉ BOUTIQUE (Shop/Store)

**Attributs obligatoires :**
- `id` : Long (auto-généré)
- `nom` : String (ex: "Boucherie Moderne Lomé", "Marché Bio Tokoin")
- `description` : String (courte présentation de la boutique)
- `type` : String (PACK_STANDARD, BOUTIQUE_SPECIALISEE)
- `categorie` : String (VIANDES, LEGUMES, CEREALES, CONSERVES, FRUITS, etc.)
- `adresse` : String (localisation complète)
- `telephone` : String (+228 XX XX XX XX)
- `email` : String
- `horairesOuverture` : String (ex: "Lun-Sam 7h-19h")
- `actif` : Boolean (boutique active/désactivée)
- `logo` : String (URL de l'image/logo)
- `fournisseurId` : Long (référence vers l'utilisateur fournisseur)

---

### 2. ENTITÉ PACK

**Attributs obligatoires :**
- `id` : Long (auto-généré)
- `nom` : String (ex: "Pack Viande 1000F - Protéines Express")
- `description` : String (description complète du pack)
- `prix` : Double (prix fixe garanti en FCFA)
- `stock` : Integer (quantité disponible)
- `categorie` : String (VIANDES, LEGUMES, CEREALES, CONSERVES, FRUITS)
- `budget` : String (500F, 1000F, 2000F, 3000F, 5000F, 10000F, 20000F)
- `actif` : Boolean
- `boutiqueId` : Long (référence vers la boutique)
- `typePack` : String (STANDARD, COMPLET_RECETTE, SEMAINE, COLOCATION)
- `imageUrl` : String (photo du pack)

---

### 3. ENTITÉ CONTENU_PACK (PackIngredient)

**Attributs obligatoires :**
- `id` : Long (auto-généré)
- `packId` : Long (référence vers le pack)
- `ingredientId` : Long (référence vers l'ingrédient)
- `quantite` : Double (quantité de l'ingrédient)
- `unite` : String (g, kg, L, ml, pièce, botte, etc.)

---

### 4. ENTITÉ CATEGORIE_MARKETPLACE

**Attributs :**
- `id` : Long
- `nom` : String (VIANDES_PROTEINES, LEGUMES_TUBERCULES, etc.)
- `emoji` : String (🥩, 🥬, 🌾, 🥫, 🍊)
- `description` : String
- `ordre` : Integer (ordre d'affichage)
- `actif` : Boolean
- `imageUrl` : String (bannière de la catégorie)

**Catégories prédéfinies :**
1. 🥩 VIANDES_PROTEINES - "Viandes & Protéines"
2. 🥬 LEGUMES_TUBERCULES - "Légumes & Tubercules"
3. 🌾 FECULENTS_CEREALES - "Féculents & Céréales"
4. 🥫 CONSERVES_PRODUITS_BASE - "Conserves & Produits de Base"
5. 🍊 FRUITS - "Fruits"

---

### 5. ENTITÉ BUDGET_RANGE

**Attributs :**
- `id` : Long
- `label` : String ("500F", "1000F", "2000F", etc.)
- `montant` : Double (500, 1000, 2000, etc.)
- `ordre` : Integer (1, 2, 3, etc.)
- `actif` : Boolean

**Budgets prédéfinis :**
1. 500F - "Starter Étudiant"
2. 1000F - "Express"
3. 2000F - "Complet"
4. 3000F - "Semaine courte"
5. 5000F - "Semaine complète"
6. 10000F - "Deux semaines"
7. 20000F - "Colocation"

---

### 6. ENTITÉ COMMANDE (Order)

**Attributs :**
- `id` : Long
- `etudiantId` : Long (référence utilisateur)
- `packId` : Long
- `boutiqueId` : Long
- `quantite` : Integer
- `prixTotal` : Double
- `statut` : String (EN_ATTENTE, CONFIRMEE, PRETE, LIVREE, ANNULEE)
- `modePaiement` : String (TMONEY, FLOOZ, ESPECES, CARTE)
- `statutPaiement` : String (EN_ATTENTE, PAYE, REMBOURSE)
- `modeRecuperation` : String (RETRAIT_BOUTIQUE, LIVRAISON)
- `adresseLivraison` : String
- `dateCommande` : Date
- `dateConfirmation` : Date
- `dateLivraison` : Date
- `codeQR` : String (pour retrait)
- `notes` : String (instructions spéciales)

---

### 7. ENTITÉ AVIS (Review)

**Attributs :**
- `id` : Long
- `etudiantId` : Long
- `packId` : Long (ou boutiqueId)
- `note` : Integer (1-5 étoiles)
- `commentaire` : String
- `dateAvis` : Date
- `utile` : Integer (nombre de "utile")
- `verifie` : Boolean (achat vérifié)

---

## 📊 MAPPING AVEC BACKEND EXISTANT

### Entités déjà créées (✅)
- ✅ **Boutique.java** - Correspond à ENTITÉ BOUTIQUE ci-dessus
- ✅ **Pack.java** - Correspond à ENTITÉ PACK
- ✅ **ContenuPack.java** - Correspond à ENTITÉ CONTENU_PACK

### Entités à créer (❌)
- ❌ **CategorieMarketplace.java** - Pour gérer les 5 catégories principales
- ❌ **BudgetRange.java** - Pour les paliers de prix (optionnel, peut être géré en front)
- ❌ **Commande.java** - Pour gérer les achats
- ❌ **AvisMarketplace.java** - Pour les évaluations

### Attributs à ajouter aux entités existantes

**Pack.java - Attributs manquants à ajouter :**
```java
private String budget; // "500F", "1000F", etc.
private String typePack; // STANDARD, COMPLET_RECETTE, SEMAINE, COLOCATION
private String imageUrl; // Photo du pack
```

**Boutique.java - Attributs manquants à ajouter :**
```java
private String type; // PACK_STANDARD ou BOUTIQUE_SPECIALISEE
private String logo; // URL du logo
private String email; // Email de contact
```

---

## 📱 STRUCTURE DE LA PAGE MARKETPLACE

### PARTIE 1 : 5 CATÉGORIES DE PACKS (avec budgets 500F, 1000F, 2000F, etc.)
### PARTIE 2 : SECTION "AUTRES BOUTIQUES" (boutiques spécialisées pour produits spécifiques)

A. ARCHITECTURE GÉNÉRALE
La marketplace est organisée en 3 niveaux:
•	NIVEAU 1 : Catégories principales
Viandes & Poissons | Légumes & Tubercules | Féculents & Céréales | Produits Laitiers | Épices & Condiments | Conserves | Packs Complets
•	NIVEAU 2 : Packs par budget
500F | 1000F | 2000F | 3000F | 5000F | 10000F | 20000F
•	NIVEAU 3 : Détails du pack
Image | Prix | Fournisseur | Liste des ingrédients | Quantités | Description | Contact
B. INFORMATIONS PAR PACK
Chaque pack contient les informations suivantes:
•	📸 Photo du pack (ou photo illustrative)
•	💰 Prix fixe garanti
•	🏪 Logo + Nom du fournisseur partenaire
•	📋 Liste détaillée des ingrédients avec quantités
•	📍 Adresse de la boutique/point de retrait
•	📞 Numéro de téléphone
•	⏰ Horaires d'ouverture
•	💬 Description courte (1-2 phrases)
•	⭐ Note/Avis des utilisateurs
•	📦 Mode de livraison (retrait sur place / livraison possible)



PARTIE 1 : CATÉGORIES DE PACKS PAR BUDGET
5 catégories avec packs de 500F à 20000F :

1. 🥩 Viandes & Protéines

Bœuf, poulet, poisson, œufs, tripes, crevettes


2. 🥬 Légumes & Tubercules

Tomates, oignons, gombo, épinards, igname, plantain


3. 🌾 Féculents & Céréales

Riz, pâtes, maïs, mil, haricots, gari, attiéké


4.🥫 Conserves & Produits de Base 

Sardines ,Tomates en boîte ,Mayonnaise ,Huile végétale ,Sel, sucre, cubes Maggi,Lait concentré, beurre, fromage


5. 🍊 Fruits 

Oranges , Bananes ,Mangues, pastèques, ananas, papayes ,Avocats, citrons, noix de coco


PARTIE 2 : SECTION "AUTRES BOUTIQUES"
8 boutiques spécialisées pour produits spécifiques :

1.🌾 BOUTIQUE CÉRÉALES & LÉGUMINEUSES - Grenier d'Afrique

Mil, sorgho, fonio, pistache/egoussi, couscous de mil, pâte d'arachide
Pour trouver : graines de pistache, mil, thiakry


2.🥤 BOUTIQUE BOISSONS & EAU MINÉRALE - Fraîcheur Express

Eau Possotomé, jus naturels, bissap, sodas, energy drinks
Pour trouver : eau minérale, boissons


3.🌶️ BOUTIQUE ÉPICES & CONDIMENTS - Saveurs du Terroir

Potasse, poudre de crevette, soumbala, dawadawa, épices africaines
Pour trouver : épices rares, condiments traditionnels


4.🧀 BOUTIQUE PRODUITS LAITIERS - Laiterie Moderne

Wagashi frais, yaourt local, lait caillé, fromages variés
Pour trouver : wagashi, yaourt artisanal


5.🥫 BOUTIQUE CONSERVES - Stock & Save

Corned-beef, pilchard, légumes en boîte, olives
Pour trouver : conserves variées


6.🥖 BOUTIQUE SNACKS & PÂTISSERIE - Boulangerie Délice

Pain frais, croissants, gâteaux, chin-chin
Pour trouver : pain chaud, pâtisseries


7.🥩 BOUTIQUE VIANDES & CHARCUTERIE - Boucherie Excellence

Saucisses, jambon, bacon, viande hachée, brochettes
Pour trouver : charcuterie, viandes transformées


8.🌿 BOUTIQUE HERBES MÉDICINALES - Pharmacopée Naturelle

Kinkeliba, citronnelle, menthe, basilic africain
Pour trouver : plantes médicinales, herbes fraîches


COOK'CAMPUS
MARKETPLACE COMPLÈTE
Structure : 5 Catégories de Packs + Boutiques Spécialisées
PARTIE 1: CATÉGORIES DE PACKS PAR BUDGET
Dans cette section, les étudiants trouvent des packs pré-établis organisés par budget (500F, 1000F, 2000F, 3000F, 5000F, 10000F, 20000F) dans 5 catégories principales:
•	CATÉGORIE 1: Viandes & Protéines
Bœuf, poulet, poisson, œufs, tripes, crevettes
•	CATÉGORIE 2: Légumes & Tubercules
Tomates, oignons, gombo, épinards, igname, plantain, carottes, etc.
•	CATÉGORIE 3: Féculents & Céréales
Riz, pâtes, maïs, mil, haricots, soja, gari, attiéké
•	CATÉGORIE 4: Conserves & Produits de Base
Sardines, tomates en boîte, mayonnaise, huile, sel, épices, cube Maggi
•	CATÉGORIE 5: Fruits
Oranges, bananes, mangues, pastèques, ananas, papayes, avocats
PRIX DES CONSERVES & PRODUITS DE BASE (Lomé 2026)
Produit	Prix	Unité
Sardines à l'huile (boîte 125g)	425 F	boîte
Sardines carton de 50 boîtes	21000 F	carton
Thon à l'huile (boîte 160g)	600 F	boîte
Concentré de tomate (70g)	100 F	tube
Concentré de tomate (210g)	250 F	boîte
Tomates pelées (boîte 400g)	350 F	boîte
Mayonnaise Petit Matin (250ml)	500 F	pot
Mayonnaise Mabona (500ml)	800 F	pot
Huile végétale (1L)	1600 F	bouteille
Huile végétale (5L)	7500 F	bidon
Huile de palme (1L)	2000 F	bouteille
Sel fin (500g)	200 F	paquet
Sel gemme (1kg)	300 F	sachet
Cube Maggi (25 cubes)	250 F	boîte
Cube Jumbo (10 cubes)	200 F	paquet
Sucre blanc cristal (1kg)	1000 F	paquet
Lait concentré sucré (397g)	500 F	boîte
Lait en poudre (400g)	2500 F	boîte
Lait liquide (1L)	1200 F	brique
Beurre (250g)	1800 F	plaquette
Fromage portion (8 portions)	1500 F	boîte
Pâte de tomate (200g)	300 F	tube
Ketchup (500ml)	800 F	bouteille
Moutarde (250ml)	650 F	pot
Vinaigre blanc (1L)	600 F	bouteille
Poudre de lait Gloria (900g)	4200 F	sachet
Confiture (350g)	1200 F	pot
Miel liquide (500ml)	2500 F	pot
Café soluble Nescafé (100g)	2800 F	pot
Thé Lipton (25 sachets)	1500 F	boîte
 
PRIX DES FRUITS (Lomé 2026)
Fruit	Prix	Unité
Oranges locales (4 grosses)	200 F	lot
Bananes dessert (1 régime ~12)	500 F	régime
Mangues (selon saison)	100-300 F	pièce
Pastèque (petite)	800 F	pièce
Pastèque (grande)	1500 F	pièce
Ananas (petit)	500 F	pièce
Ananas (gros)	1000 F	pièce
Papaye (moyenne)	600 F	pièce
Papaye (grande)	1200 F	pièce
Avocat (gros)	300 F	pièce
Citrons verts (6 pièces)	200 F	lot
Pommes importées	400 F	pièce
Raisins importés	3000 F	kg
Noix de coco	200 F	pièce
Corossol	1500 F	pièce
Goyave (3 pièces)	200 F	lot
 
EXEMPLES DE PACKS - CATÉGORIE CONSERVES
PACK CONSERVES 1000F - Essentiels Express
💰 1000 FCFA
🏪 Épicerie Campus Express
📦 Contenu:
•	1 boîte sardines 125g (425F)
•	2 tubes concentré tomate (200F)
•	1 mayonnaise Petit Matin 250ml (500F)
•	Total: 1125F → 1000F (réduction)
📝 Description:
Les basiques pour accompagner un repas simple. Parfait pour sandwich ou sauce rapide.
📍 Campus UL, Bâtiment C | 📞 +228 96 78 90 12 | ⏰ Lun-Ven 7h-20h
PACK CONSERVES 2000F - Kit Sauce Complète
💰 2000 FCFA
🏪 Supermarché la Concorde
📦 Contenu:
•	2 boîtes sardines (850F)
•	3 concentrés tomate 210g (750F)
•	1 cube Maggi 25 cubes (250F)
•	Sel 500g (200F)
•	Total: 2050F → 2000F
📝 Description:
Tout pour faire plusieurs sauces tomate. Conserves de qualité, longue durée.
📍 Akossombo, Atikoumé | 📞 +228 22 21 79 09 | ⏰ Lun-Dim 7h-21h | 🚚 Livraison campus
PACK CONSERVES 5000F - Stock Semaine Étudiant
💰 5000 FCFA
🏪 RAMCO Supermarché
📦 Contenu:
•	Sardines x6 (2550F)
•	1L huile végétale (1600F)
•	Mayonnaise 500ml (800F)
•	3 concentrés 210g (750F)
•	Sel 1kg (300F)
•	Cube Maggi x2 boîtes (500F)
•	Sucre 1kg (1000F)
•	Total: 7500F → 5000F (promo gros)
📝 Description:
Une semaine de produits essentiels. Prix spécial étudiant avec carte campus.
📍 Tokoin Centre | 📞 +228 22 21 50 51 | ⏰ Lun-Dim 8h-22h | 🚚 Livraison GRATUITE > 5000F
 
EXEMPLES DE PACKS - CATÉGORIE FRUITS
PACK FRUITS 500F - Vitamine Express
💰 500 FCFA
🏪 Marché Bio Tokoin - Rayon Fruits
📦 Contenu:
•	4 oranges (200F)
•	3 bananes dessert (125F)
•	6 citrons verts (200F)
•	Total: 525F → 500F
📝 Description:
Fruits frais du jour pour vitamines et jus naturels. Idéal petit-déjeuner!
📍 Tokoin Centre, Marché Kassablanca | 📞 +228 93 45 67 89 | ⏰ Mar-Dim 6h-18h
PACK FRUITS 1000F - Mix Énergisant
💰 1000 FCFA
🏪 Ferme Fraîcheur Lomé
📦 Contenu:
•	1 régime bananes (500F)
•	1 papaye moyenne (600F)
•	Total: 1100F → 1000F
📝 Description:
Fruits mûrs à point, idéaux pour smoothies et desserts sains.
📍 Bè-Klikamé | 📞 +228 95 67 89 01 | 🚚 Livraison GRATUITE campus
PACK FRUITS 2000F - Santé Complète
💰 2000 FCFA
🏪 Marché Bio Tokoin
📦 Contenu:
•	8 oranges (400F)
•	1 régime bananes (500F)
•	1 ananas gros (1000F)
•	2 avocats (600F)
•	Total: 2500F → 2000F
📝 Description:
Mix complet pour 3-4 jours. Fruits tropicaux frais cueillis le jour même.
📍 Tokoin Centre | 📞 +228 93 45 67 89 | ⏰ Mar-Dim 6h-18h
 
PARTIE 2: SECTION « AUTRES BOUTIQUES »
Cette section permet aux étudiants de trouver des produits SPÉCIFIQUES qui ne sont pas dans les packs standards. Chaque boutique est spécialisée dans une catégorie précise.
STRUCTURE DES BOUTIQUES SPÉCIALISÉES
Quand l'étudiant clique sur « Autres Boutiques », il accède à une liste de boutiques classées par spécialité:
1️⃣ BOUTIQUE CÉRÉALES & LÉGUMINEUSES
•	Nom: Grenier d'Afrique

Mil (1200F/bol 2.5kg), Sorgho (1100F/bol), Fonio (1500F/kg), Graines de pistache/egoussi (4000F/kg), Pâte d'arachide (2500F/kg), Couscous de mil thiakry (1000F/kg), Semoule de manioc gari (400F/kg), Attiéké premium (750F/kg), Farine de riz (800F/kg), Poudre de baobab (2000F/kg)


2️⃣ BOUTIQUE BOISSONS & EAU MINÉRALE
•	Nom: Fraîcheur Express

Eau minérale Possotomé 1.5L (350F), Eau en sachet plateau (500F), Jus naturels locaux bouteille (600F), Bissap concentré (800F), Tchoukoutou traditionnel (500F/L), Sodabi local (2000F/L), Coca-Cola canette (400F), Youki Soda (300F), Malta Guinness (500F), Energy drinks (800F)


3️⃣ BOUTIQUE ÉPICES & CONDIMENTS AFRICAINS
•	Nom: Saveurs du Terroir

Potasse/Kanwa (500F/200g), Poudre de crevette (3000F/kg), Piment frais rouge (100F/100g), Piment sec moulu (800F/200g), Cube Adja (150F/boîte), Soumbala (600F/200g), Dawadawa (700F/paquet), Muscade (400F/pièce), Gingembre en poudre (1000F/200g), Ail en poudre (800F/200g), Mélange 4 épices (600F), Curry togolais (500F), Akanwu/Natron (400F/paquet)


4️⃣ BOUTIQUE PRODUITS LAITIERS & FROMAGES
•	Nom: Laiterie Moderne

Wagashi frais (300F/pièce), Yaourt local nature (400F/pot 250ml), Yaourt aux fruits (500F/pot), Lait caillé traditionnel (350F/pot), Fromage Vache qui Rit (800F/boîte), Fromage râpé (1500F/sachet), Crème fraîche (1200F/pot), Beurre doux (1800F/250g), Lait fermenté (600F/bouteille)


 
5️⃣ BOUTIQUE PRODUITS SECS & CONSERVES
•	Nom: Stock & Save

Corned-beef (1200F/boîte), Pilchard (700F/boîte), Luncheon meat (1500F/boîte), Haricots en conserve (450F/boîte), Petits pois (500F/boîte), Maïs doux (600F/boîte), Champignons (800F/boîte), Olives (1200F/pot), Câpres (900F/pot), Cornichons (700F/pot)


6️⃣ BOUTIQUE SNACKS & PÂTISSERIE
•	Nom: Boulangerie Délice

Pain baguette (150F), Pain de mie (700F), Croissants (200F/pièce), Pain chocolat (250F), Gâteaux secs assortis (1500F/kg), Biscuits locaux (100F/sachet), Chin-chin (500F/sachet), Doughnuts (150F/pièce), Muffins (300F/pièce)


7️⃣ BOUTIQUE VIANDES & CHARCUTERIE
•	Nom: Boucherie Excellence

Saucisses de bœuf (2000F/kg), Saucisses de poulet (1800F/kg), Mortadelle (2500F/kg), Jambon (3500F/kg), Bacon (4000F/kg), Viande hachée (2200F/kg), Brochettes préparées (150F/pièce), Poulet mariné prêt à griller (3000F/kg)


8️⃣ BOUTIQUE HERBES & PLANTES MÉDICINALES
•	Nom: Pharmacopée Naturelle

Feuilles de kinkeliba (500F/botte), Citronnelle fraîche (300F/botte), Menthe fraîche (200F/botte), Basilic africain (250F/botte), Feuilles de neem (400F/botte), Gingembre frais bio (300F/250g), Curcuma frais (600F/200g), Ail noir fermenté (1500F/100g)


 
AVANTAGES DU SYSTÈME « AUTRES BOUTIQUES »
•	✅ Produits SPÉCIFIQUES introuvables en packs standards
•	✅ Achat à l'unité possible (pas besoin de pack complet)
•	✅ Boutiques SPÉCIALISÉES = meilleur choix et qualité
•	✅ Prix transparents affichés
•	✅ Chaque boutique a sa SPÉCIALITÉ (céréales, boissons, épices, etc.)
•	✅ Contact direct avec les boutiques
•	✅ Livraison possible selon la boutique
🎓 Cook'Campus - Votre marketplace alimentaire complète ! 🛒


COOK'CAMPUS MARKETPLACE
EXEMPLES DE PACKS D'INGRÉDIENTS
I. CATÉGORIE: VIANDES & POISSONS
PACK VIANDE 500F - Starter Étudiant
💰 Prix: 500 FCFA
🏪 Fournisseur: Boucherie Moderne Lomé
Contenu du pack:
•	150g de bœuf avec os
•	Total: 500 FCFA
Description:
Parfait pour un étudiant qui veut faire une sauce simple. Suffisant pour 1 personne.
Contact:
📍 Adresse: Quartier Agoè, Marché d'Agoè | 📞 Tel: +228 90 12 34 56 | ⏰ Lun-Sam 7h-19h
PACK VIANDE 1000F - Protéines Express
💰 Prix: 1000 FCFA
🏪 Fournisseur: Boucherie Moderne Lomé
Contenu du pack:
•	250g de cuisses de poulet congelées (375F)
•	200g de poisson tilapia congelé (300F)
•	100g crevettes séchées (300F)
•	Total: 1000 FCFA (arrondi avec réduction)
Description:
Mix viande et poisson pour varier les plaisirs. Idéal pour 2 repas différents.
Contact:
📍 Adresse: Quartier Agoè, Marché d'Agoè | 📞 Tel: +228 90 12 34 56 | ⏰ Lun-Sam 7h-19h
PACK VIANDE 2000F - Protéines Complètes
💰 Prix: 2000 FCFA
🏪 Fournisseur: Boucherie du Campus
Contenu du pack:
•	300g de bœuf sans os (720F)
•	200g de poisson fumé (400F)
•	150g de crevettes séchées (450F)
•	200g cuisses poulet (300F)
•	2 œufs (200F)
•	Total: 2070 FCFA → 2000F (promo étudiant)
Description:
Pack complet avec plusieurs types de protéines. Parfait pour 4-5 repas variés.
Contact:
📍 Adresse: Université de Lomé, Entrée principale | 📞 Tel: +228 91 23 45 67 | ⏰ Lun-Ven 8h-18h, Sam 8h-14h
PACK POISSON 3000F - Spécial Sauces
💰 Prix: 3000 FCFA
🏪 Fournisseur: Poissonnerie Atlantique
Contenu du pack:
•	500g poisson tilapia frais de ferme (1250F)
•	300g poisson fumé maquereau (600F)
•	200g crevettes séchées (600F)
•	1 crabe entier (400F)
•	100g poudre de crevette (150F)
•	Total: 3000 FCFA
Description:
Tout ce qu'il faut pour faire plusieurs sauces africaines authentiques (gombo, adémé, pistache).
Contact:
📍 Adresse: Bè-Kpota, près du Port | 📞 Tel: +228 92 34 56 78 | ⏰ Lun-Dim 6h-20h
PACK VIANDE 5000F - Semaine Complète
💰 Prix: 5000 FCFA
🏪 Fournisseur: Boucherie Moderne Lomé
Contenu du pack:
•	1kg bœuf sans os (2400F)
•	500g poulet entier découpé (1125F)
•	300g poisson fumé (600F)
•	200g tripes (240F)
•	6 œufs (600F)
•	Total: 4965F → 5000F
Description:
Une semaine de protéines variées pour un étudiant. Permet de cuisiner 7-8 plats différents.
Contact:
📍 Adresse: Quartier Agoè, Marché d'Agoè | 📞 Tel: +228 90 12 34 56 | ⏰ Lun-Sam 7h-19h | 🚚 Livraison gratuite campus
PACK PROTÉINES 10000F - Deux Semaines Premium
💰 Prix: 10000 FCFA
🏪 Fournisseur: Boucherie du Campus
Contenu du pack:
•	1.5kg bœuf sans os (3600F)
•	1 poulet entier vivant (4500F)
•	500g poisson tilapia frais (1250F)
•	12 œufs (1200F)
•	Total: 10550F → 10000F (réduction fidélité)
Description:
Pour 2 semaines de repas variés et équilibrés. Viande fraîche de qualité supérieure.
Contact:
📍 Adresse: Université de Lomé | 📞 Tel: +228 91 23 45 67 | 🚚 Livraison campus incluse
 
II. CATÉGORIE: LÉGUMES & TUBERCULES
PACK LÉGUMES 500F - Sauce Simple
💰 Prix: 500 FCFA
🏪 Fournisseur: Marché Bio Tokoin
Contenu du pack:
•	2 tomates (100F)
•	1 oignon (75F)
•	1 piment (50F)
•	1 gousse d'ail (50F)
•	1 morceau gingembre (50F)
•	1 botte persil (100F)
•	Total: 425F → 500F
Description:
Les essentiels pour une sauce de base. Ingrédients frais du jour.
Contact:
📍 Adresse: Tokoin Centre, Marché Kassablanca | 📞 Tel: +228 93 45 67 89 | ⏰ Mar-Dim 6h-18h
PACK LÉGUMES 1000F - Sauce Complète
💰 Prix: 1000 FCFA
🏪 Fournisseur: Marché Bio Tokoin
Contenu du pack:
•	4 tomates (200F)
•	2 oignons (150F)
•	2 piments (100F)
•	2 gousses ail (100F)
•	300g gombo (90F)
•	1 aubergine africaine (150F)
•	1 botte épinards (200F)
•	Total: 990F → 1000F
Description:
Parfait pour faire une belle sauce gombo, adémé ou gboma pour 2-3 personnes.
Contact:
📍 Adresse: Tokoin Centre, Marché Kassablanca | 📞 Tel: +228 93 45 67 89 | ⏰ Mar-Dim 6h-18h
PACK TUBERCULES 2000F - Accompagnements Variés
💰 Prix: 2000 FCFA
🏪 Fournisseur: Coopérative Agricole du Nord
Contenu du pack:
•	1kg igname (1000F)
•	2 bananes plantain (500F)
•	500g manioc (250F)
•	Total: 1750F → 2000F (emballage inclus)
Description:
Tous les accompagnements traditionnels : foufou, alloco, koliko. Produits certifiés bio.
Contact:
📍 Adresse: Nyékonakpoè, Route de Kpalimé | 📞 Tel: +228 94 56 78 90 | ⏰ Lun-Sam 7h-17h
PACK LÉGUMES 3000F - Semaine de Légumes
💰 Prix: 3000 FCFA
🏪 Fournisseur: Marché Bio Tokoin
Contenu du pack:
•	8 tomates (400F)
•	3 oignons (225F)
•	500g carottes (200F)
•	1 chou entier (500F)
•	500g gombo (150F)
•	2 bottes épinards (400F)
•	3 aubergines (450F)
•	3 poivrons (600F)
•	Assortiment herbes (75F)
•	Total: 3000F
Description:
Une semaine complète de légumes frais variés. Permet de préparer plusieurs sauces différentes.
Contact:
📍 Adresse: Tokoin Centre, Marché Kassablanca | 📞 Tel: +228 93 45 67 89 | 🚚 Livraison possible (+ 500F)
PACK LÉGUMES + TUBERCULES 5000F - Mix Complet
💰 Prix: 5000 FCFA
🏪 Fournisseur: Ferme Fraîcheur Lomé
Contenu du pack:
•	12 tomates (600F)
•	4 oignons (300F)
•	1kg carottes (400F)
•	2 choux (1000F)
•	1kg igname (1000F)
•	4 bananes plantain (1000F)
•	500g gombo (150F)
•	Assortiment légumes verts (550F)
•	Total: 5000F
Description:
Combinaison de légumes et tubercules pour 10 jours. Idéal pour colocation étudiante.
Contact:
📍 Adresse: Bè-Klikamé, Ferme Fraîcheur | 📞 Tel: +228 95 67 89 01 | 🚚 Livraison GRATUITE Lomé
PACK MARAÎCHER 10000F - Deux Semaines Légumes Premium
💰 Prix: 10000 FCFA
🏪 Fournisseur: Ferme Fraîcheur Lomé
Contenu du pack:
•	20 tomates (1000F)
•	6 oignons (450F)
•	2kg carottes (800F)
•	3 choux (1500F)
•	1kg betteraves (600F)
•	5 concombres (1500F)
•	1kg gombo (300F)
•	4 bottes épinards/adémé (800F)
•	6 aubergines africaines (900F)
•	Assortiment herbes fraîches (200F)
•	2kg igname (2000F)
•	Total: 10050F → 10000F
Description:
Pack premium 2 semaines avec légumes bio certifiés. Livraison réfrigérée incluse.
Contact:
📍 Adresse: Bè-Klikamé | 📞 Tel: +228 95 67 89 01 | 🚚 Livraison GRATUITE + Sac isotherme
 
III. PACKS COMPLETS MULTI-CATÉGORIES
PACK COMPLET 2000F - Riz Au Gras + Poulet
💰 Prix: 2000 FCFA
🏪 Fournisseur: Épicerie Campus Express
Contenu du pack:
•	250g riz (275F)
•	100g cuisses poulet (150F)
•	2 tomates (100F)
•	1 oignon (75F)
•	1 poivron (200F)
•	1 carotte (40F)
•	Concentré tomate petit (100F)
•	Cube Maggi x2 (50F)
•	2 c.à.s huile (160F)
•	Épices (curry, thym, laurier) (150F)
•	1 ail (50F)
•	Sel (50F)
•	Total: 1400F + emballage → 2000F (marge fournisseur)
Description:
Tous les ingrédients pour faire un délicieux riz au gras rouge pour 2 personnes. Recette incluse dans l'app!
Contact:
📍 Adresse: Campus UL, Bâtiment C | 📞 Tel: +228 96 78 90 12 | ⏰ Lun-Ven 7h-20h
PACK COMPLET 3000F - Sauce Gombo Authentique
💰 Prix: 3000 FCFA
🏪 Fournisseur: Épicerie Maman Afrique
Contenu du pack:
•	300g gombo (90F)
•	200g poisson fumé (400F)
•	100g crevettes séchées (300F)
•	200g bœuf (360F)
•	1 aubergine (150F)
•	3 tomates (150F)
•	2 oignons (150F)
•	2 gousses ail (100F)
•	Gingembre (50F)
•	80ml huile de palme (320F)
•	2 piments (100F)
•	1 cube Maggi (25F)
•	Bicarbonate (25F)
•	Sel, poivre (30F)
•	Total: 2250F + service → 3000F
Description:
Pack authentique pour préparer une sauce gombo traditionnelle togolaise pour 3-4 personnes. Instructions détaillées fournies.
Contact:
📍 Adresse: Amoutivé, près Église Catholique | 📞 Tel: +228 97 89 01 23 | ⏰ Lun-Sam 6h-21h
PACK COMPLET 5000F - Attiéké Poisson Grillé
💰 Prix: 5000 FCFA
🏪 Fournisseur: Saveurs d'Afrique
Contenu du pack:
•	500g attiéké (250F)
•	1 gros poisson tilapia (1875F)
•	3 tomates (150F)
•	2 oignons (150F)
•	1 concombre (300F)
•	2 carottes (80F)
•	3 gousses ail (150F)
•	Gingembre (100F)
•	1 botte persil (100F)
•	2 citrons (200F)
•	3 piments (150F)
•	Huile friture 250ml (400F)
•	Mayonnaise (300F)
•	Moutarde (100F)
•	Épices marinade (200F)
•	Sel (50F)
•	Total: 4555F → 5000F
Description:
Le plat complet ivoirien ! Tout ce qu'il faut pour 2-3 personnes avec salade fraîche.
Contact:
📍 Adresse: Bè-Plage, Avenue de la Marina | 📞 Tel: +228 98 90 12 34 | 🚚 Livraison 7j/7
PACK ÉTUDIANT 10000F - Une Semaine de Repas Complets
💰 Prix: 10000 FCFA
🏪 Fournisseur: Supermarché du Campus
Contenu du pack:
•	1kg riz (1100F)
•	500g pâtes (375F)
•	500g bœuf (900F)
•	300g poulet (450F)
•	200g poisson fumé (400F)
•	6 œufs (600F)
•	1kg igname (1000F)
•	8 tomates (400F)
•	3 oignons (225F)
•	500g carottes (200F)
•	2 bottes légumes verts (400F)
•	Huile 500ml (800F)
•	Concentré tomate x3 (300F)
•	Cubes Maggi x10 (250F)
•	Sel, poivre, épices (200F)
•	Pain (500F)
•	Lait en poudre 200g (800F)
•	Sucre 500g (475F)
•	Café (425F)
•	Total: 9800F → 10000F
Description:
Pack complet pour une semaine! Tout ce qu'il faut pour petit-déjeuner, déjeuner et dîner. Recettes suggérées dans l'app.
Contact:
📍 Adresse: Campus UL, Bâtiment Administration | 📞 Tel: +228 99 01 23 45 | ⏰ Lun-Dim 7h-22h | 🚚 Livraison GRATUITE campus
PACK COLOCATION 20000F - Deux Semaines Pour 3-4 Étudiants
💰 Prix: 20000 FCFA
🏪 Fournisseur: Grossiste Assivito
Contenu du pack:
•	2.5kg riz (2750F)
•	1kg pâtes (750F)
•	1.5kg bœuf (2700F)
•	1 poulet entier (4500F)
•	500g poisson (750F)
•	18 œufs (1800F)
•	2kg igname (2000F)
•	6 bananes plantain (1500F)
•	15 tomates (750F)
•	6 oignons (450F)
•	1kg carottes (400F)
•	4 bottes légumes (800F)
•	1L huile (1600F)
•	6 concentrés tomate (600F)
•	20 cubes Maggi (500F)
•	Pain x7 (700F)
•	Conserves (sardines x3) (900F)
•	Épices complètes (450F)
•	Total: 19900F → 20000F
Description:
Idéal pour colocation de 3-4 étudiants. Ingrédients de qualité, emballage hermétique. Plan de repas 14 jours inclus dans l'app!
Contact:
📍 Adresse: Hédzranawoé, Zone Industrielle | 📞 Tel: +228 90 30 26 27 | 🚚 Livraison GRATUITE + Contenants réutilisables
 
IV. SYSTÈME DE GARANTIE DES PRIX
A. Engagement des Fournisseurs
•	Prix fixes garantis pendant 1 mois minimum
•	Qualité contrôlée par Cook'Campus
•	Remboursement si différence de prix > 10%
•	Fraîcheur garantie (légumes < 24h, viandes < 48h)
B. Fonctionnement de la Commande
•	1. L'étudiant sélectionne un pack dans l'app
→ Visualise tous les ingrédients et le prix total
•	2. Validation du pack
→ Paiement via Mobile Money (Tmoney, Flooz) ou à la livraison
•	3. Confirmation du fournisseur
→ SMS/Notification avec heure de retrait ou livraison
•	4. Retrait ou livraison
→ L'étudiant récupère son pack avec QR code de confirmation
•	5. Évaluation
→ Note et commentaire pour améliorer le service
C. Avantages du Système
•	✅ Prix transparents et garantis (pas de mauvaise surprise)
•	✅ Gain de temps (pas besoin de chercher au marché)
•	✅ Budget maîtrisé (savoir exactement combien on dépense)
•	✅ Qualité contrôlée (fournisseurs certifiés)
•	✅ Livraison possible sur campus (pour packs > 5000F)
•	✅ Recettes intégrées dans l'app (savoir quoi cuisiner)
🎓 Cook'Campus - Manger bien, Dépenser moins ! 📱

---

## 📸 STOCKAGE DES IMAGES MARKETPLACE

### Structure des dossiers (serveur backend)

```
server/cookplusserver/src/main/webapp/uploads/packs/
├── viandes/      → Images packs VIANDES & POISSONS
├── legumes/      → Images packs LÉGUMES & TUBERCULES  
├── cereales/     → Images packs CÉRÉALES & FARINES
├── conserves/    → Images packs CONSERVES & ÉPICES
├── fruits/       → Images packs FRUITS & DESSERTS
└── boutiques/    → Logos des 8 boutiques/fournisseurs
```

### Convention de nommage

**Packs :** `pack-{categorie}-{budget}-{type}.jpg`
- Exemples : `pack-viande-500f-starter.jpg`, `pack-legumes-1000f-hebdo.jpg`

**Boutiques :** `{nom-boutique}.jpg`
- Exemples : `boucherie-moderne-lome.jpg`, `marche-bio-tokoin.jpg`

### Spécifications images
- **Format :** JPG (packs) ou PNG (logos avec transparence)
- **Dimensions :** 800x800px à 1200x800px (packs), 400x400px (logos)
- **Poids max :** 500KB (packs), 200KB (logos)

### URLs dans l'API
```
http://localhost:8080/cookplusserver/uploads/packs/viandes/pack-viande-500f-starter.jpg
http://localhost:8080/cookplusserver/uploads/packs/boutiques/boucherie-moderne-lome.jpg
```

### Stockage en BDD (Pack.java, Boutique.java)
```java
pack.setImageUrl("packs/viandes/pack-viande-500f-starter.jpg");
boutique.setLogoUrl("packs/boutiques/boucherie-moderne-lome.jpg");
```

📄 **Voir README complet :** `server/cookplusserver/src/main/webapp/uploads/packs/README.md`

---

Exemple propres de 5 catégories + 8 boutiques de Lomé 2026 


COOK'CAMPUS MARKETPLACE
EXEMPLES DE PACKS D'INGRÉDIENTS
I. CATÉGORIE: VIANDES & POISSONS
PACK VIANDE 500F - Starter Étudiant
💰 Prix: 500 FCFA
🏪 Fournisseur: Boucherie Moderne Lomé
Contenu du pack:
•	150g de bœuf avec os
•	Total: 500 FCFA
Description:
Parfait pour un étudiant qui veut faire une sauce simple. Suffisant pour 1 personne.
Contact:
📍 Adresse: Quartier Agoè, Marché d'Agoè | 📞 Tel: +228 90 12 34 56 | ⏰ Lun-Sam 7h-19h
PACK VIANDE 1000F - Protéines Express
💰 Prix: 1000 FCFA
🏪 Fournisseur: Boucherie Moderne Lomé
Contenu du pack:
•	250g de cuisses de poulet congelées (375F)
•	200g de poisson tilapia congelé (300F)
•	100g crevettes séchées (300F)
•	Total: 1000 FCFA (arrondi avec réduction)
Description:
Mix viande et poisson pour varier les plaisirs. Idéal pour 2 repas différents.
Contact:
📍 Adresse: Quartier Agoè, Marché d'Agoè | 📞 Tel: +228 90 12 34 56 | ⏰ Lun-Sam 7h-19h
PACK VIANDE 2000F - Protéines Complètes
💰 Prix: 2000 FCFA
🏪 Fournisseur: Boucherie du Campus
Contenu du pack:
•	300g de bœuf sans os (720F)
•	200g de poisson fumé (400F)
•	150g de crevettes séchées (450F)
•	200g cuisses poulet (300F)
•	2 œufs (200F)
•	Total: 2070 FCFA → 2000F (promo étudiant)
Description:
Pack complet avec plusieurs types de protéines. Parfait pour 4-5 repas variés.
Contact:
📍 Adresse: Université de Lomé, Entrée principale | 📞 Tel: +228 91 23 45 67 | ⏰ Lun-Ven 8h-18h, Sam 8h-14h
PACK POISSON 3000F - Spécial Sauces
💰 Prix: 3000 FCFA
🏪 Fournisseur: Poissonnerie Atlantique
Contenu du pack:
•	500g poisson tilapia frais de ferme (1250F)
•	300g poisson fumé maquereau (600F)
•	200g crevettes séchées (600F)
•	1 crabe entier (400F)
•	100g poudre de crevette (150F)
•	Total: 3000 FCFA
Description:
Tout ce qu'il faut pour faire plusieurs sauces africaines authentiques (gombo, adémé, pistache).
Contact:
📍 Adresse: Bè-Kpota, près du Port | 📞 Tel: +228 92 34 56 78 | ⏰ Lun-Dim 6h-20h
PACK VIANDE 5000F - Semaine Complète
💰 Prix: 5000 FCFA
🏪 Fournisseur: Boucherie Moderne Lomé
Contenu du pack:
•	1kg bœuf sans os (2400F)
•	500g poulet entier découpé (1125F)
•	300g poisson fumé (600F)
•	200g tripes (240F)
•	6 œufs (600F)
•	Total: 4965F → 5000F
Description:
Une semaine de protéines variées pour un étudiant. Permet de cuisiner 7-8 plats différents.
Contact:
📍 Adresse: Quartier Agoè, Marché d'Agoè | 📞 Tel: +228 90 12 34 56 | ⏰ Lun-Sam 7h-19h | 🚚 Livraison gratuite campus
PACK PROTÉINES 10000F - Deux Semaines Premium
💰 Prix: 10000 FCFA
🏪 Fournisseur: Boucherie du Campus
Contenu du pack:
•	1.5kg bœuf sans os (3600F)
•	1 poulet entier vivant (4500F)
•	500g poisson tilapia frais (1250F)
•	12 œufs (1200F)
•	Total: 10550F → 10000F (réduction fidélité)
Description:
Pour 2 semaines de repas variés et équilibrés. Viande fraîche de qualité supérieure.
Contact:
📍 Adresse: Université de Lomé | 📞 Tel: +228 91 23 45 67 | 🚚 Livraison campus incluse
 
II. CATÉGORIE: LÉGUMES & TUBERCULES
PACK LÉGUMES 500F - Sauce Simple
💰 Prix: 500 FCFA
🏪 Fournisseur: Marché Bio Tokoin
Contenu du pack:
•	2 tomates (100F)
•	1 oignon (75F)
•	1 piment (50F)
•	1 gousse d'ail (50F)
•	1 morceau gingembre (50F)
•	1 botte persil (100F)
•	Total: 425F → 500F
Description:
Les essentiels pour une sauce de base. Ingrédients frais du jour.
Contact:
📍 Adresse: Tokoin Centre, Marché Kassablanca | 📞 Tel: +228 93 45 67 89 | ⏰ Mar-Dim 6h-18h
PACK LÉGUMES 1000F - Sauce Complète
💰 Prix: 1000 FCFA
🏪 Fournisseur: Marché Bio Tokoin
Contenu du pack:
•	4 tomates (200F)
•	2 oignons (150F)
•	2 piments (100F)
•	2 gousses ail (100F)
•	300g gombo (90F)
•	1 aubergine africaine (150F)
•	1 botte épinards (200F)
•	Total: 990F → 1000F
Description:
Parfait pour faire une belle sauce gombo, adémé ou gboma pour 2-3 personnes.
Contact:
📍 Adresse: Tokoin Centre, Marché Kassablanca | 📞 Tel: +228 93 45 67 89 | ⏰ Mar-Dim 6h-18h
PACK TUBERCULES 2000F - Accompagnements Variés
💰 Prix: 2000 FCFA
🏪 Fournisseur: Coopérative Agricole du Nord
Contenu du pack:
•	1kg igname (1000F)
•	2 bananes plantain (500F)
•	500g manioc (250F)
•	Total: 1750F → 2000F (emballage inclus)
Description:
Tous les accompagnements traditionnels : foufou, alloco, koliko. Produits certifiés bio.
Contact:
📍 Adresse: Nyékonakpoè, Route de Kpalimé | 📞 Tel: +228 94 56 78 90 | ⏰ Lun-Sam 7h-17h
PACK LÉGUMES 3000F - Semaine de Légumes
💰 Prix: 3000 FCFA
🏪 Fournisseur: Marché Bio Tokoin
Contenu du pack:
•	8 tomates (400F)
•	3 oignons (225F)
•	500g carottes (200F)
•	1 chou entier (500F)
•	500g gombo (150F)
•	2 bottes épinards (400F)
•	3 aubergines (450F)
•	3 poivrons (600F)
•	Assortiment herbes (75F)
•	Total: 3000F
Description:
Une semaine complète de légumes frais variés. Permet de préparer plusieurs sauces différentes.
Contact:
📍 Adresse: Tokoin Centre, Marché Kassablanca | 📞 Tel: +228 93 45 67 89 | 🚚 Livraison possible (+ 500F)
PACK LÉGUMES + TUBERCULES 5000F - Mix Complet
💰 Prix: 5000 FCFA
🏪 Fournisseur: Ferme Fraîcheur Lomé
Contenu du pack:
•	12 tomates (600F)
•	4 oignons (300F)
•	1kg carottes (400F)
•	2 choux (1000F)
•	1kg igname (1000F)
•	4 bananes plantain (1000F)
•	500g gombo (150F)
•	Assortiment légumes verts (550F)
•	Total: 5000F
Description:
Combinaison de légumes et tubercules pour 10 jours. Idéal pour colocation étudiante.
Contact:
📍 Adresse: Bè-Klikamé, Ferme Fraîcheur | 📞 Tel: +228 95 67 89 01 | 🚚 Livraison GRATUITE Lomé
PACK MARAÎCHER 10000F - Deux Semaines Légumes Premium
💰 Prix: 10000 FCFA
🏪 Fournisseur: Ferme Fraîcheur Lomé
Contenu du pack:
•	20 tomates (1000F)
•	6 oignons (450F)
•	2kg carottes (800F)
•	3 choux (1500F)
•	1kg betteraves (600F)
•	5 concombres (1500F)
•	1kg gombo (300F)
•	4 bottes épinards/adémé (800F)
•	6 aubergines africaines (900F)
•	Assortiment herbes fraîches (200F)
•	2kg igname (2000F)
•	Total: 10050F → 10000F
Description:
Pack premium 2 semaines avec légumes bio certifiés. Livraison réfrigérée incluse.
Contact:
📍 Adresse: Bè-Klikamé | 📞 Tel: +228 95 67 89 01 | 🚚 Livraison GRATUITE + Sac isotherme









COOK'CAMPUS
PACKS MARKETPLACE - 3 CATÉGORIES
Féculents & Céréales | Conserves & Produits de Base | Fruits
 
CATÉGORIE 3: FÉCULENTS & CÉRÉALES
PACK FÉCULENTS 500F - Riz Express
💰 Prix: 500 FCFA
🏪 Fournisseur: Épicerie Campus Express
📦 Contenu du pack:
•	500g riz parfumé (550F)
•	Total: 550F → 500F (réduction étudiant)
📝 Description:
Un demi-kilo de riz parfumé pour 2-3 repas. Idéal pour étudiant solo.
📍 Campus UL, Bâtiment C | 📞 +228 96 78 90 12 | ⏰ Lun-Ven 7h-20h
PACK FÉCULENTS 1000F - Mix Basique
💰 Prix: 1000 FCFA
🏪 Fournisseur: Grenier d'Afrique
📦 Contenu du pack:
•	500g riz parfumé (550F)
•	1 paquet pâtes 200g (150F)
•	250g gari (100F)
•	250g attiéké (125F)
•	Total: 925F → 1000F
📝 Description:
Variété de féculents pour une semaine diversifiée. Riz, pâtes et produits locaux.
📍 Hédzranawoé | 📞 +228 90 30 26 27 | ⏰ Lun-Sam 8h-19h
PACK FÉCULENTS 2000F - Semaine Complète
💰 Prix: 2000 FCFA
🏪 Fournisseur: Supermarché du Campus
📦 Contenu du pack:
•	1kg riz parfumé (1100F)
•	3 paquets pâtes 200g (450F)
•	500g gari (200F)
•	250g couscous mil (250F)
•	Total: 2000F
📝 Description:
Base complète pour une semaine de repas variés. Combine féculents modernes et traditionnels.
📍 Campus UL, Bâtiment Admin | 📞 +228 99 01 23 45 | ⏰ Lun-Dim 7h-22h
PACK FÉCULENTS 3000F - Mix Premium
💰 Prix: 3000 FCFA
🏪 Fournisseur: Grenier d'Afrique
📦 Contenu du pack:
•	2kg riz parfumé (2200F)
•	2 paquets pâtes (300F)
•	500g attiéké premium (375F)
•	1 bol haricots rouges (230F)
•	Total: 3105F → 3000F
📝 Description:
Pack premium avec légumineuses pour protéines végétales. Idéal pour 10 jours.
📍 Hédzranawoé | 📞 +228 90 30 26 27 | 🚚 Livraison campus gratuite
PACK FÉCULENTS 5000F - Deux Semaines Diversifiées
💰 Prix: 5000 FCFA
🏪 Fournisseur: Grossiste Assivito
📦 Contenu du pack:
•	3kg riz parfumé (3300F)
•	5 paquets pâtes (750F)
•	1kg gari (400F)
•	500g attiéké (250F)
•	1 bol haricots (230F)
•	Total: 4930F → 5000F
📝 Description:
Stock complet pour 2 semaines. Mix de céréales et légumineuses pour alimentation équilibrée.
📍 Hédzranawoé, Zone Industrielle | 📞 +228 90 30 26 27 | 🚚 Livraison GRATUITE
PACK FÉCULENTS 10000F - Un Mois Premium
💰 Prix: 10000 FCFA
🏪 Fournisseur: RAMCO Supermarché
📦 Contenu du pack:
•	Sac riz 25kg → 6kg (4800F)
•	10 paquets pâtes (1500F)
•	2kg gari (800F)
•	1kg attiéké (500F)
•	2 bols haricots (460F)
•	2 bols maïs (700F)
•	1kg farine riz (800F)
•	1kg couscous mil (1000F)
•	Total: 10560F → 10000F (tarif gros)
📝 Description:
Stock d'un mois complet pour étudiant ou colocation. Prix de gros avantageux.
📍 Tokoin Centre | 📞 +228 22 21 50 51 | 🚚 Livraison GRATUITE + Contenants
 
CATÉGORIE 4: CONSERVES & PRODUITS DE BASE
PACK CONSERVES 500F - Kit Sauce Rapide
💰 Prix: 500 FCFA
🏪 Fournisseur: Épicerie Campus Express
📦 Contenu du pack:
•	1 boîte sardines 125g (425F)
•	1 tube concentré tomate 70g (100F)
•	Total: 525F → 500F
📝 Description:
Le minimum pour faire une sauce tomate avec sardines. Repas rapide pour étudiant pressé.
📍 Campus UL, Bâtiment C | 📞 +228 96 78 90 12 | ⏰ Lun-Ven 7h-20h
PACK CONSERVES 1000F - Essentiels Semaine
💰 Prix: 1000 FCFA
🏪 Fournisseur: Stock & Save
📦 Contenu du pack:
•	2 boîtes sardines (850F)
•	2 tubes concentré tomate (200F)
•	1 paquet sel 500g (200F)
•	Total: 1250F → 1000F (promo)
📝 Description:
Conserves essentielles pour la semaine. Protéines et base pour sauces.
📍 Nyékonakpoè | 📞 +228 94 00 11 22 | ⏰ Lun-Sam 8h-20h
PACK CONSERVES 2000F - Kit Complet
💰 Prix: 2000 FCFA
🏪 Fournisseur: Supermarché la Concorde
📦 Contenu du pack:
•	3 boîtes sardines (1275F)
•	2 concentrés 210g (500F)
•	1 boîte cubes Maggi (250F)
•	1 mayonnaise 250ml (500F)
•	Total: 2525F → 2000F (réduction)
📝 Description:
Kit complet pour sauces et sandwichs. Conserves de qualité, longue durée.
📍 Akossombo, Atikoumé | 📞 +228 22 21 79 09 | ⏰ Lun-Dim 7h-21h
PACK CONSERVES 3000F - Stock Cuisine
💰 Prix: 3000 FCFA
🏪 Fournisseur: RAMCO Supermarché
📦 Contenu du pack:
•	4 boîtes sardines (1700F)
•	3 concentrés 210g (750F)
•	1L huile végétale (1600F)
•	2 boîtes cubes Maggi (500F)
•	Sel 1kg (300F)
•	Total: 4850F → 3000F (promo gros)
📝 Description:
Stock complet pour cuisiner toute la semaine. Huile incluse pour friture et cuisson.
📍 Tokoin Centre | 📞 +228 22 21 50 51 | ⏰ Lun-Dim 8h-22h
PACK CONSERVES 5000F - Deux Semaines Premium
💰 Prix: 5000 FCFA
🏪 Fournisseur: Stock & Save
📦 Contenu du pack:
•	6 boîtes sardines (2550F)
•	1L huile végétale (1600F)
•	1 mayonnaise 500ml (800F)
•	3 concentrés 210g (750F)
•	Sel 1kg (300F)
•	2 boîtes cubes Maggi (500F)
•	1kg sucre (1000F)
•	Total: 7500F → 5000F (tarif étudiant)
📝 Description:
Stock complet 2 semaines. Prix spécial avec carte campus. Tous les essentiels inclus.
📍 Nyékonakpoè | 📞 +228 94 00 11 22 | 🚚 Livraison GRATUITE campus
PACK CONSERVES 10000F - Un Mois Complet
💰 Prix: 10000 FCFA
🏪 Fournisseur: Grossiste Assivito
📦 Contenu du pack:
•	Carton sardines → 10 boîtes (4200F)
•	Bidon huile 5L partagé (2500F)
•	6 concentrés 210g (1500F)
•	4 boîtes cubes Maggi (1000F)
•	Sel 2kg (600F)
•	Sucre 2kg (2000F)
•	2 mayonnaises 500ml (1600F)
•	Total: 13400F → 10000F (gros)
📝 Description:
Stock d'un mois pour colocation 3-4 étudiants. Prix de gros ultra-avantageux.
📍 Hédzranawoé, Zone Industrielle | 📞 +228 90 30 26 27 | 🚚 Livraison GRATUITE + Stockage
 
CATÉGORIE 5: FRUITS
PACK FRUITS 500F - Vitamine Express
💰 Prix: 500 FCFA
🏪 Fournisseur: Marché Bio Tokoin
📦 Contenu du pack:
•	4 oranges (200F)
•	3 bananes dessert (125F)
•	6 citrons verts (200F)
•	Total: 525F → 500F
📝 Description:
Fruits frais du jour pour vitamines et jus naturels. Idéal petit-déjeuner sain!
📍 Tokoin Centre, Marché Kassablanca | 📞 +228 93 45 67 89 | ⏰ Mar-Dim 6h-18h
PACK FRUITS 1000F - Mix Énergisant
💰 Prix: 1000 FCFA
🏪 Fournisseur: Ferme Fraîcheur Lomé
📦 Contenu du pack:
•	1 régime bananes ~12 pièces (500F)
•	1 papaye moyenne (600F)
•	Total: 1100F → 1000F
📝 Description:
Fruits mûrs à point pour smoothies et desserts sains. Bananes et papaye fraîches.
📍 Bè-Klikamé | 📞 +228 95 67 89 01 | 🚚 Livraison GRATUITE campus
PACK FRUITS 2000F - Santé Complète
💰 Prix: 2000 FCFA
🏪 Fournisseur: Marché Bio Tokoin
📦 Contenu du pack:
•	8 oranges (400F)
•	1 régime bananes (500F)
•	1 ananas gros (1000F)
•	2 avocats (600F)
•	Total: 2500F → 2000F (promo)
📝 Description:
Mix complet pour 3-4 jours. Fruits tropicaux frais cueillis le jour même. Vitamines garanties!
📍 Tokoin Centre | 📞 +228 93 45 67 89 | ⏰ Mar-Dim 6h-18h
PACK FRUITS 3000F - Tropical Premium
💰 Prix: 3000 FCFA
🏪 Fournisseur: Ferme Fraîcheur Lomé
📦 Contenu du pack:
•	12 oranges (600F)
•	1 régime bananes (500F)
•	1 pastèque grande (1500F)
•	2 papayes moyennes (1200F)
•	3 avocats (900F)
•	Total: 4700F → 3000F (réduction bio)
📝 Description:
Sélection premium de fruits tropicaux. Fraîcheur garantie, idéal pour toute la semaine.
📍 Bè-Klikamé | 📞 +228 95 67 89 01 | 🚚 Livraison GRATUITE + Emballage isotherme
PACK FRUITS 5000F - Vitamine Max (10 jours)
💰 Prix: 5000 FCFA
🏪 Fournisseur: Marché Bio Tokoin
📦 Contenu du pack:
•	20 oranges (1000F)
•	2 régimes bananes (1000F)
•	2 ananas gros (2000F)
•	2 pastèques petites (1600F)
•	4 papayes (2400F)
•	6 avocats (1800F)
•	1 noix coco (200F)
•	Total: 10000F → 5000F (promo fidélité)
📝 Description:
Stock fruits 10 jours pour alimentation ultra-saine. Mix tropical complet riche en vitamines.
📍 Tokoin Centre | 📞 +228 93 45 67 89 | 🚚 Livraison bihebdomadaire incluse
PACK FRUITS 10000F - Deux Semaines Santé Premium
💰 Prix: 10000 FCFA
🏪 Fournisseur: Ferme Fraîcheur Lomé - BIO Certifié
📦 Contenu du pack:
•	40 oranges (2000F)
•	3 régimes bananes (1500F)
•	4 ananas (4000F)
•	3 pastèques grandes (4500F)
•	6 papayes (3600F)
•	10 avocats (3000F)
•	6 mangues (900F)
•	2 corossols (3000F)
•	3 noix coco (600F)
•	Total: 23100F → 10000F (bio certifié)
📝 Description:
Pack premium 2 semaines pour colocation. Fruits BIO certifiés, livraison réfrigérée 2×/semaine.
📍 Bè-Klikamé, Ferme Bio | 📞 +228 95 67 89 01 | 🚚 Livraison GRATUITE 2×/semaine + Certification BIO
🎓 Cook'Campus - Manger bien, Dépenser moins ! 📱

STRUCTURE DES BOUTIQUES SPÉCIALISÉES
Quand l'étudiant clique sur « Autres Boutiques », il accède à une liste de boutiques classées par spécialité:
1️⃣ BOUTIQUE CÉRÉALES & LÉGUMINEUSES
•	Nom: Grenier d'Afrique

Mil (1200F/bol 2.5kg), Sorgho (1100F/bol), Fonio (1500F/kg), Graines de pistache/egoussi (4000F/kg), Pâte d'arachide (2500F/kg), Couscous de mil thiakry (1000F/kg), Semoule de manioc gari (400F/kg), Attiéké premium (750F/kg), Farine de riz (800F/kg), Poudre de baobab (2000F/kg)


2️⃣ BOUTIQUE BOISSONS & EAU MINÉRALE
•	Nom: Fraîcheur Express

Eau minérale Possotomé 1.5L (350F), Eau en sachet plateau (500F), Jus naturels locaux bouteille (600F), Bissap concentré (800F), Tchoukoutou traditionnel (500F/L), Sodabi local (2000F/L), Coca-Cola canette (400F), Youki Soda (300F), Malta Guinness (500F), Energy drinks (800F)


3️⃣ BOUTIQUE ÉPICES & CONDIMENTS AFRICAINS
•	Nom: Saveurs du Terroir

Potasse/Kanwa (500F/200g), Poudre de crevette (3000F/kg), Piment frais rouge (100F/100g), Piment sec moulu (800F/200g), Cube Adja (150F/boîte), Soumbala (600F/200g), Dawadawa (700F/paquet), Muscade (400F/pièce), Gingembre en poudre (1000F/200g), Ail en poudre (800F/200g), Mélange 4 épices (600F), Curry togolais (500F), Akanwu/Natron (400F/paquet)


4️⃣ BOUTIQUE PRODUITS LAITIERS & FROMAGES
•	Nom: Laiterie Moderne

Wagashi frais (300F/pièce), Yaourt local nature (400F/pot 250ml), Yaourt aux fruits (500F/pot), Lait caillé traditionnel (350F/pot), Fromage Vache qui Rit (800F/boîte), Fromage râpé (1500F/sachet), Crème fraîche (1200F/pot), Beurre doux (1800F/250g), Lait fermenté (600F/bouteille)


 
5️⃣ BOUTIQUE PRODUITS SECS & CONSERVES
•	Nom: Stock & Save

Corned-beef (1200F/boîte), Pilchard (700F/boîte), Luncheon meat (1500F/boîte), Haricots en conserve (450F/boîte), Petits pois (500F/boîte), Maïs doux (600F/boîte), Champignons (800F/boîte), Olives (1200F/pot), Câpres (900F/pot), Cornichons (700F/pot)


6️⃣ BOUTIQUE SNACKS & PÂTISSERIE
•	Nom: Boulangerie Délice

Pain baguette (150F), Pain de mie (700F), Croissants (200F/pièce), Pain chocolat (250F), Gâteaux secs assortis (1500F/kg), Biscuits locaux (100F/sachet), Chin-chin (500F/sachet), Doughnuts (150F/pièce), Muffins (300F/pièce)


7️⃣ BOUTIQUE VIANDES & CHARCUTERIE
•	Nom: Boucherie Excellence

Saucisses de bœuf (2000F/kg), Saucisses de poulet (1800F/kg), Mortadelle (2500F/kg), Jambon (3500F/kg), Bacon (4000F/kg), Viande hachée (2200F/kg), Brochettes préparées (150F/pièce), Poulet mariné prêt à griller (3000F/kg)


8️⃣ BOUTIQUE HERBES & PLANTES MÉDICINALES
•	Nom: Pharmacopée Naturelle

Feuilles de kinkeliba (500F/botte), Citronnelle fraîche (300F/botte), Menthe fraîche (200F/botte), Basilic africain (250F/botte), Feuilles de neem (400F/botte), Gingembre frais bio (300F/250g), Curcuma frais (600F/200g), Ail noir fermenté (1500F/100g)



