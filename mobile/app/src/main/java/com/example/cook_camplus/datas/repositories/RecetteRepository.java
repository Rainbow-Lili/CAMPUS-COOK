package com.example.cook_camplus.datas.repositories;

import android.content.Context;

import com.example.cook_camplus.datas.api.RetrofitClient;
import com.example.cook_camplus.datas.models.Recette;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecetteRepository {
    
    private final Context context;
    private final AuthRepository authRepository;
    
    public RecetteRepository(Context context) {
        this.context = context.getApplicationContext();
        this.authRepository = new AuthRepository(context);
    }
    
    /**
     * Récupère toutes les recettes publiques (sans authentification)
     */
    public void getPublicRecettes(RecetteListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getPublicRecettes()
            .enqueue(new Callback<List<Recette>>() {
                @Override
                public void onResponse(Call<List<Recette>> call, Response<List<Recette>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Impossible de récupérer les recettes");
                    }
                }
                
                @Override
                public void onFailure(Call<List<Recette>> call, Throwable t) {
                    callback.onError("Erreur réseau: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère toutes les recettes (authentification requise)
     */
    public void getAllRecettes(RecetteListCallback callback) {
        String token = authRepository.getToken();
        if (token == null) {
            callback.onError("Authentification requise");
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .getAllRecettes(RetrofitClient.formatToken(token))
            .enqueue(new Callback<List<Recette>>() {
                @Override
                public void onResponse(Call<List<Recette>> call, Response<List<Recette>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Erreur lors de la récupération");
                    }
                }
                
                @Override
                public void onFailure(Call<List<Recette>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère une recette par son ID
     */
    public void getRecetteById(Long id, RecetteCallback callback) {
        String token = authRepository.getToken();
        if (token == null) {
            callback.onError("Authentification requise");
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .getRecetteById(id, RetrofitClient.formatToken(token))
            .enqueue(new Callback<Recette>() {
                @Override
                public void onResponse(Call<Recette> call, Response<Recette> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Recette introuvable");
                    }
                }
                
                @Override
                public void onFailure(Call<Recette> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Recherche des recettes par nom
     */
    public void searchRecettes(String nom, RecetteListCallback callback) {
        String token = authRepository.getToken();
        if (token == null) {
            callback.onError("Authentification requise");
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .searchRecettes(nom, RetrofitClient.formatToken(token))
            .enqueue(new Callback<List<Recette>>() {
                @Override
                public void onResponse(Call<List<Recette>> call, Response<List<Recette>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>()); // Retourne liste vide si rien trouvé
                    }
                }
                
                @Override
                public void onFailure(Call<List<Recette>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Filtre les recettes par difficulté
     */
    public void getRecettesByDifficulte(String difficulte, RecetteListCallback callback) {
        String token = authRepository.getToken();
        if (token == null) {
            callback.onError("Authentification requise");
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .getRecettesByDifficulte(difficulte, RetrofitClient.formatToken(token))
            .enqueue(new Callback<List<Recette>>() {
                @Override
                public void onResponse(Call<List<Recette>> call, Response<List<Recette>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Recette>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Filtre les recettes par origine
     */
    public void getRecettesByOrigine(Long origineId, RecetteListCallback callback) {
        String token = authRepository.getToken();
        if (token == null) {
            callback.onError("Authentification requise");
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .getRecettesByOrigine(origineId, RetrofitClient.formatToken(token))
            .enqueue(new Callback<List<Recette>>() {
                @Override
                public void onResponse(Call<List<Recette>> call, Response<List<Recette>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(new ArrayList<>());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Recette>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Incrémente le nombre de vues d'une recette
     */
    public void incrementerVues(Long recetteId) {
        RetrofitClient.getInstance().getApiService()
            .incrementerVues(recetteId)
            .enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    // Silencieux - pas besoin de notifier
                }
                
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Erreur silencieuse
                }
            });
    }
    
    /**
     * Crée une nouvelle recette
     */
    public void createRecette(Recette recette, RecetteCallback callback) {
        String token = authRepository.getToken();
        if (token == null) {
            callback.onError("Authentification requise");
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .createRecette(recette, RetrofitClient.formatToken(token))
            .enqueue(new Callback<Recette>() {
                @Override
                public void onResponse(Call<Recette> call, Response<Recette> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Erreur lors de la création");
                    }
                }
                
                @Override
                public void onFailure(Call<Recette> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Met à jour une recette existante
     */
    public void updateRecette(Long id, Recette recette, RecetteCallback callback) {
        String token = authRepository.getToken();
        if (token == null) {
            callback.onError("Authentification requise");
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .updateRecette(id, recette, RetrofitClient.formatToken(token))
            .enqueue(new Callback<Recette>() {
                @Override
                public void onResponse(Call<Recette> call, Response<Recette> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Erreur lors de la mise à jour");
                    }
                }
                
                @Override
                public void onFailure(Call<Recette> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Supprime une recette
     */
    public void deleteRecette(Long id, DeleteCallback callback) {
        String token = authRepository.getToken();
        if (token == null) {
            callback.onError("Authentification requise");
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .deleteRecette(id, RetrofitClient.formatToken(token))
            .enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onError("Erreur lors de la suppression");
                    }
                }
                
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    // Callbacks
    public interface RecetteCallback {
        void onSuccess(Recette recette);
        void onError(String error);
    }
    
    public interface RecetteListCallback {
        void onSuccess(List<Recette> recettes);
        void onError(String error);
    }
    
    public interface DeleteCallback {
        void onSuccess();
        void onError(String error);
    }
}
