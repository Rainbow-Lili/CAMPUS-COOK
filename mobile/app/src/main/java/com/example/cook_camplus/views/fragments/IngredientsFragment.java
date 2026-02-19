package com.example.cook_camplus.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cook_camplus.R;
import com.example.cook_camplus.datas.models.Recette;
import com.example.cook_camplus.datas.models.RecetteIngredient;
import com.example.cook_camplus.views.MarketplaceActivity;
import com.example.cook_camplus.views.adapters.IngredientAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class IngredientsFragment extends Fragment {
    
    private Recette recette;
    private int nombrePersonnes = 1;
    private RecyclerView recyclerViewIngredients;
    private View tvNoIngredients;
    private View tvCoutTotal;
    private View tvCoutParPersonne;
    private View tvNombrePersonnes;
    private View btnMoinsPersonnes;
    private View btnPlusPersonnes;
    private MaterialButton btnMarche;
    
    public static IngredientsFragment newInstance(Recette recette, int nombrePersonnes) {
        IngredientsFragment fragment = new IngredientsFragment();
        fragment.recette = recette;
        fragment.nombrePersonnes = nombrePersonnes;
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        
        recyclerViewIngredients = view.findViewById(R.id.recycler_view_ingredients);
        tvNoIngredients = view.findViewById(R.id.tv_no_ingredients);
        tvCoutTotal = view.findViewById(R.id.tv_cout_total);
        tvCoutParPersonne = view.findViewById(R.id.tv_cout_par_personne);
        tvNombrePersonnes = view.findViewById(R.id.tv_nombre_personnes);
        btnMoinsPersonnes = view.findViewById(R.id.btn_moins_personnes);
        btnPlusPersonnes = view.findViewById(R.id.btn_plus_personnes);
        btnMarche = view.findViewById(R.id.btn_marche);
        
        setupPortionsControl();
        setupMarketplaceButton();
        afficherIngredients();
        
        return view;
    }
    
    private void setupMarketplaceButton() {
        btnMarche.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MarketplaceActivity.class);
            startActivity(intent);
        });
    }
    
    private void setupPortionsControl() {
        btnMoinsPersonnes.setOnClickListener(v -> {
            if (nombrePersonnes > 1) {
                nombrePersonnes--;
                updatePortions();
            }
        });
        
        btnPlusPersonnes.setOnClickListener(v -> {
            if (nombrePersonnes < 20) {
                nombrePersonnes++;
                updatePortions();
            }
        });
    }
    
    private void updatePortions() {
        ((android.widget.TextView) tvNombrePersonnes).setText(String.valueOf(nombrePersonnes));
        afficherIngredients();
    }
    
    public void afficherIngredients() {
        if (recette == null || recette.getIngredients() == null || recette.getIngredients().isEmpty()) {
            tvNoIngredients.setVisibility(View.VISIBLE);
            recyclerViewIngredients.setVisibility(View.GONE);
            return;
        }
        
        tvNoIngredients.setVisibility(View.GONE);
        recyclerViewIngredients.setVisibility(View.VISIBLE);
        
        // Ajuster les quantités selon le nombre de personnes
        List<RecetteIngredient> ingredientsAjustes = recette.ajusterIngredientsQuantites(nombrePersonnes);
        
        // Afficher dans le RecyclerView
        IngredientAdapter adapter = new IngredientAdapter(getContext(), ingredientsAjustes);
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewIngredients.setAdapter(adapter);
        
        // Calculer et afficher le coût
        calculerEtAfficherCout(ingredientsAjustes);
    }
    
    private void calculerEtAfficherCout(List<RecetteIngredient> ingredientsAjustes) {
        double coutTotal = 0.0;
        
        if (ingredientsAjustes != null && !ingredientsAjustes.isEmpty()) {
            for (RecetteIngredient ri : ingredientsAjustes) {
                coutTotal += ri.calculerPrix();
            }
        }
        
        ((android.widget.TextView) tvCoutTotal).setText(String.format("%.0f FCFA", coutTotal));
        ((android.widget.TextView) tvCoutParPersonne).setText(
            String.format("Soit %.0f FCFA / personne", coutTotal / nombrePersonnes)
        );
    }
    
    public void updateRecette(Recette recette) {
        this.recette = recette;
        if (getView() != null) {
            afficherIngredients();
        }
    }
}
