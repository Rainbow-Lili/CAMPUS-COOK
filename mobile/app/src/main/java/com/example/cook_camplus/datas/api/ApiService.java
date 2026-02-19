package com.example.cook_camplus.datas.api;

import com.example.cook_camplus.datas.models.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    
    // ==================== AUTHENTICATION ====================
    
    @POST("auth/login")
    Call<AuthResponse> login(@Body LoginRequest loginRequest);
    
    @POST("auth/register")
    Call<AuthResponse> register(@Body SignUpRequest signUpRequest);
    
    @GET("auth/profile")
    Call<User> getProfile(@Header("Authorization") String token);
    
    
    // ==================== RECETTES ====================
    
    @GET("recettes")
    Call<List<Recette>> getAllRecettes(@Header("Authorization") String token);
    
    @GET("recettes/public")
    Call<List<Recette>> getPublicRecettes();
    
    @GET("recettes/{id}")
    Call<Recette> getRecetteById(
        @Path("id") Long id,
        @Header("Authorization") String token
    );
    
    @GET("recettes/search")
    Call<List<Recette>> searchRecettes(
        @Query("nom") String nom,
        @Header("Authorization") String token
    );
    
    @GET("recettes/difficulte/{niveau}")
    Call<List<Recette>> getRecettesByDifficulte(
        @Path("niveau") String niveau,
        @Header("Authorization") String token
    );
    
    @GET("recettes/origine/{origineId}")
    Call<List<Recette>> getRecettesByOrigine(
        @Path("origineId") Long origineId,
        @Header("Authorization") String token
    );
    
    @POST("recettes")
    Call<Recette> createRecette(
        @Body Recette recette,
        @Header("Authorization") String token
    );
    
    @PUT("recettes/{id}")
    Call<Recette> updateRecette(
        @Path("id") Long id,
        @Body Recette recette,
        @Header("Authorization") String token
    );
    
    @DELETE("recettes/{id}")
    Call<Void> deleteRecette(
        @Path("id") Long id,
        @Header("Authorization") String token
    );
    
    @PUT("recettes/{id}/vues")
    Call<Void> incrementerVues(
        @Path("id") Long id
    );
    
    
    // ==================== UTILISATEURS ====================
    
    @GET("utilisateurs")
    Call<List<User>> getAllUsers(@Header("Authorization") String token);
    
    @GET("utilisateurs/{id}")
    Call<User> getUserById(
        @Path("id") Long id,
        @Header("Authorization") String token
    );
    
    @PUT("utilisateurs/{id}")
    Call<User> updateUser(
        @Path("id") Long id,
        @Body User user,
        @Header("Authorization") String token
    );
    
    @DELETE("utilisateurs/{id}")
    Call<Void> deleteUser(
        @Path("id") Long id,
        @Header("Authorization") String token
    );
    
    
    // ==================== FAVORIS ====================
    
    @GET("favoris/utilisateur/{userId}")
    Call<List<Favori>> getFavorisByUser(
        @Path("userId") Long userId,
        @Header("Authorization") String token
    );
    
    @POST("favoris")
    Call<Favori> addFavori(
        @Body Favori favori,
        @Header("Authorization") String token
    );
    
    @DELETE("favoris/{id}")
    Call<Void> removeFavori(
        @Path("id") Long id,
        @Header("Authorization") String token
    );
    
    @GET("favoris/existe")
    Call<Boolean> checkFavoriExists(
        @Query("utilisateurId") Long utilisateurId,
        @Query("recetteId") Long recetteId,
        @Header("Authorization") String token
    );
    
    
    // ==================== COMMENTAIRES ====================
    
    @GET("commentaires/recette/{recetteId}")
    Call<List<Commentaire>> getCommentairesByRecette(
        @Path("recetteId") Long recetteId
    );
    
    @POST("commentaires")
    Call<Commentaire> addCommentaire(
        @Body Commentaire commentaire,
        @Header("Authorization") String token
    );
    
    @PUT("commentaires/{id}")
    Call<Commentaire> updateCommentaire(
        @Path("id") Long id,
        @Body Commentaire commentaire,
        @Header("Authorization") String token
    );
    
    @DELETE("commentaires/{id}")
    Call<Void> deleteCommentaire(
        @Path("id") Long id,
        @Header("Authorization") String token
    );
    
    
    // ==================== NOTES ====================
    
    @GET("notes/recette/{recetteId}")
    Call<List<Note>> getNotesByRecette(
        @Path("recetteId") Long recetteId
    );
    
    @GET("notes/moyenne/{recetteId}")
    Call<Double> getMoyenneNotes(
        @Path("recetteId") Long recetteId
    );
    
    @POST("notes")
    Call<Note> addNote(
        @Body Note note,
        @Header("Authorization") String token
    );
    
    @PUT("notes/{id}")
    Call<Note> updateNote(
        @Path("id") Long id,
        @Body Note note,
        @Header("Authorization") String token
    );
    
    
    // ==================== INGREDIENTS ====================
    
    @GET("ingredients")
    Call<List<Ingredient>> getAllIngredients(@Header("Authorization") String token);
    
    @GET("ingredients/{id}")
    Call<Ingredient> getIngredientById(
        @Path("id") Long id,
        @Header("Authorization") String token
    );
    
    @GET("ingredients/categorie/{categorie}")
    Call<List<Ingredient>> getIngredientsByCategorie(
        @Path("categorie") String categorie,
        @Header("Authorization") String token
    );
    
    @POST("ingredients")
    Call<Ingredient> createIngredient(
        @Body Ingredient ingredient,
        @Header("Authorization") String token
    );
    
    @PUT("ingredients/{id}")
    Call<Ingredient> updateIngredient(
        @Path("id") Long id,
        @Body Ingredient ingredient,
        @Header("Authorization") String token
    );
    
    
    // ==================== ORIGINES ====================
    
    @GET("origines")
    Call<List<Origine>> getAllOrigines();
    
    @GET("origines/{id}")
    Call<Origine> getOrigineById(@Path("id") Long id);
    
    @GET("origines/pays/{pays}")
    Call<List<Origine>> getOriginesByPays(@Path("pays") String pays);
    
    @POST("origines")
    Call<Origine> createOrigine(
        @Body Origine origine,
        @Header("Authorization") String token
    );
    
    
    // ==================== MEDIAS ====================
    
    @GET("medias/recette/{recetteId}")
    Call<List<Media>> getMediasByRecette(@Path("recetteId") Long recetteId);
    
    @POST("medias")
    Call<Media> uploadMedia(
        @Body Media media,
        @Header("Authorization") String token
    );
    
    @DELETE("medias/{id}")
    Call<Void> deleteMedia(
        @Path("id") Long id,
        @Header("Authorization") String token
    );
    
    
    // ==================== VIDEOS ====================
    
    @GET("videos/recette/{recetteId}")
    Call<List<Video>> getVideosByRecette(@Path("recetteId") Long recetteId);
    
    @POST("videos")
    Call<Video> uploadVideo(
        @Body Video video,
        @Header("Authorization") String token
    );
    
    @DELETE("videos/{id}")
    Call<Void> deleteVideo(
        @Path("id") Long id,
        @Header("Authorization") String token
    );
    
    
    // ==================== ETAPES ====================
    
    @GET("etapes/recette/{recetteId}")
    Call<List<Etape>> getEtapesByRecette(@Path("recetteId") Long recetteId);
    
    @POST("etapes")
    Call<Etape> createEtape(
        @Body Etape etape,
        @Header("Authorization") String token
    );
    
    @PUT("etapes/{id}")
    Call<Etape> updateEtape(
        @Path("id") Long id,
        @Body Etape etape,
        @Header("Authorization") String token
    );
    
    @DELETE("etapes/{id}")
    Call<Void> deleteEtape(
        @Path("id") Long id,
        @Header("Authorization") String token
    );
    
    
    // ==================== PACKS ====================
    
    @GET("packs")
    Call<List<Pack>> getAllPacks();
    
    @GET("packs/available")
    Call<List<Pack>> getAvailablePacks();
    
    @GET("packs/{id}")
    Call<Pack> getPackById(@Path("id") Long id);
    
    @GET("packs/categorie/{categorie}")
    Call<List<Pack>> getPacksByCategorie(@Path("categorie") String categorie);
    
    @GET("packs/budget/{budget}")
    Call<List<Pack>> getPacksByBudget(@Path("budget") String budget);
    
    @GET("packs/categorie/{categorie}/budget/{budget}")
    Call<List<Pack>> getPacksByCategorieAndBudget(
        @Path("categorie") String categorie,
        @Path("budget") String budget
    );
    
    @GET("packs/type/{typePack}")
    Call<List<Pack>> getPacksByType(@Path("typePack") String typePack);
    
    @GET("packs/boutique/{boutiqueId}")
    Call<List<Pack>> getPacksByBoutique(@Path("boutiqueId") Long boutiqueId);
    
    @GET("packs/recette/{recetteId}")
    Call<List<Pack>> getPacksByRecette(@Path("recetteId") Long recetteId);
    
    @GET("packs/search")
    Call<List<Pack>> searchPacks(@Query("nom") String nom);
    
    @GET("packs/top-selling")
    Call<List<Pack>> getTopSellingPacks(@Query("limit") Integer limit);
    
    @GET("packs/top-rated")
    Call<List<Pack>> getTopRatedPacks(@Query("limit") Integer limit);
    
    
    // ==================== BOUTIQUES ====================
    
    @GET("boutiques")
    Call<List<Boutique>> getAllBoutiques();
    
    @GET("boutiques/active")
    Call<List<Boutique>> getActiveBoutiques();
    
    @GET("boutiques/{id}")
    Call<Boutique> getBoutiqueById(@Path("id") Long id);
    
    @GET("boutiques/categorie/{categorie}")
    Call<List<Boutique>> getBoutiquesByCategorie(@Path("categorie") String categorie);
    
    @GET("boutiques/type/{type}")
    Call<List<Boutique>> getBoutiquesByType(@Path("type") String type);
    
    @GET("boutiques/specialisees")
    Call<List<Boutique>> getBoutiquesSpecialisees();
    
    @GET("boutiques/search")
    Call<List<Boutique>> searchBoutiques(@Query("nom") String nom);
    
    @GET("boutiques/top-rated")
    Call<List<Boutique>> getTopRatedBoutiques(@Query("limit") Integer limit);

    @PUT("boutiques/{id}")
    Call<Boutique> updateBoutique(
        @Path("id") Long id,
        @Body Boutique boutique,
        @Header("Authorization") String token
    );

    @POST("boutiques")
    Call<Boutique> createBoutique(
        @Body Boutique boutique,
        @Header("Authorization") String token
    );
}
