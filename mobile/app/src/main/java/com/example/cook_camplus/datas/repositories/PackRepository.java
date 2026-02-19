package com.example.cook_camplus.datas.repositories;

import android.content.Context;

import com.example.cook_camplus.datas.api.RetrofitClient;
import com.example.cook_camplus.datas.models.Pack;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackRepository {
    
    private final Context context;
    
    public PackRepository(Context context) {
        this.context = context.getApplicationContext();
    }
    
    /**
     * Récupère tous les packs disponibles
     */
    public void getAvailablePacks(PackListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getAvailablePacks()
            .enqueue(new Callback<List<Pack>>() {
                @Override
                public void onResponse(Call<List<Pack>> call, Response<List<Pack>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Impossible de récupérer les packs");
                    }
                }
                
                @Override
                public void onFailure(Call<List<Pack>> call, Throwable t) {
                    callback.onError("Erreur réseau: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère tous les packs
     */
    public void getAllPacks(PackListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getAllPacks()
            .enqueue(new Callback<List<Pack>>() {
                @Override
                public void onResponse(Call<List<Pack>> call, Response<List<Pack>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Erreur lors de la récupération");
                    }
                }
                
                @Override
                public void onFailure(Call<List<Pack>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère un pack par son ID
     */
    public void getPackById(Long id, PackCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getPackById(id)
            .enqueue(new Callback<Pack>() {
                @Override
                public void onResponse(Call<Pack> call, Response<Pack> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Pack introuvable");
                    }
                }
                
                @Override
                public void onFailure(Call<Pack> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère les packs par catégorie
     */
    public void getPacksByCategorie(String categorie, PackListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getPacksByCategorie(categorie)
            .enqueue(new Callback<List<Pack>>() {
                @Override
                public void onResponse(Call<List<Pack>> call, Response<List<Pack>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Pack>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère les packs par budget
     */
    public void getPacksByBudget(String budget, PackListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getPacksByBudget(budget)
            .enqueue(new Callback<List<Pack>>() {
                @Override
                public void onResponse(Call<List<Pack>> call, Response<List<Pack>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Pack>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère les packs par catégorie et budget
     */
    public void getPacksByCategorieAndBudget(String categorie, String budget, PackListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getPacksByCategorieAndBudget(categorie, budget)
            .enqueue(new Callback<List<Pack>>() {
                @Override
                public void onResponse(Call<List<Pack>> call, Response<List<Pack>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Pack>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère les packs par type
     */
    public void getPacksByType(String typePack, PackListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getPacksByType(typePack)
            .enqueue(new Callback<List<Pack>>() {
                @Override
                public void onResponse(Call<List<Pack>> call, Response<List<Pack>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Pack>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère les packs d'une boutique
     */
    public void getPacksByBoutique(Long boutiqueId, PackListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getPacksByBoutique(boutiqueId)
            .enqueue(new Callback<List<Pack>>() {
                @Override
                public void onResponse(Call<List<Pack>> call, Response<List<Pack>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Pack>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère les packs pour une recette
     */
    public void getPacksByRecette(Long recetteId, PackListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getPacksByRecette(recetteId)
            .enqueue(new Callback<List<Pack>>() {
                @Override
                public void onResponse(Call<List<Pack>> call, Response<List<Pack>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Pack>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Recherche des packs par nom
     */
    public void searchPacks(String nom, PackListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .searchPacks(nom)
            .enqueue(new Callback<List<Pack>>() {
                @Override
                public void onResponse(Call<List<Pack>> call, Response<List<Pack>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Pack>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère les packs les plus vendus
     */
    public void getTopSellingPacks(int limit, PackListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getTopSellingPacks(limit)
            .enqueue(new Callback<List<Pack>>() {
                @Override
                public void onResponse(Call<List<Pack>> call, Response<List<Pack>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Pack>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère les packs les mieux notés
     */
    public void getTopRatedPacks(int limit, PackListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getTopRatedPacks(limit)
            .enqueue(new Callback<List<Pack>>() {
                @Override
                public void onResponse(Call<List<Pack>> call, Response<List<Pack>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Pack>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    // Callbacks
    public interface PackCallback {
        void onSuccess(Pack pack);
        void onError(String error);
    }
    
    public interface PackListCallback {
        void onSuccess(List<Pack> packs);
        void onError(String error);
    }
}
