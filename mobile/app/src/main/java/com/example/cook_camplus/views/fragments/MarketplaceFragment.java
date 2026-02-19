package com.example.cook_camplus.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cook_camplus.R;
import com.example.cook_camplus.datas.models.Boutique;
import com.example.cook_camplus.datas.models.Pack;
import com.example.cook_camplus.datas.repositories.BoutiqueRepository;
import com.example.cook_camplus.datas.repositories.PackRepository;
import com.example.cook_camplus.datas.repositories.PanierManager;
import com.example.cook_camplus.views.BoutiqueDetailActivity;
import com.example.cook_camplus.views.PackDetailActivity;
import com.example.cook_camplus.views.PanierActivity;
import com.example.cook_camplus.views.adapters.BoutiqueAdapter;
import com.example.cook_camplus.views.adapters.PackAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MarketplaceFragment extends Fragment {
    
    private MaterialToolbar toolbar;
    private RecyclerView recyclerCategories;
    private RecyclerView recyclerBoutiques;
    private View progressBar;
    private View tvEmptyState;
    private FloatingActionButton fabPanier;
    private TextView tvBadgePanier;
    
    private PackAdapter packAdapter;
    private BoutiqueAdapter boutiqueAdapter;
    
    private PackRepository packRepository;
    private BoutiqueRepository boutiqueRepository;
    
    public static MarketplaceFragment newInstance() {
        return new MarketplaceFragment();
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marketplace, container, false);
        
        // Initialiser les repositories
        packRepository = new PackRepository(requireContext());
        boutiqueRepository = new BoutiqueRepository(requireContext());
        
        initViews(view);
        setupToolbar();
        setupRecyclerViews();
        loadMarketplaceData();
        
        return view;
    }
    
    private void initViews(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        recyclerCategories = view.findViewById(R.id.recycler_categories);
        recyclerBoutiques = view.findViewById(R.id.recycler_boutiques);
        progressBar = view.findViewById(R.id.progress_bar);
        fabPanier = view.findViewById(R.id.fabPanier);
        tvBadgePanier = view.findViewById(R.id.tvBadgePanier);
        
        // Gestion du clic sur le FAB
        fabPanier.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PanierActivity.class);
            startActivity(intent);
        });
        tvEmptyState = view.findViewById(R.id.tv_empty_state);
        
        // Initialiser le badge
        updateBadgePanier();
    }
    
    private void setupToolbar() {
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        }
    }
    
    private void setupRecyclerViews() {
        // Catégories en grille horizontale
        recyclerCategories.setLayoutManager(
            new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false)
        );
        
        // Boutiques en liste verticale
        recyclerBoutiques.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    
    private void loadMarketplaceData() {
        // Afficher le loader
        progressBar.setVisibility(View.VISIBLE);
        recyclerCategories.setVisibility(View.GONE);
        recyclerBoutiques.setVisibility(View.GONE);
        tvEmptyState.setVisibility(View.GONE);
        
        // Charger les packs disponibles
        packRepository.getAvailablePacks(new PackRepository.PackListCallback() {
            @Override
            public void onSuccess(List<Pack> packs) {
                if (getContext() == null) return;
                
                // Initialiser l'adapter des packs
                packAdapter = new PackAdapter(getContext(), packs);
                packAdapter.setOnPackClickListener(pack -> {
                    Intent intent = new Intent(getActivity(), PackDetailActivity.class);
                    intent.putExtra("pack", pack);
                    startActivity(intent);
                });
                
                // Listener pour l'ajout au panier
                packAdapter.setOnPackAddToPanierListener(pack -> {
                    PanierManager panierManager = PanierManager.getInstance();
                    panierManager.ajouterPack(pack);
                    Toast.makeText(getContext(), 
                        "✓ Ajouté au panier (" + panierManager.getNombreArticles() + ")", 
                        Toast.LENGTH_SHORT).show();
                    updateBadgePanier();
                });
                
                recyclerCategories.setAdapter(packAdapter);
                
                // Afficher les packs si disponibles
                if (!packs.isEmpty()) {
                    recyclerCategories.setVisibility(View.VISIBLE);
                }
                
                // Charger les boutiques ensuite
                loadBoutiques();
            }
            
            @Override
            public void onError(String error) {
                if (getContext() == null) return;
                
                Toast.makeText(getContext(), "Erreur packs: " + error, Toast.LENGTH_SHORT).show();
                // Charger quand même les boutiques
                loadBoutiques();
            }
        });
    }
    
    private void loadBoutiques() {
        // Charger les boutiques actives
        boutiqueRepository.getActiveBoutiques(new BoutiqueRepository.BoutiqueListCallback() {
            @Override
            public void onSuccess(List<Boutique> boutiques) {
                if (getContext() == null) return;
                
                // Initialiser l'adapter des boutiques
                boutiqueAdapter = new BoutiqueAdapter(getContext(), boutiques);
                boutiqueAdapter.setOnBoutiqueClickListener(boutique -> {
                    Intent intent = new Intent(getActivity(), BoutiqueDetailActivity.class);
                    intent.putExtra("boutique", boutique);
                    startActivity(intent);
                });
                recyclerBoutiques.setAdapter(boutiqueAdapter);
                
                // Masquer le loader
                progressBar.setVisibility(View.GONE);
                
                // Afficher les données ou l'état vide
                if (!boutiques.isEmpty()) {
                    recyclerBoutiques.setVisibility(View.VISIBLE);
                } else if (packAdapter == null || packAdapter.getItemCount() == 0) {
                    // Si aucun pack et aucune boutique, afficher message vide
                    tvEmptyState.setVisibility(View.VISIBLE);
                }
            }
            
            @Override
            public void onError(String error) {
                if (getContext() == null) return;
                
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Erreur boutiques: " + error, Toast.LENGTH_SHORT).show();
                
                // Si aucune donnée chargée, afficher état vide
                if (packAdapter == null || packAdapter.getItemCount() == 0) {
                    tvEmptyState.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    
    private void updateBadgePanier() {
        if (tvBadgePanier != null) {
            PanierManager panierManager = PanierManager.getInstance();
            int nombreArticles = panierManager.getNombreArticles();
            
            if (nombreArticles > 0) {
                tvBadgePanier.setVisibility(View.VISIBLE);
                tvBadgePanier.setText(String.valueOf(nombreArticles));
            } else {
                tvBadgePanier.setVisibility(View.GONE);
            }
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        updateBadgePanier();
    }
}
