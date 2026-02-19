package com.example.cook_camplus.views;

import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cook_camplus.R;
import com.example.cook_camplus.databinding.ActivityRecipeDetailBinding;
import com.example.cook_camplus.datas.models.Recette;
import com.example.cook_camplus.datas.repositories.RecetteRepository;
import com.example.cook_camplus.datas.connection.Connection;
import com.example.cook_camplus.views.adapters.RecipeTabsAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

import coil.Coil;
import coil.request.ImageRequest;

public class RecipeDetailActivity extends AppCompatActivity {
    
    private ActivityRecipeDetailBinding binding;
    private RecetteRepository recetteRepository;
    private Recette recette;
    private int nombrePersonnesActuel = 1;
    private RecipeTabsAdapter tabsAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        recetteRepository = new RecetteRepository(this);
        
        Long recetteId = getIntent().getLongExtra("recette_id", -1L);
        String recetteNom = getIntent().getStringExtra("recette_nom");
        
        if (recetteId == -1L) {
            Toast.makeText(this, "Erreur: recette introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        setupToolbar(recetteNom);
        setupBackPressedHandler();
        loadRecetteDetail(recetteId);
    }
    
    private void setupToolbar(String nom) {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(nom != null ? nom : "");
        }
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.collapsingToolbar.setTitle(nom != null ? nom : "Détail de la recette");
        binding.collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        binding.collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
    }
    
    private void setupBackPressedHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }
    
    private void loadRecetteDetail(Long recetteId) {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.scrollView.setVisibility(View.GONE);
        
        recetteRepository.getRecetteById(recetteId, new RecetteRepository.RecetteCallback() {
            @Override
            public void onSuccess(Recette r) {
                runOnUiThread(() -> {
                    recette = r;
                    afficherRecette();
                    binding.progressBar.setVisibility(View.GONE);
                    binding.scrollView.setVisibility(View.VISIBLE);
                });
            }
            
            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(RecipeDetailActivity.this, error, Toast.LENGTH_LONG).show();
                    finish();
                });
            }
        });
    }
    
    private void afficherRecette() {
        nombrePersonnesActuel = recette.getNombrePersonnesBase() != null ? recette.getNombrePersonnesBase() : 2;
        chargerImageRecette();
        binding.tvTemps.setText(recette.getTempsFormate());
        binding.tvTemps.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_timer, 0, 0, 0);
        binding.tvTemps.setCompoundDrawablePadding(8);
        
        binding.tvDifficulte.setText(recette.getDifficulte());
        binding.tvDifficulte.setCompoundDrawablesWithIntrinsicBounds(recette.getDifficulteIconResId(), 0, 0, 0);
        binding.tvDifficulte.setCompoundDrawablePadding(8);
        
        binding.tvPersonnes.setText(String.valueOf(nombrePersonnesActuel));
        binding.tvPersonnes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person, 0, 0, 0);
        binding.tvPersonnes.setCompoundDrawablePadding(8);
        
        binding.tvDescription.setText(recette.getDescription());
        setupTabs();
    }
    
    private void chargerImageRecette() {
        if (recette.getImageUrl() != null && !recette.getImageUrl().isEmpty()) {
            String imageUrl = Connection.getImageUrl(recette.getImageUrl());
            ImageView imageView = binding.ivRecipeImage;
            ImageRequest request = new ImageRequest.Builder(this)
                .data(imageUrl)
                .target(imageView)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .build();
            Coil.imageLoader(this).enqueue(request);
        }
    }
    
    private void setupTabs() {
        tabsAdapter = new RecipeTabsAdapter(this, recette, nombrePersonnesActuel);
        binding.viewPager.setAdapter(tabsAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
            (tab, position) -> {
                switch (position) {
                    case 0:
                        tab.setText("Ingrédients");
                        tab.setIcon(R.drawable.ic_ingredients);
                        break;
                    case 1:
                        tab.setText("Instructions");
                        tab.setIcon(R.drawable.ic_instructions);
                        break;
                    case 2:
                        tab.setText("Vidéo");
                        tab.setIcon(R.drawable.ic_video);
                        break;
                }
            }
        ).attach();
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
