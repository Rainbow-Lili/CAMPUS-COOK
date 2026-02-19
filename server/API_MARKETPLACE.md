# API Marketplace - Documentation des Endpoints

## 📦 Fonctionnalité de Marketplace pour Fournisseurs

Cette API permet aux fournisseurs de créer des boutiques et de vendre des packs d'ingrédients aux étudiants.

---

## 🏪 Endpoints Boutiques

### GET `/rs/boutiques`
Récupère toutes les boutiques.

**Réponse :** Array de Boutiques

---

### GET `/rs/boutiques/active`
Récupère uniquement les boutiques actives.

**Réponse :** Array de Boutiques actives

---

### GET `/rs/boutiques/top-rated?limit=10`
Récupère les boutiques les mieux notées.

**Query Params :**
- `limit` (optionnel) : Nombre de résultats (défaut: 10)

**Réponse :** Array de Boutiques triées par note

---

### GET `/rs/boutiques/{id}`
Récupère une boutique par son ID.

**Path Params :**
- `id` : ID de la boutique

**Réponse :** Objet Boutique

---

### GET `/rs/boutiques/fournisseur/{fournisseurId}`
Récupère la boutique d'un fournisseur spécifique.

**Path Params :**
- `fournisseurId` : ID de l'utilisateur fournisseur

**Réponse :** Objet Boutique

---

### GET `/rs/boutiques/search?nom=marche`
Recherche des boutiques par nom.

**Query Params :**
- `nom` (requis) : Terme de recherche

**Réponse :** Array de Boutiques correspondantes

---

### POST `/rs/boutiques`
Crée une nouvelle boutique (réservé aux fournisseurs).

**Headers :**
- `Authorization: Bearer {token}`

**Body :**
```json
{
  "nom": "Marché Bio Campus",
  "description": "Vos ingrédients frais livrés sur le campus",
  "adresse": "Campus Universitaire, Lomé",
  "telephone": "+228 90 12 34 56",
  "email": "contact@marchebio.tg",
  "logoUrl": "logo_marche.png",
  "horairesOuverture": "Lun-Ven: 8h-18h, Sam: 9h-14h",
  "fournisseur": {
    "id": 3
  }
}
```

**Réponse :** Boutique créée (201)

---

### PUT `/rs/boutiques/{id}`
Met à jour une boutique existante (propriétaire ou admin).

**Headers :**
- `Authorization: Bearer {token}`

**Path Params :**
- `id` : ID de la boutique

**Body :** Champs à mettre à jour (partiels acceptés)

**Réponse :** Boutique mise à jour

---

### DELETE `/rs/boutiques/{id}`
Supprime une boutique (admin uniquement).

**Headers :**
- `Authorization: Bearer {token}`

**Path Params :**
- `id` : ID de la boutique

**Réponse :** 204 No Content

---

### PUT `/rs/boutiques/{id}/toggle-active`
Active/désactive une boutique.

**Headers :**
- `Authorization: Bearer {token}`

**Path Params :**
- `id` : ID de la boutique

**Réponse :** Message de confirmation

---

### GET `/rs/boutiques/stats/count-active`
Compte le nombre de boutiques actives.

**Réponse :**
```json
{
  "count": 15
}
```

---

## 📦 Endpoints Packs

### GET `/rs/packs`
Récupère tous les packs.

**Réponse :** Array de Packs

---

### GET `/rs/packs/available`
Récupère uniquement les packs disponibles (en stock).

**Réponse :** Array de Packs disponibles

---

### GET `/rs/packs/top-selling?limit=10`
Récupère les packs les plus vendus.

**Query Params :**
- `limit` (optionnel) : Nombre de résultats (défaut: 10)

**Réponse :** Array de Packs triés par ventes

---

### GET `/rs/packs/top-rated?limit=10`
Récupère les packs les mieux notés.

**Query Params :**
- `limit` (optionnel) : Nombre de résultats (défaut: 10)

**Réponse :** Array de Packs triés par note

---

### GET `/rs/packs/{id}`
Récupère un pack par son ID.

**Path Params :**
- `id` : ID du pack

**Réponse :** Objet Pack

---

### GET `/rs/packs/boutique/{boutiqueId}`
Récupère tous les packs d'une boutique.

**Path Params :**
- `boutiqueId` : ID de la boutique

**Réponse :** Array de Packs

---

### GET `/rs/packs/recette/{recetteId}`
Récupère les packs associés à une recette spécifique.

**Path Params :**
- `recetteId` : ID de la recette

**Réponse :** Array de Packs

---

### GET `/rs/packs/search?nom=gombo`
Recherche des packs par nom.

