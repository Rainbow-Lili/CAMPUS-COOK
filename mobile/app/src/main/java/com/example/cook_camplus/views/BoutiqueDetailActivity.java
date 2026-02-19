package com.example.cook_camplus.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cook_camplus.R;
import com.example.cook_camplus.datas.connection.Connection;
import com.example.cook_camplus.datas.models.Boutique;
import com.example.cook_camplus.datas.models.Pack;
import com.example.cook_camplus.datas.repositories.PackRepository;
import com.example.cook_camplus.datas.repositories.PanierManager;
import com.example.cook_camplus.views.adapters.PackAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import coil.Coil;
import coil.request.ImageRequest;

public class BoutiqueDetailActivity extends AppCompatActivity {
    
    private Boutique boutique;
    private ImageView ivBoutiqueLogo;
    private TextView tvBoutiqueNom;
    private TextView tvBoutiqueCategorie;
    private TextView tvBoutiqueNote;
    private TextView tvBoutiqueAdresse;
    private TextView tvBoutiqueTelephone;
    private TextView tvBoutiqueHoraires;
    private TextView tvBoutiqueDescription;
    private RecyclerView recyclerPacks;
    private ProgressBar progressPacks;
    private TextView tvNoPacks;
    private CollapsingToolbarLayout collapsingToolbar;
    
    private PackRepository packRepository;
    private PackAdapter packAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique_detail);
        
        // Récupérer la boutique depuis l'intent
        boutique = (Boutique) getIntent().getSerializableExtra("boutique");
        
        if (boutique == null) {
            Toast.makeText(this, "Erreur: boutique introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        packRepository = new PackRepository(this);
        
        initViews();
        setupToolbar();
        displayBoutiqueData();
        loadBoutiquePacks();
    }
    
    private void initViews() {
        ivBoutiqueLogo = findViewById(R.id.iv_boutique_logo);
        tvBoutiqueNom = findViewById(R.id.tv_boutique_nom);
        tvBoutiqueCategorie = findViewById(R.id.tv_boutique_categorie);
        tvBoutiqueNote = findViewById(R.id.tv_boutique_note);
        tvBoutiqueAdresse = findViewById(R.id.tv_boutique_adresse);
        tvBoutiqueTelephone = findViewById(R.id.tv_boutique_telephone);
        tvBoutiqueHoraires = findViewById(R.id.tv_boutique_horaires);
        tvBoutiqueDescription = findViewById(R.id.tv_boutique_description);
        recyclerPacks = findViewById(R.id.recycler_packs);
        progressPacks = findViewById(R.id.progress_packs);
        tvNoPacks = findViewById(R.id.tv_no_packs);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        
        recyclerPacks.setLayoutManager(new LinearLayoutManager(this));
    }
    
    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        
        // Titre dans la toolbar
        collapsingToolbar.setTitle(boutique.getNom());
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.textPrimary));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }
    
    private void displayBoutiqueData() {
        // Nom
        tvBoutiqueNom.setText(boutique.getNom());
        
        // Catégorie/Type
        String categorieText = boutique.estBoutiqueSpecialisee() 
            ? "Boutique spécialisée • " + boutique.getCategorie() 
            : boutique.getCategorie() != null ? boutique.getCategorie() : "Boutique";
        tvBoutiqueCategorie.setText(categorieText);
        
        // Note
        tvBoutiqueNote.setText(boutique.getNoteFormatee());
        
        // Adresse
        if (boutique.getAdresse() != null && !boutique.getAdresse().isEmpty()) {
            tvBoutiqueAdresse.setText(boutique.getAdresse());
        } else {
            tvBoutiqueAdresse.setVisibility(View.GONE);
        }
        
        // Téléphone
        if (boutique.getTelephone() != null && !boutique.getTelephone().isEmpty()) {
            tvBoutiqueTelephone.setText(boutique.getTelephone());
        } else {
            tvBoutiqueTelephone.setVisibility(View.GONE);
        }
        
        // Horaires
        if (boutique.getHorairesOuverture() != null && !boutique.getHorairesOuverture().isEmpty()) {
            tvBoutiqueHoraires.setText(boutique.getHorairesOuverture());
        } else {
            tvBoutiqueHoraires.setVisibility(View.GONE);
        }
        
        // Description
        if (boutique.getDescription() != null && !boutique.getDescription().isEmpty()) {
            tvBoutiqueDescription.setText(boutique.getDescription());
        } else {
            tvBoutiqueDescription.setText("Aucune description disponible");
        }
        
        // Logo avec Coil
        String logoUrl = boutique.getLogoUrl();
        if (logoUrl != null && !logoUrl.isEmpty()) {
            String fullUrl = Connection.getImageUrl(logoUrl);
            ImageRequest request = new ImageRequest.Builder(this)
                .data(fullUrl)
                .crossfade(true)
                .placeholder(R.drawable.ic_store)
                .error(R.drawable.ic_store)
                .target(ivBoutiqueLogo)
                .build();
            Coil.imageLoader(this).enqueue(request);
        } else {
            ivBoutiqueLogo.setImageResource(R.drawable.ic_store);
        }
    }
    
    private void loadBoutiquePacks() {
        progressPacks.setVisibility(View.VISIBLE);
        
        // Charger les packs de cette boutique
        packRepository.getPacksByBoutique(boutique.getId(), new PackRepository.PackListCallback() {
            @Override
            public void onSuccess(List<Pack> packs) {
                progressPacks.setVisibility(View.GONE);
                
                if (packs != null && !packs.isEmpty()) {
                    // Afficher les packs
                    packAdapter = new PackAdapter(BoutiqueDetailActivity.this, packs);
                    packAdapter.setOnPackClickListener(pack -> {
                        // Ouvrir le détail du pack
                        Intent intent = new Intent(BoutiqueDetailActivity.this, PackDetailActivity.class);
                        intent.putExtra("pack", pack);
                        startActivity(intent);
                    });
                    
                    // Listener pour l'ajout au panier
                    packAdapter.setOnPackAddToPanierListener(pack -> {
                        PanierManager panierManager = PanierManager.getInstance();
                        panierManager.ajouterPack(pack);
                        Toast.makeText(BoutiqueDetailActivity.this, 
                            "✓ Ajouté au panier (" + panierManager.getNombreArticles() + ")", 
                            Toast.LENGTH_SHORT).show();
                    });
                    
                    recyclerPacks.setAdapter(packAdapter);
                    recyclerPacks.setVisibility(View.VISIBLE);
                } else {
                    // Aucun pack
                    tvNoPacks.setVisibility(View.VISIBLE);
                }
            }
            
            @Override
            public void onError(String message) {
                progressPacks.setVisibility(View.GONE);
                tvNoPacks.setVisibility(View.VISIBLE);
                Toast.makeText(BoutiqueDetailActivity.this, "Erreur: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
