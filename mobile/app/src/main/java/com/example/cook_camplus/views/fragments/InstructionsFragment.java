package com.example.cook_camplus.views.fragments;

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
import com.example.cook_camplus.views.adapters.EtapeAdapter;

public class InstructionsFragment extends Fragment {
    
    private Recette recette;
    private RecyclerView recyclerViewEtapes;
    private View tvNoEtapes;
    
    public static InstructionsFragment newInstance(Recette recette) {
        InstructionsFragment fragment = new InstructionsFragment();
        fragment.recette = recette;
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);
        
        recyclerViewEtapes = view.findViewById(R.id.recycler_view_etapes);
        tvNoEtapes = view.findViewById(R.id.tv_no_etapes);
        
        afficherEtapes();
        
        return view;
    }
    
    private void afficherEtapes() {
        if (recette == null || recette.getEtapes() == null || recette.getEtapes().isEmpty()) {
            tvNoEtapes.setVisibility(View.VISIBLE);
            recyclerViewEtapes.setVisibility(View.GONE);
            return;
        }
        
        tvNoEtapes.setVisibility(View.GONE);
        recyclerViewEtapes.setVisibility(View.VISIBLE);
        
        // Afficher les étapes de préparation
        EtapeAdapter etapeAdapter = new EtapeAdapter(getContext(), recette.getEtapes());
        recyclerViewEtapes.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewEtapes.setAdapter(etapeAdapter);
    }
    
    public void updateRecette(Recette recette) {
        this.recette = recette;
        if (getView() != null) {
            afficherEtapes();
        }
    }
}
