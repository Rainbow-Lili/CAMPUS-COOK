package com.example.cook_camplus.datas.api;

import com.example.cook_camplus.datas.connection.Connection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    
    private static RetrofitClient instance;
    private final Retrofit retrofit;
    private final ApiService apiService;
    
    private RetrofitClient() {
        // Configuration du logging pour déboguer les appels HTTP
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        
        // Configuration d'OkHttpClient avec timeouts réduits
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build();
        
        // Configuration de Gson pour la sérialisation/désérialisation JSON
        Gson gson = new GsonBuilder()
            .setLenient()
            .serializeNulls()
            .create();
        
        // Construction de Retrofit
        retrofit = new Retrofit.Builder()
            .baseUrl(Connection.getBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
        
        // Création du service API
        apiService = retrofit.create(ApiService.class);
    }
    
    /**
     * Récupère l'instance unique de RetrofitClient (Pattern Singleton)
     */
    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }
    
    /**
     * Retourne le service API pour effectuer les appels
     */
    public ApiService getApiService() {
        return apiService;
    }
    
    /**
     * Retourne l'instance Retrofit (utile pour créer d'autres services si nécessaire)
     */
    public Retrofit getRetrofit() {
        return retrofit;
    }
    
    /**
     * Formate le token JWT pour l'header Authorization
     * @param token Le token JWT brut
     * @return "Bearer {token}"
     */
    public static String formatToken(String token) {
        if (token == null || token.isEmpty()) {
            return "";
        }
        if (token.startsWith("Bearer ")) {
            return token;
        }
        return "Bearer " + token;
    }
}
