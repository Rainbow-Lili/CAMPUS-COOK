package com.example.cook_camplus.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.cook_camplus.R;
import com.example.cook_camplus.datas.connection.Connection;
import com.example.cook_camplus.datas.models.Pack;
import com.example.cook_camplus.datas.repositories.PanierManager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import coil.Coil;
import coil.request.ImageRequest;

public class PackDetailActivity extends AppCompatActivity {
    
    private Pack pack;
    private ImageView ivPackImage;
    private ImageView ivFavorite;
    private ImageView ivShare;
    private CardView cardBudget;
    private TextView tvBudget;
    private TextView tvPackNom;
    private TextView tvPackPrix;
    private TextView tvPackNote;
    private TextView tvPackAvis;
    private TextView tvPackStock;
    private TextView tvPackDescription;
    private ImageView ivBoutiqueLogo;
    private TextView tvBoutiqueNom;
    private TextView tvBoutiqueNote;
    private LinearLayout layoutAdresse;
    private LinearLayout layoutTelephone;
    private LinearLayout layoutHoraires;
    private TextView tvBoutiqueAdresse;
    private TextView tvBoutiqueTelephone;
    private TextView tvBoutiqueHoraires;
    private ExtendedFloatingActionButton fabAddCart;
    private CollapsingToolbarLayout collapsingToolbar;
    private boolean isFavorite = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_detail);
        
        // Récupérer le pack depuis l'intent
        pack = (Pack) getIntent().getSerializableExtra("pack");
        
        if (pack == null) {
            Toast.makeText(this, "Erreur: pack introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        initViews();
        setupToolbar();
        displayPackData();
        setupListeners();
    }
    
    private void initViews() {
        ivPackImage = findViewById(R.id.iv_pack_image);
        ivFavorite = findViewById(R.id.iv_favorite);
        ivShare = findViewById(R.id.iv_share);
        cardBudget = findViewById(R.id.card_budget);
        tvBudget = findViewById(R.id.tv_budget);
        tvPackNom = findViewById(R.id.tv_pack_nom);
        tvPackPrix = findViewById(R.id.tv_pack_prix);
        tvPackNote = findViewById(R.id.tv_pack_note);
        tvPackAvis = findViewById(R.id.tv_pack_avis);
        tvPackStock = findViewById(R.id.tv_pack_stock);
        tvPackDescription = findViewById(R.id.tv_pack_description);
        ivBoutiqueLogo = findViewById(R.id.iv_boutique_logo);
        tvBoutiqueNom = findViewById(R.id.tv_boutique_nom);
        tvBoutiqueNote = findViewById(R.id.tv_boutique_note);
        layoutAdresse = findViewById(R.id.layout_adresse);
        layoutTelephone = findViewById(R.id.layout_telephone);
        layoutHoraires = findViewById(R.id.layout_horaires);
        tvBoutiqueAdresse = findViewById(R.id.tv_boutique_adresse);
        tvBoutiqueTelephone = findViewById(R.id.tv_boutique_telephone);
        tvBoutiqueHoraires = findViewById(R.id.tv_boutique_horaires);
        fabAddCart = findViewById(R.id.fab_add_cart);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
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
        collapsingToolbar.setTitle(pack.getNom());
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.textPrimary));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }
    
    private void displayPackData() {
        // Nom
        tvPackNom.setText(pack.getNom());
        
        // Budget
        if (pack.getBudget() != null && !pack.getBudget().isEmpty()) {
            tvBudget.setText(pack.getBudget());
            cardBudget.setVisibility(View.VISIBLE);
        } else {
            cardBudget.setVisibility(View.GONE);
        }
        
        // Prix
        tvPackPrix.setText(pack.getPrixFormate());
        
        // Note et avis
        if (pack.getNoteMoyenne() != null && pack.getNoteMoyenne() > 0) {
            tvPackNote.setText(String.format("%.1f", pack.getNoteMoyenne()));
        } else {
            tvPackNote.setText("Pas encore noté");
        }
        
        int nombreAvis = pack.getNombreAvis() != null ? pack.getNombreAvis() : 0;
        tvPackAvis.setText("(" + nombreAvis + " avis)");
        
        // Stock
        if (pack.getStock() != null) {
            if (pack.getStock() > 0) {
                tvPackStock.setText("✓ En stock: " + pack.getStock() + " disponibles");
                tvPackStock.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                fabAddCart.setEnabled(true);
            } else {
                tvPackStock.setText("✗ Rupture de stock");
                tvPackStock.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                fabAddCart.setEnabled(false);
            }
        }
        
        // Description
        if (pack.getDescription() != null && !pack.getDescription().isEmpty()) {
            tvPackDescription.setText(pack.getDescription());
        } else {
            tvPackDescription.setText("Aucune description disponible");
        }
        
        // Informations de la boutique
        if (pack.getBoutique() != null) {
            tvBoutiqueNom.setText(pack.getBoutique().getNom());
            
            // Note boutique
            if (pack.getBoutique().getNoteMoyenne() != null && pack.getBoutique().getNoteMoyenne() > 0) {
                tvBoutiqueNote.setText(String.format("%.1f", pack.getBoutique().getNoteMoyenne()));
            } else {
                tvBoutiqueNote.setText("Nouvelle boutique");
            }
            
            // Logo boutique
            String logoUrl = pack.getBoutique().getLogoUrl();
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
            
            // Adresse
            if (pack.getBoutique().getAdresse() != null && !pack.getBoutique().getAdresse().isEmpty()) {
                tvBoutiqueAdresse.setText(pack.getBoutique().getAdresse());
                layoutAdresse.setVisibility(View.VISIBLE);
            } else {
                layoutAdresse.setVisibility(View.GONE);
            }
            
            // Téléphone
            if (pack.getBoutique().getTelephone() != null && !pack.getBoutique().getTelephone().isEmpty()) {
                tvBoutiqueTelephone.setText(pack.getBoutique().getTelephone());
                layoutTelephone.setVisibility(View.VISIBLE);
            } else {
                layoutTelephone.setVisibility(View.GONE);
            }
            
            // Horaires
            if (pack.getBoutique().getHorairesOuverture() != null && !pack.getBoutique().getHorairesOuverture().isEmpty()) {
                tvBoutiqueHoraires.setText(pack.getBoutique().getHorairesOuverture());
                layoutHoraires.setVisibility(View.VISIBLE);
            } else {
                layoutHoraires.setVisibility(View.GONE);
            }
        }
        
        // Image du pack avec Coil
        String imageUrl = pack.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String fullUrl = Connection.getImageUrl(imageUrl);
            ImageRequest request = new ImageRequest.Builder(this)
                .data(fullUrl)
                .crossfade(true)
                .placeholder(R.drawable.ic_pack)
                .error(R.drawable.ic_pack)
                .target(ivPackImage)
                .build();
            Coil.imageLoader(this).enqueue(request);
        } else {
            ivPackImage.setImageResource(R.drawable.ic_pack);
        }
    }
    
    private void setupListeners() {
        // Bouton favoris
        ivFavorite.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            if (isFavorite) {
                ivFavorite.setImageResource(android.R.drawable.btn_star_big_on);
                Toast.makeText(this, "Ajouté aux favoris", Toast.LENGTH_SHORT).show();
            } else {
                ivFavorite.setImageResource(android.R.drawable.btn_star_big_off);
                Toast.makeText(this, "Retiré des favoris", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Bouton partager
        ivShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, pack.getNom());
            shareIntent.putExtra(Intent.EXTRA_TEXT, 
                "Découvre ce pack sur CookCampus: " + pack.getNom() + " - " + pack.getPrixFormate());
            startActivity(Intent.createChooser(shareIntent, "Partager via"));
        });
        
        // Clic sur le téléphone pour appeler
        layoutTelephone.setOnClickListener(v -> {
            if (pack.getBoutique() != null && pack.getBoutique().getTelephone() != null) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + pack.getBoutique().getTelephone()));
                startActivity(callIntent);
            }
        });
        
        // Bouton ajouter au panier
        fabAddCart.setOnClickListener(v -> {
            if (pack.getStock() != null && pack.getStock() > 0) {
                PanierManager panierManager = PanierManager.getInstance();
                panierManager.ajouterPack(pack);
                Toast.makeText(this, 
                    "✓ " + pack.getNom() + " ajouté au panier (" + panierManager.getNombreArticles() + ")", 
                    Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ce pack n'est plus en stock", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
