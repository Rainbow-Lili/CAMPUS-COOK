package com.example.cook_camplus.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cook_camplus.databinding.ActivityLoginBinding;
import com.example.cook_camplus.datas.models.AuthResponse;
import com.example.cook_camplus.datas.repositories.AuthRepository;
import com.example.cook_camplus.views.FournisseurMainActivity;

public class LoginActivity extends AppCompatActivity {
    
    private ActivityLoginBinding binding;
    private AuthRepository authRepository;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        authRepository = new AuthRepository(this);
        
        setupListeners();
    }
    
    private void setupListeners() {
        // Bouton de connexion
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            
            if (validateInputs(email, password)) {
                login(email, password);
            }
        });
        
        // Lien vers l'inscription
        binding.tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
    
    private boolean validateInputs(String email, String password) {
        if (email.isEmpty()) {
            binding.etEmail.setError("Email requis");
            binding.etEmail.requestFocus();
            return false;
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("Email invalide");
            binding.etEmail.requestFocus();
            return false;
        }
        
        if (password.isEmpty()) {
            binding.etPassword.setError("Mot de passe requis");
            binding.etPassword.requestFocus();
            return false;
        }
        
        if (password.length() < 8) {
            binding.etPassword.setError("Au moins 8 caractères");
            binding.etPassword.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void login(String email, String password) {
        // Afficher le loader
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnLogin.setEnabled(false);
        
        authRepository.login(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(AuthResponse response) {
                runOnUiThread(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Connexion réussie !", Toast.LENGTH_SHORT).show();

                    // Sauvegarder les infos utilisateur
                    android.content.SharedPreferences prefs = getSharedPreferences("cook_camplus_prefs", MODE_PRIVATE);
                    prefs.edit()
                        .putString("auth_token", response.getToken())
                        .putString("user_nom", response.getNom())
                        .putString("user_email", response.getEmail())
                        .putString("user_role", response.getRole())
                        .putLong("user_id", response.getId() != null ? response.getId() : -1L)
                        .apply();

                    // Rediriger selon le rôle
                    Intent intent;
                    if ("fournisseur".equalsIgnoreCase(response.getRole())) {
                        intent = new Intent(LoginActivity.this, FournisseurMainActivity.class);
                    } else {
                        intent = new Intent(LoginActivity.this, AllergiesActivity.class);
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });
            }
            
            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.btnLogin.setEnabled(true);
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                });
            }
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
