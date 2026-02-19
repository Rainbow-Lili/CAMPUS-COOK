package com.example.cook_camplus.datas.repositories;

import android.content.Context;

import com.example.cook_camplus.datas.api.RetrofitClient;
import com.example.cook_camplus.datas.models.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteRepository {
    
    private final Context context;
    private final AuthRepository authRepository;
    
    public NoteRepository(Context context) {
        this.context = context.getApplicationContext();
        this.authRepository = new AuthRepository(context);
    }
    
    /**
     * Récupère toutes les notes d'une recette
     */
    public void getNotesByRecette(Long recetteId, NoteListCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getNotesByRecette(recetteId)
            .enqueue(new Callback<List<Note>>() {
                @Override
                public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Erreur lors de la récupération");
                    }
                }
                
                @Override
                public void onFailure(Call<List<Note>> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère la moyenne des notes d'une recette
     */
    public void getMoyenneNotes(Long recetteId, MoyenneCallback callback) {
        RetrofitClient.getInstance().getApiService()
            .getMoyenneNotes(recetteId)
            .enqueue(new Callback<Double>() {
                @Override
                public void onResponse(Call<Double> call, Response<Double> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onSuccess(0.0); // Retourne 0 si pas de notes
                    }
                }
                
                @Override
                public void onFailure(Call<Double> call, Throwable t) {
                    callback.onSuccess(0.0);
                }
            });
    }
    
    /**
     * Ajoute ou met à jour une note
     */
    public void addOrUpdateNote(Long recetteId, int valeur, NoteCallback callback) {
        String token = authRepository.getToken();
        Long userId = authRepository.getUserId();
        
        if (token == null || userId == null) {
            callback.onError("Authentification requise");
            return;
        }
        
        Note note = new Note(valeur, userId, recetteId);
        
        if (!note.isValid()) {
            callback.onError("La note doit être entre 1 et 5");
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .addNote(note, RetrofitClient.formatToken(token))
            .enqueue(new Callback<Note>() {
                @Override
                public void onResponse(Call<Note> call, Response<Note> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Erreur lors de l'ajout de la note");
                    }
                }
                
                @Override
                public void onFailure(Call<Note> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    // Callbacks
    public interface NoteCallback {
        void onSuccess(Note note);
        void onError(String error);
    }
    
    public interface NoteListCallback {
        void onSuccess(List<Note> notes);
        void onError(String error);
    }
    
    public interface MoyenneCallback {
        void onSuccess(Double moyenne);
    }
}
