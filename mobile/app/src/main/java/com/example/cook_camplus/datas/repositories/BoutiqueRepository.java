package com.example.cook_camplus.datas.repositories;

import android.content.Context;

import com.example.cook_camplus.datas.api.RetrofitClient;
import com.example.cook_camplus.datas.models.Boutique;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoutiqueRepository {
    
    private final Context context;
    
    public BoutiqueRepository(Context context) {
        this.context = context.getApplicationContext();
    }
    
    /**
     * Récupère toutes les boutiques actives
     */
    public void getActiveBoutiques(BoutiqueListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getActiveBoutiques()
            .enqueue(new Callback<List<Boutique>>() {
                @Override
                public void onResponse(Call<List<Boutique>> call, Response<List<Boutique>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Impossible de récupérer les boutiques");
                    }
                }
                
                @Override
                public void onFailure(Call<List<Boutique>> call, Throwable t) {
                    callback.onError("Erreur réseau: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère toutes les boutiques
     */
    public void getAllBoutiques(BoutiqueListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getAllBoutiques()
            .enqueue(new Callback<List<Boutique>>() {
                @Override
                public void onResponse(Call<List<Boutique>> call, Response<List<Boutique>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Erreur lors de la récupération");
                    }
                }
                
                @Override
                public void onFailure(Call<List<Boutique>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère une boutique par son ID
     */
    public void getBoutiqueById(Long id, BoutiqueCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getBoutiqueById(id)
            .enqueue(new Callback<Boutique>() {
                @Override
                public void onResponse(Call<Boutique> call, Response<Boutique> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Boutique introuvable");
                    }
                }
                
                @Override
                public void onFailure(Call<Boutique> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère les boutiques par catégorie
     */
    public void getBoutiquesByCategorie(String categorie, BoutiqueListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getBoutiquesByCategorie(categorie)
            .enqueue(new Callback<List<Boutique>>() {
                @Override
                public void onResponse(Call<List<Boutique>> call, Response<List<Boutique>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Boutique>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère les boutiques par type
     */
    public void getBoutiquesByType(String type, BoutiqueListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getBoutiquesByType(type)
            .enqueue(new Callback<List<Boutique>>() {
                @Override
                public void onResponse(Call<List<Boutique>> call, Response<List<Boutique>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Boutique>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère les boutiques spécialisées
     */
    public void getBoutiquesSpecialisees(BoutiqueListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getBoutiquesSpecialisees()
            .enqueue(new Callback<List<Boutique>>() {
                @Override
                public void onResponse(Call<List<Boutique>> call, Response<List<Boutique>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Boutique>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Recherche des boutiques par nom
     */
    public void searchBoutiques(String nom, BoutiqueListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .searchBoutiques(nom)
            .enqueue(new Callback<List<Boutique>>() {
                @Override
                public void onResponse(Call<List<Boutique>> call, Response<List<Boutique>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Boutique>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère les boutiques les mieux notées
     */
    public void getTopRatedBoutiques(int limit, BoutiqueListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getTopRatedBoutiques(limit)
            .enqueue(new Callback<List<Boutique>>() {
                @Override
                public void onResponse(Call<List<Boutique>> call, Response<List<Boutique>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Boutique>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Sauvegarde (crée ou met à jour) une boutique
     */
    public void saveBoutique(Boutique boutique, String token, BoutiqueCallback callback) {
        String bearer = token.startsWith("Bearer ") ? token : "Bearer " + token;
        if (boutique.getId() != null) {
            // Mise à jour
            RetrofitClient.getInstance().getApiService()
                .updateBoutique(boutique.getId(), boutique, bearer)
                .enqueue(new Callback<Boutique>() {
                    @Override
                    public void onResponse(Call<Boutique> call, Response<Boutique> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onError("Erreur lors de la mise à jour: " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<Boutique> call, Throwable t) {
                        callback.onError("Erreur réseau: " + t.getMessage());
                    }
                });
        } else {
            // Création
            RetrofitClient.getInstance().getApiService()
                .createBoutique(boutique, bearer)
                .enqueue(new Callback<Boutique>() {
                    @Override
                    public void onResponse(Call<Boutique> call, Response<Boutique> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onError("Erreur lors de la création: " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<Boutique> call, Throwable t) {
                        callback.onError("Erreur réseau: " + t.getMessage());
                    }
                });
        }
    }

    // Callbacks
    public interface BoutiqueCallback {
        void onSuccess(Boutique boutique);
        void onError(String error);
    }
    
    public interface BoutiqueListCallback {
        void onSuccess(List<Boutique> boutiques);
        void onError(String error);
    }
}
