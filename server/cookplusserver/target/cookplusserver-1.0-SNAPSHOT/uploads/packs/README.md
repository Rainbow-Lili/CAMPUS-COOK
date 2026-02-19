# 📦 IMAGES MARKETPLACE - PACKS D'INGRÉDIENTS

Ce dossier contient toutes les images des packs d'ingrédients du marketplace Cook'Campus.

## 📁 STRUCTURE

```
packs/
├── viandes/      → Images packs VIANDES & POISSONS
├── legumes/      → Images packs LÉGUMES & TUBERCULES  
├── cereales/     → Images packs CÉRÉALES & FARINES
├── conserves/    → Images packs CONSERVES & ÉPICES
├── fruits/       → Images packs FRUITS & DESSERTS
└── boutiques/    → Logos des boutiques/fournisseurs
```

## 🖼️ CONVENTION DE NOMMAGE

### Pour les packs :
```
pack-{categorie}-{budget}-{type}.jpg
```

**Exemples :**
- `pack-viande-500f-starter.jpg`
- `pack-viande-1000f-express.jpg`
- `pack-legumes-2000f-hebdo.jpg`
- `pack-cereales-3000f-complet.jpg`

### Pour les boutiques :
```
{nom-boutique}.jpg
```

**Exemples :**
- `boucherie-moderne-lome.jpg`
- `boucherie-campus.jpg`
- `poissonnerie-atlantique.jpg`
- `marche-bio-tokoin.jpg`
- `grenier-afrique.jpg`

## 📏 SPÉCIFICATIONS TECHNIQUES

### Images de packs :
- **Format :** JPG ou PNG
- **Dimensions recommandées :** 800x800px (carré) ou 1200x800px (paysage)
- **Poids maximum :** 500KB par image
- **Qualité :** 80-85% (bon compromis qualité/poids)

### Logos boutiques :
- **Format :** PNG (avec transparence) ou JPG
- **Dimensions recommandées :** 400x400px (carré)
- **Poids maximum :** 200KB
- **Fond :** Transparent (PNG) ou blanc (JPG)

## 🔗 UTILISATION DANS L'API

Les URLs des images seront accessibles via :
```
http://localhost:8080/cookplusserver/uploads/packs/viandes/pack-viande-500f-starter.jpg
http://localhost:8080/cookplusserver/uploads/packs/boutiques/boucherie-moderne-lome.jpg
```

En base de données, on stocke uniquement le chemin relatif :
```java
pack.setImageUrl("packs/viandes/pack-viande-500f-starter.jpg");
boutique.setLogoUrl("packs/boutiques/boucherie-moderne-lome.jpg");
```

## 📦 PACKS À CRÉER (Référence noteMarket.md ligne 855+)

### VIANDES & POISSONS (7 packs)
1. Pack Viande 500F - Starter Étudiant
2. Pack Viande 1000F - Protéines Express
3. Pack Viande 2000F - Protéines Complètes
4. Pack Poisson 3000F - Spécial Sauces
5. Pack Viande 5000F - Semaine Complète
6. Pack Protéines 10000F - Deux Semaines Premium
7. Pack Viande 20000F - Mois Complet (si applicable)

### LÉGUMES & TUBERCULES (7 packs)
1. Pack Légumes 500F - Sauce Simple
2. Pack Légumes 1000F - Hebdo Frais
3. Pack Légumes 2000F - Semaine Variée
4. Pack Légumes 3000F - Mix Complet
5. Pack Légumes 5000F - Quinze Jours
6. Pack Légumes 10000F - Mois Bio
7. Pack Légumes 20000F - Famille

### CÉRÉALES & FARINES (7 packs)
### CONSERVES & ÉPICES (7 packs)
### FRUITS & DESSERTS (7 packs)

### BOUTIQUES (8 logos)
1. Boucherie Moderne Lomé
2. Boucherie du Campus
3. Poissonnerie Atlantique
4. Marché Bio Tokoin
5. Grenier d'Afrique
6. Fraîcheur Express
7. Épices du Monde
8. Fruits & Saveurs

## 🎨 CONSEILS PHOTO

### Pour les packs :
- Fond neutre (blanc, beige clair)
- Éclairage naturel ou ring light
- Produits bien visibles et appétissants
- Disposition aérée et organisée
- Ajouter badge prix/budget sur l'image (optionnel)

### Pour les logos :
- Simple et lisible
- Couleurs cohérentes avec identité boutique
- Vectoriel si possible (SVG → PNG)
- Contraste suffisant pour la lisibilité

## 🔄 MISE À JOUR

Dernière mise à jour : 17 février 2026
Créé par : DAKEY Ahoefa Light
