package com.example.cook_camplus.datas.repositories;

import android.content.Context;

import com.example.cook_camplus.datas.api.RetrofitClient;
import com.example.cook_camplus.datas.models.Commentaire;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentaireRepository {
    
    private final Context context;
    private final AuthRepository authRepository;
    
    public CommentaireRepository(Context context) {
        this.context = context.getApplicationContext();
        this.authRepository = new AuthRepository(context);
    }
    
    /**
     * Récupère tous les commentaires d'une recette
     */
    public void getCommentairesByRecette(Long recetteId, CommentaireListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getCommentairesByRecette(recetteId)
            .enqueue(new Callback<List<Commentaire>>() {
                @Override
                public void onResponse(Call<List<Commentaire>> call, Response<List<Commentaire>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Erreur lors de la récupération");
                    }
                }
                
                @Override
                public void onFailure(Call<List<Commentaire>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Ajoute un commentaire
     */
    public void addCommentaire(Commentaire commentaire, CommentaireCallback callback) {
        String token = authRepository.getToken();
        if (token == null) {
            callback.onError("Authentification requise");
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .addCommentaire(commentaire, RetrofitClient.formatToken(token))
            .enqueue(new Callback<Commentaire>() {
                @Override
                public void onResponse(Call<Commentaire> call, Response<Commentaire> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Erreur lors de l'ajout");
                    }
                }
                
                @Override
                public void onFailure(Call<Commentaire> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Supprime un commentaire
     */
    public void deleteCommentaire(Long commentaireId, DeleteCallback callback) {
        String token = authRepository.getToken();
        if (token == null) {
            callback.onError("Authentification requise");
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .deleteCommentaire(commentaireId, RetrofitClient.formatToken(token))
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
    public interface CommentaireCallback {
        void onSuccess(Commentaire commentaire);
        void onError(String error);
    }
    
    public interface CommentaireListCallback {
        void onSuccess(List<Commentaire> commentaires);
        void onError(String error);
    }
    
    public interface DeleteCallback {
        void onSuccess();
        void onError(String error);
    }
}