**Query Params :**
- `nom` (requis) : Terme de recherche

**Réponse :** Array de Packs correspondants

---

### GET `/rs/packs/price-range?min=5000&max=15000`
Recherche des packs par plage de prix.

**Query Params :**
- `min` (requis) : Prix minimum
- `max` (requis) : Prix maximum

**Réponse :** Array de Packs dans la plage

---

### POST `/rs/packs/boutique/{boutiqueId}`
Crée un nouveau pack dans une boutique.

**Headers :**
- `Authorization: Bearer {token}`

**Path Params :**
- `boutiqueId` : ID de la boutique

**Body :**
```json
{
  "nom": "Pack Gombo Sauce",
  "description": "Tous les ingrédients pour 4 personnes",
  "prix": 7500.0,
  "stock": 20,
  "disponible": true,
  "imageUrl": "pack_gombo.png",
  "recetteId": 2
}
```

**Réponse :** Pack créé (201)

---

### PUT `/rs/packs/{id}`
Met à jour un pack existant (propriétaire ou admin).

**Headers :**
- `Authorization: Bearer {token}`

**Path Params :**
- `id` : ID du pack

**Body :** Champs à mettre à jour

**Réponse :** Pack mis à jour

---

### DELETE `/rs/packs/{id}`
Supprime un pack (propriétaire ou admin).

**Headers :**
- `Authorization: Bearer {token}`

**Path Params :**
- `id` : ID du pack

**Réponse :** 204 No Content

---

### PUT `/rs/packs/{id}/toggle-disponibilite`
Active/désactive la disponibilité d'un pack.

**Headers :**
- `Authorization: Bearer {token}`

**Path Params :**
- `id` : ID du pack

**Réponse :** Message de confirmation

---

### PUT `/rs/packs/{id}/stock?quantite=10`
Modifie le stock d'un pack (ajout ou retrait).

**Headers :**
- `Authorization: Bearer {token}`

**Path Params :**
- `id` : ID du pack

**Query Params :**
- `quantite` (requis) : Quantité à ajouter (positif) ou retirer (négatif)

**Réponse :** Message de confirmation

---

### POST `/rs/packs/{id}/purchase?quantite=1`
Traite un achat de pack (décrémente stock, incrémente ventes).

**Headers :**
- `Authorization: Bearer {token}`

**Path Params :**
- `id` : ID du pack

**Query Params :**
- `quantite` (requis) : Quantité achetée

**Réponse :** Message de confirmation

---

## 🛒 Endpoints Contenu Packs

### GET `/rs/contenu-packs/{id}`
Récupère un contenu de pack par son ID.

**Path Params :**
- `id` : ID du contenu

**Réponse :** Objet ContenuPack

---

### GET `/rs/contenu-packs/pack/{packId}`
Récupère tous les ingrédients d'un pack.

**Path Params :**
- `packId` : ID du pack

**Réponse :** Array de ContenuPack

---

### GET `/rs/contenu-packs/ingredient/{ingredientId}`
Récupère tous les packs contenant un ingrédient.

**Path Params :**
- `ingredientId` : ID de l'ingrédient

**Réponse :** Array de ContenuPack

---

### POST `/rs/contenu-packs/pack/{packId}/ingredient/{ingredientId}?quantite=500&unite=g`
Ajoute un ingrédient à un pack.

**Headers :**
- `Authorization: Bearer {token}`

**Path Params :**
- `packId` : ID du pack
- `ingredientId` : ID de l'ingrédient

**Query Params :**
- `quantite` (requis) : Quantité de l'ingrédient
- `unite` (optionnel) : Unité de mesure (défaut: "g")

**Réponse :** ContenuPack créé (201)

---

### PUT `/rs/contenu-packs/{id}`
Met à jour la quantité/unité d'un ingrédient dans un pack.

**Headers :**
- `Authorization: Bearer {token}`

**Path Params :**
- `id` : ID du contenu

**Body :**
```json
{
  "quantite": 600,
  "unite": "g",
  "ordre": 1
}
```

**Réponse :** ContenuPack mis à jour

---

### DELETE `/rs/contenu-packs/{id}`
Supprime un ingrédient d'un pack.

**Headers :**
- `Authorization: Bearer {token}`

**Path Params :**
- `id` : ID du contenu

**Réponse :** 204 No Content

---

### DELETE `/rs/contenu-packs/pack/{packId}`
Supprime tous les ingrédients d'un pack.

**Headers :**
- `Authorization: Bearer {token}`

**Path Params :**
- `packId` : ID du pack

