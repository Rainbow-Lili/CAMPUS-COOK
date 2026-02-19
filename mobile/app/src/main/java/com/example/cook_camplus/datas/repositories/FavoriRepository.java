package com.example.cook_camplus.datas.repositories;

import android.content.Context;

import com.example.cook_camplus.datas.api.RetrofitClient;
import com.example.cook_camplus.datas.models.Favori;
import com.example.cook_camplus.datas.models.Recette;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriRepository {
    
    private final Context context;
    private final AuthRepository authRepository;
    
    public FavoriRepository(Context context) {
        this.context = context.getApplicationContext();
        this.authRepository = new AuthRepository(context);
    }
    
    /**
     * Récupère tous les favoris de l'utilisateur connecté
     */
    public void getFavoris(FavoriListCallback callback) {
        Long userId = authRepository.getUserId();
        String token = authRepository.getToken();
        
        if (userId == null || token == null) {
            callback.onError("Authentification requise");
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .getFavorisByUser(userId, RetrofitClient.formatToken(token))
            .enqueue(new Callback<List<Favori>>() {
                @Override
                public void onResponse(Call<List<Favori>> call, Response<List<Favori>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Erreur lors de la récupération");
                    }
                }
                
                @Override
                public void onFailure(Call<List<Favori>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Ajoute une recette aux favoris
     */
    public void addFavori(Recette recette, FavoriCallback callback) {
        Long userId = authRepository.getUserId();
        String token = authRepository.getToken();
        
        if (userId == null || token == null) {
            callback.onError("Authentification requise");
            return;
        }
        
        Favori favori = new Favori(userId, recette);
        
        RetrofitClient.getInstance().getApiService()
            .addFavori(favori, RetrofitClient.formatToken(token))
            .enqueue(new Callback<Favori>() {
                @Override
                public void onResponse(Call<Favori> call, Response<Favori> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Erreur lors de l'ajout");
                    }
                }
                
                @Override
                public void onFailure(Call<Favori> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Retire une recette des favoris
     */
    public void removeFavori(Long favoriId, DeleteCallback callback) {
        String token = authRepository.getToken();
        
        if (token == null) {
            callback.onError("Authentification requise");
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .removeFavori(favoriId, RetrofitClient.formatToken(token))
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
    
    /**
     * Vérifie si une recette est dans les favoris
     */
    public void checkFavoriExists(Long recetteId, ExistsCallback callback) {
        Long userId = authRepository.getUserId();
        String token = authRepository.getToken();
        
        if (userId == null || token == null) {
            callback.onResult(false);
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .checkFavoriExists(userId, recetteId, RetrofitClient.formatToken(token))
            .enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onResult(response.body());
                    } else {
                        callback.onResult(false);
                    }
                }
                
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    callback.onResult(false);
                }
            });
    }
    
    // Callbacks
    public interface FavoriCallback {
        void onSuccess(Favori favori);
        void onError(String error);
    }
    
    public interface FavoriListCallback {
        void onSuccess(List<Favori> favoris);
        void onError(String error);
    }
    
    public interface DeleteCallback {
        void onSuccess();
        void onError(String error);
    }
    
    public interface ExistsCallback {
        void onResult(boolean exists);
    }
}
