package com.example.cook_camplus.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cook_camplus.R;
import com.example.cook_camplus.datas.models.PanierItem;
import com.example.cook_camplus.datas.repositories.PanierManager;
import com.example.cook_camplus.views.adapters.PanierAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;
import java.util.Locale;

public class PanierActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private RecyclerView recyclerPanier;
    private TextView tvTotal;
    private Button btnCommander;
    private LinearLayout layoutPanierVide;
    private LinearLayout layoutFooter;

    private PanierManager panierManager;
    private PanierAdapter adapter;
    private List<PanierItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);

        initViews();
        setupPanier();
        setupListeners();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerPanier = findViewById(R.id.recyclerPanier);
        tvTotal = findViewById(R.id.tvTotal);
        btnCommander = findViewById(R.id.btnCommander);
        layoutPanierVide = findViewById(R.id.layoutPanierVide);
        layoutFooter = findViewById(R.id.layoutFooter);
        
        // Configuration de la toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupPanier() {
        panierManager = PanierManager.getInstance();
        items = panierManager.getTousLesItems();

        if (items.isEmpty()) {
            afficherPanierVide();
        } else {
            afficherPanier();
        }
    }

    private void afficherPanierVide() {
        layoutPanierVide.setVisibility(View.VISIBLE);
        recyclerPanier.setVisibility(View.GONE);
        layoutFooter.setVisibility(View.GONE);
    }

    private void afficherPanier() {
        layoutPanierVide.setVisibility(View.GONE);
        recyclerPanier.setVisibility(View.VISIBLE);
        layoutFooter.setVisibility(View.VISIBLE);

        // Configuration du RecyclerView
        recyclerPanier.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PanierAdapter(this, items);
        adapter.setOnPanierItemListener(new PanierAdapter.OnPanierItemListener() {
            @Override
            public void onQuantiteChanged(PanierItem item, int nouvelleQuantite) {
                panierManager.modifierQuantite(item.getPack(), nouvelleQuantite);
                refreshPanier();
            }

            @Override
            public void onItemSupprimer(PanierItem item) {
                panierManager.retirerPack(item.getPack());
                refreshPanier();
                Toast.makeText(PanierActivity.this, "Article retiré", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerPanier.setAdapter(adapter);

        // Mise à jour du total
        updateTotal();
    }

    private void refreshPanier() {
        items = panierManager.getTousLesItems();
        if (items.isEmpty()) {
            afficherPanierVide();
        } else {
            adapter = new PanierAdapter(this, items);
            adapter.setOnPanierItemListener(new PanierAdapter.OnPanierItemListener() {
                @Override
                public void onQuantiteChanged(PanierItem item, int nouvelleQuantite) {
                    panierManager.modifierQuantite(item.getPack(), nouvelleQuantite);
                    refreshPanier();
                }

                @Override
                public void onItemSupprimer(PanierItem item) {
                    panierManager.retirerPack(item.getPack());
                    refreshPanier();
                    Toast.makeText(PanierActivity.this, "Article retiré", Toast.LENGTH_SHORT).show();
                }
            });
            recyclerPanier.setAdapter(adapter);
            updateTotal();
        }
    }

    private void updateTotal() {
        double total = panierManager.getTotal();
        tvTotal.setText(String.format(Locale.getDefault(), "%.0f FCFA", total));
    }

    private void setupListeners() {
        btnCommander.setOnClickListener(v -> {
            if (panierManager.estVide()) {
                Toast.makeText(this, "Votre panier est vide", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérifier qu'il n'y a qu'une seule boutique
            List<Long> boutiquesIds = panierManager.getBoutiquesIds();
            if (boutiquesIds.size() > 1) {
                Toast.makeText(this, "Vous ne pouvez commander que d'une seule boutique à la fois", Toast.LENGTH_LONG).show();
                return;
            }

            // Ouvrir la discussion avec le fournisseur
            Long boutiqueId = boutiquesIds.get(0);
            Intent intent = new Intent(PanierActivity.this, DiscussionActivity.class);
            intent.putExtra("BOUTIQUE_ID", boutiqueId);
            intent.putExtra("ENVOYER_PANIER", true);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshPanier();
    }
}