**Réponse :** 204 No Content

---

### GET `/rs/contenu-packs/pack/{packId}/count`
Compte le nombre d'ingrédients dans un pack.

**Path Params :**
- `packId` : ID du pack

**Réponse :**
```json
{
  "count": 5
}
```

---

### GET `/rs/contenu-packs/pack/{packId}/total-price`
Calcule le coût total des ingrédients d'un pack.

**Path Params :**
- `packId` : ID du pack

**Réponse :**
```json
{
  "totalPrice": 6200.50
}
```

---

### GET `/rs/contenu-packs/pack/{packId}/margin`
Calcule la marge d'un pack.

**Path Params :**
- `packId` : ID du pack

**Réponse :**
```json
{
  "margin": 1299.50,
  "marginPercentage": 20.95
}
```

---

## 🔐 Sécurité et Permissions

### Rôles disponibles :
- **ETUDIANT** : Peut consulter et acheter des packs
- **FOURNISSEUR** : Peut créer/gérer sa boutique et ses packs
- **ADMIN** : Accès complet

### Règles d'accès :
- Lecture (GET) : Public ou authentifié selon l'endpoint
- Création boutique (POST) : FOURNISSEUR ou ADMIN
- Modification boutique/packs (PUT/DELETE) : Propriétaire ou ADMIN
- Achat (POST /purchase) : Utilisateur authentifié

---

## 📊 Schéma de Données

### Boutique
```json
{
  "id": 1,
  "nom": "Marché Bio Campus",
  "description": "...",
  "adresse": "Campus Lomé",
  "telephone": "+228 90 12 34 56",
  "email": "contact@marchebio.tg",
  "logoUrl": "logo.png",
  "horairesOuverture": "Lun-Ven: 8h-18h",
  "active": true,
  "noteMoyenne": 4.5,
  "nombreAvis": 23,
  "dateCreation": "2026-02-17T10:00:00",
  "dateModification": null,
  "fournisseur": {
    "id": 3,
    "nom": "Kofi",
    "prenom": "Jean"
  }
}
```

### Pack
```json
{
  "id": 1,
  "nom": "Pack Gombo Sauce",
  "description": "Ingrédients pour 4 personnes",
  "prix": 7500.0,
  "stock": 20,
  "disponible": true,
  "imageUrl": "pack_gombo.png",
  "recetteId": 2,
  "noteMoyenne": 4.7,
  "nombreAvis": 15,
  "nombreVentes": 45,
  "dateCreation": "2026-02-17T10:30:00",
  "boutique": {
    "id": 1,
    "nom": "Marché Bio Campus"
  }
}
```

### ContenuPack
```json
{
  "id": 1,
  "quantite": 500.0,
  "unite": "g",
  "ordre": 1,
  "pack": {
    "id": 1,
    "nom": "Pack Gombo Sauce"
  },
  "ingredient": {
    "id": 1,
    "nom": "Gombo",
    "prixUnitaire": 2.5
  }
}
```

---

## 🚀 Prochaines étapes

1. **Exécuter le script SQL** : `psql -U postgres -d cookcamplus < create_boutique_tables.sql`
2. **Redémarrer le serveur** pour charger les nouveaux endpoints
3. **Tester avec Postman** ou l'outil de votre choix
4. **Créer des utilisateurs fournisseurs** via l'API d'authentification
5. **Créer des boutiques** et ajouter des packs

---

## 💡 Exemples d'utilisation

### Scénario 1 : Un fournisseur crée sa boutique
```bash
POST /rs/boutiques
Authorization: Bearer {token_fournisseur}

{
  "nom": "Épicerie du Campus",
  "description": "Produits frais et locaux",
  "adresse": "Résidence universitaire A",
  "telephone": "+228 91 11 11 11",
  "fournisseur": { "id": 5 }
}
```

### Scénario 2 : Ajout d'un pack
```bash
POST /rs/packs/boutique/1
Authorization: Bearer {token_fournisseur}

{
  "nom": "Pack Riz Sauce Arachide",
  "prix": 6000,
  "stock": 30,
  "recetteId": 8
}
```

### Scénario 3 : Ajout d'ingrédients au pack
```bash
POST /rs/contenu-packs/pack/1/ingredient/5?quantite=1000&unite=g
POST /rs/contenu-packs/pack/1/ingredient/12?quantite=200&unite=g
POST /rs/contenu-packs/pack/1/ingredient/3?quantite=150&unite=g
```

### Scénario 4 : Un étudiant achète un pack
```bash
POST /rs/packs/1/purchase?quantite=1
Authorization: Bearer {token_etudiant}
```
