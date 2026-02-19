package com.example.cook_camplus.datas.repositories;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.cook_camplus.datas.api.RetrofitClient;
import com.example.cook_camplus.datas.models.AuthResponse;
import com.example.cook_camplus.datas.models.LoginRequest;
import com.example.cook_camplus.datas.models.SignUpRequest;
import com.example.cook_camplus.datas.models.User;

import java.io.IOException;
import java.security.GeneralSecurityException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    
    private static final String PREFS_NAME = "cook_camplus_secure_prefs";
    private static final String KEY_TOKEN = "auth_token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_NAME = "user_name";
    
    private final Context context;
    private SharedPreferences securePrefs;
    
    public AuthRepository(Context context) {
        this.context = context.getApplicationContext();
        initializeSecurePreferences();
    }
    
    private void initializeSecurePreferences() {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();
            
            securePrefs = EncryptedSharedPreferences.create(
                context,
                PREFS_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            // Fallback to normal SharedPreferences if encryption fails
            securePrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
    }
    
    /**
     * Connexion de l'utilisateur
     */
    public void login(String email, String password, AuthCallback callback) {
        LoginRequest request = new LoginRequest(email, password);
        
        RetrofitClient.getInstance().getApiService()
            .login(request)
            .enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        AuthResponse authResponse = response.body();
                        
                        // Sauvegarder le token et les infos utilisateur
                        saveAuthData(authResponse);
                        callback.onSuccess(authResponse);
                    } else {
                        callback.onError("Email ou mot de passe incorrect");
                    }
                }
                
                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    callback.onError("Erreur de connexion: " + t.getMessage());
                }
            });
    }
    
    /**
     * Inscription d'un nouvel utilisateur
     */
    public void register(String nom, String prenom, String email, String password, AuthCallback callback) {
        SignUpRequest request = new SignUpRequest(nom, prenom, email, password);
        
        if (!request.isValid()) {
            callback.onError(request.getValidationError());
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .register(request)
            .enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        AuthResponse authResponse = response.body();
                        saveAuthData(authResponse);
                        callback.onSuccess(authResponse);
                    } else {
                        callback.onError("Erreur lors de l'inscription");
                    }
                }
                
                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    callback.onError("Erreur réseau: " + t.getMessage());
                }
            });
    }
    
    /**
     * Récupère le profil de l'utilisateur connecté
     */
    public void getProfile(ProfileCallback callback) {
        String token = getToken();
        if (token == null || token.isEmpty()) {
            callback.onError("Non authentifié");
            return;
        }
        
        RetrofitClient.getInstance().getApiService()
            .getProfile(RetrofitClient.formatToken(token))
            .enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Impossible de récupérer le profil");
                    }
                }
                
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    callback.onError("Erreur: " + t.getMessage());
                }
            });
    }
    
    /**
     * Sauvegarde les données d'authentification
     */
    private void saveAuthData(AuthResponse authResponse) {
        SharedPreferences.Editor editor = securePrefs.edit();
        editor.putString(KEY_TOKEN, authResponse.getToken());
        editor.putLong(KEY_USER_ID, authResponse.getId() != null ? authResponse.getId() : -1);
        editor.putString(KEY_USER_EMAIL, authResponse.getEmail());
        editor.putString(KEY_USER_NAME, authResponse.getNom());
        editor.apply();
    }
    
    /**
     * Récupère le token JWT stocké
     */
    public String getToken() {
        return securePrefs.getString(KEY_TOKEN, null);
    }
    
    /**
     * Récupère l'ID de l'utilisateur connecté
     */
    public Long getUserId() {
        long id = securePrefs.getLong(KEY_USER_ID, -1);
        return id != -1 ? id : null;
    }
    
    /**
     * Vérifie si l'utilisateur est connecté
     */
    public boolean isLoggedIn() {
        String token = getToken();
        return token != null && !token.isEmpty();
    }
    
    /**
     * Déconnexion de l'utilisateur
     */
    public void logout() {
        securePrefs.edit().clear().apply();
    }
    
    /**
     * Récupère le nom de l'utilisateur connecté
     */
    public String getUserName() {
        return securePrefs.getString(KEY_USER_NAME, "Utilisateur");
    }
    
    /**
     * Récupère l'email de l'utilisateur connecté
     */
    public String getUserEmail() {
        return securePrefs.getString(KEY_USER_EMAIL, "");
    }
    
    // Callbacks
    public interface AuthCallback {
        void onSuccess(AuthResponse response);
        void onError(String error);
    }
    
    public interface ProfileCallback {
        void onSuccess(User user);
        void onError(String error);
    }
}
