package com.example.cook_camplus.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cook_camplus.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.HashSet;
import java.util.Set;

public class AllergiesActivity extends AppCompatActivity {
    
    private static final String PREFS_NAME = "cook_camplus_prefs";
    private static final String KEY_ALLERGIES = "user_allergies";
    private static final String KEY_ALLERGIES_CONFIGURED = "allergies_configured";
    
    private ChipGroup chipGroupAllergies;
    private MaterialCardView cardNoAllergy;
    private MaterialButton btnContinue;
    private Set<String> selectedAllergies;
    private boolean noAllergySelected = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergies);
        
        selectedAllergies = new HashSet<>();
        
        initViews();
        setupListeners();
    }
    
    private void initViews() {
        chipGroupAllergies = findViewById(R.id.chip_group_allergies);
        cardNoAllergy = findViewById(R.id.card_no_allergy);
        btnContinue = findViewById(R.id.btn_continue);
    }
    
    private void setupListeners() {
        // Gestion des chips d'allergies
        for (int i = 0; i < chipGroupAllergies.getChildCount(); i++) {
            View child = chipGroupAllergies.getChildAt(i);
            if (child instanceof Chip) {
                Chip chip = (Chip) child;
                chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    String allergyText = chip.getText().toString();
                    // Extraire le nom sans l'emoji
                    String allergyName = allergyText.replaceAll("[^a-zA-Zéèêà]+", "").trim();
                    
                    if (isChecked) {
                        selectedAllergies.add(allergyName);
                        noAllergySelected = false;
                        updateNoAllergyCard();
                    } else {
                        selectedAllergies.remove(allergyName);
                    }
                    
                    updateContinueButton();
                });
            }
        }
        
        // Option "Aucune allergie"
        cardNoAllergy.setOnClickListener(v -> {
            noAllergySelected = !noAllergySelected;
            updateNoAllergyCard();
            
            if (noAllergySelected) {
                // Décocher toutes les chips
                for (int i = 0; i < chipGroupAllergies.getChildCount(); i++) {
                    View child = chipGroupAllergies.getChildAt(i);
                    if (child instanceof Chip) {
                        ((Chip) child).setChecked(false);
                    }
                }
                selectedAllergies.clear();
            }
            
            updateContinueButton();
        });
        
        // Bouton Continuer
        btnContinue.setOnClickListener(v -> saveAndContinue());
        
        // Passer cette étape
        findViewById(R.id.tv_skip).setOnClickListener(v -> {
            saveAllergies(new HashSet<>());
            navigateToHome();
        });
    }
    
    private void updateNoAllergyCard() {
        if (noAllergySelected) {
            cardNoAllergy.setCardBackgroundColor(0xFF4A4A4A); // Dark card selected
            cardNoAllergy.setStrokeColor(0xFFFF6B35); // Orange accent
            cardNoAllergy.setStrokeWidth(3);
        } else {
            cardNoAllergy.setCardBackgroundColor(0xFF404040); // Dark card
            cardNoAllergy.setStrokeColor(0xFF555555); // Dark divider
            cardNoAllergy.setStrokeWidth(1);
        }
    }
    
    private void updateContinueButton() {
        // Le bouton est toujours activé, même sans sélection
        btnContinue.setEnabled(true);
    }
    
    private void saveAndContinue() {
        saveAllergies(selectedAllergies);
        
        if (noAllergySelected) {
            Toast.makeText(this, "Aucune allergie enregistrée", Toast.LENGTH_SHORT).show();
        } else if (selectedAllergies.isEmpty()) {
            Toast.makeText(this, "Aucune allergie sélectionnée", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, selectedAllergies.size() + " allergie(s) enregistrée(s)", Toast.LENGTH_SHORT).show();
        }
        
        navigateToHome();
    }
    
    private void saveAllergies(Set<String> allergies) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(KEY_ALLERGIES, allergies);
        editor.putBoolean(KEY_ALLERGIES_CONFIGURED, true);
        editor.apply();
    }
    
    private void navigateToHome() {
        Intent intent = new Intent(AllergiesActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    
    /**
     * Méthode statique pour vérifier si l'utilisateur a déjà configuré ses allergies
     */
    public static boolean hasConfiguredAllergies(SharedPreferences prefs) {
        return prefs.getBoolean(KEY_ALLERGIES_CONFIGURED, false);
    }
    
    /**
     * Récupère les allergies sauvegardées
     */
    public static Set<String> getSavedAllergies(SharedPreferences prefs) {
        return prefs.getStringSet(KEY_ALLERGIES, new HashSet<>());
    }
}
