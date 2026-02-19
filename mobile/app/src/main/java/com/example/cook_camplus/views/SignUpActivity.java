package com.example.cook_camplus.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cook_camplus.databinding.ActivitySignUpBinding;
import com.example.cook_camplus.datas.models.AuthResponse;
import com.example.cook_camplus.datas.repositories.AuthRepository;

public class SignUpActivity extends AppCompatActivity {
    
    private ActivitySignUpBinding binding;
    private AuthRepository authRepository;
    private boolean isStudent = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        authRepository = new AuthRepository(this);
        
        setupListeners();
    }
    
    private void setupListeners() {
        // Radio group pour le statut étudiant
        binding.rgStudentStatus.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == binding.rbStudentYes.getId()) {
                // Afficher les champs étudiants
                binding.layoutStudentFields.setVisibility(View.VISIBLE);
                isStudent = true;
            } else {
                // Cacher les champs étudiants
                binding.layoutStudentFields.setVisibility(View.GONE);
                isStudent = false;
                // Effacer les champs étudiants
                binding.etStudentId.setText("");
                binding.etStudentCard.setText("");
            }
        });

        // Bouton d'inscription
        binding.btnSignUp.setOnClickListener(v -> {
            String nom = binding.etNom.getText().toString().trim();
            String prenom = binding.etPrenom.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            String confirmPassword = binding.etConfirmPassword.getText().toString().trim();
            
            String studentId = binding.etStudentId.getText().toString().trim();
            String studentCard = binding.etStudentCard.getText().toString().trim();
            
            if (validateInputs(nom, prenom, email, password, confirmPassword, studentId, studentCard)) {
                register(nom, prenom, email, password, studentId, studentCard);
            }
        });
        
        // Lien vers la connexion
        binding.tvLogin.setOnClickListener(v -> {
            finish();
        });
    }
    
    private boolean validateInputs(String nom, String prenom, String email, String password, 
                                   String confirmPassword, String studentId, String studentCard) {
        if (nom.isEmpty()) {
            binding.etNom.setError("Nom requis");
            binding.etNom.requestFocus();
            return false;
        }
        
        if (prenom.isEmpty()) {
            binding.etPrenom.setError("Prénom requis");
            binding.etPrenom.requestFocus();
            return false;
        }
        
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
        
        if (!password.equals(confirmPassword)) {
            binding.etConfirmPassword.setError("Les mots de passe ne correspondent pas");
            binding.etConfirmPassword.requestFocus();
            return false;
        }
        
        // Validation pour les étudiants
        if (isStudent) {
            if (studentId.isEmpty()) {
                binding.etStudentId.setError("Identifiant étudiant requis");
                binding.etStudentId.requestFocus();
                return false;
            }
            
            if (studentCard.isEmpty()) {
                binding.etStudentCard.setError("Numéro de carte requis");
                binding.etStudentCard.requestFocus();
                return false;
            }
        }
        
        return true;
    }
    
    private void register(String nom, String prenom, String email, String password, 
                         String studentId, String studentCard) {
        // Afficher le loader
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnSignUp.setEnabled(false);
        
        authRepository.register(nom, prenom, email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(AuthResponse response) {
                runOnUiThread(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    
                    // Si c'est un étudiant, sauvegarder les infos supplémentaires
                    if (isStudent && !studentId.isEmpty() && !studentCard.isEmpty()) {
                        // Sauvegarder dans SharedPreferences
                        android.content.SharedPreferences prefs = getSharedPreferences("cook_camplus_prefs", MODE_PRIVATE);
                        prefs.edit()
                            .putBoolean("is_student", true)
                            .putString("student_id", studentId)
                            .putString("student_card", studentCard)
                            .apply();
                        
                        Toast.makeText(SignUpActivity.this, 
                            "Inscription réussie ! Bienvenue étudiant(e) 🎓", 
                            Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Inscription réussie !", Toast.LENGTH_SHORT).show();
                    }
                    
                    // Rediriger vers MainActivity
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });
            }
            
            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.btnSignUp.setEnabled(true);
                    Toast.makeText(SignUpActivity.this, error, Toast.LENGTH_LONG).show();
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
