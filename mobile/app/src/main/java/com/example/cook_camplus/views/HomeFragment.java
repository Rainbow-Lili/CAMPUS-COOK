package com.example.cook_camplus.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cook_camplus.databinding.FragmentHomeBinding;
import com.example.cook_camplus.datas.models.Recette;
import com.example.cook_camplus.datas.repositories.RecetteRepository;
import com.example.cook_camplus.views.adapters.RecetteAdapter;
import com.example.cook_camplus.views.adapters.HorizontalRecetteAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {
    
    private FragmentHomeBinding binding;
    private RecetteRepository recetteRepository;
    private List<Recette> discoverList;
    private List<Recette> grilladesList;
    private List<Recette> saucesList;
    private List<Recette> accompagnementsList;
    private List<Recette> entreesList;
    private Set<String> userAllergies;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        recetteRepository = new RecetteRepository(requireContext());
        discoverList = new ArrayList<>();
        grilladesList = new ArrayList<>();
        saucesList = new ArrayList<>();
        accompagnementsList = new ArrayList<>();
        entreesList = new ArrayList<>();
        
        loadUserData();
        setupRecyclerViews();
        setupClickListeners();
        loadRecettes();
    }
    
    private void loadUserData() {
        SharedPreferences prefs = requireContext().getSharedPreferences("cook_camplus_prefs", android.content.Context.MODE_PRIVATE);
        
        String userName = prefs.getString("user_name", "");
        if (!userName.isEmpty()) {
            binding.tvGreeting.setText("Bonjour, " + userName);
        }
        
        userAllergies = prefs.getStringSet("user_allergies", new HashSet<>());
    }
    
    private void setupRecyclerViews() {
        // Utiliser HorizontalRecetteAdapter avec couleurs pastels pour la section "À Découvrir"
        binding.recyclerDiscover.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        HorizontalRecetteAdapter discoverAdapter = new HorizontalRecetteAdapter(requireContext(), discoverList);
        binding.recyclerDiscover.setAdapter(discoverAdapter);
        
        binding.recyclerGrillades.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        RecetteAdapter grilladesAdapter = new RecetteAdapter(requireContext(), grilladesList);
        binding.recyclerGrillades.setAdapter(grilladesAdapter);
        
        binding.recyclerSauces.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        RecetteAdapter saucesAdapter = new RecetteAdapter(requireContext(), saucesList);
        binding.recyclerSauces.setAdapter(saucesAdapter);
        
        binding.recyclerAccompagnements.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        RecetteAdapter accompagnementsAdapter = new RecetteAdapter(requireContext(), accompagnementsList);
        binding.recyclerAccompagnements.setAdapter(accompagnementsAdapter);
        
        binding.recyclerEntrees.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        RecetteAdapter entreesAdapter = new RecetteAdapter(requireContext(), entreesList);
        binding.recyclerEntrees.setAdapter(entreesAdapter);
    }
    
    private void setupClickListeners() {
        binding.tvSeeAllDiscover.setOnClickListener(v -> {
            Toast.makeText(requireContext(), 
                discoverList.size() + " recettes à découvrir", 
                Toast.LENGTH_SHORT).show();
        });
        
        binding.tvSeeAllGrillades.setOnClickListener(v -> {
            Toast.makeText(requireContext(), 
                grilladesList.size() + " grillades disponibles", 
                Toast.LENGTH_SHORT).show();
        });
        
        binding.tvSeeAllSauces.setOnClickListener(v -> {
            Toast.makeText(requireContext(), 
                saucesList.size() + " sauces & ragoûts disponibles", 
                Toast.LENGTH_SHORT).show();
        });
        
        binding.tvSeeAllAccompagnements.setOnClickListener(v -> {
            Toast.makeText(requireContext(), 
                accompagnementsList.size() + " accompagnements disponibles", 
                Toast.LENGTH_SHORT).show();
        });
        
        binding.tvSeeAllEntrees.setOnClickListener(v -> {
            Toast.makeText(requireContext(), 
                entreesList.size() + " entrées & apéritifs disponibles", 
                Toast.LENGTH_SHORT).show();
        });
    }
    
    private void loadRecettes() {
        recetteRepository.getPublicRecettes(new RecetteRepository.RecetteListCallback() {
            @Override
            public void onSuccess(List<Recette> recettes) {
                if (getActivity() == null) return;
                
                getActivity().runOnUiThread(() -> {
                    discoverList.clear();
                    grilladesList.clear();
                    saucesList.clear();
                    accompagnementsList.clear();
                    entreesList.clear();
                    
                    for (Recette recette : recettes) {
                        checkAllergies(recette);
                        
                        String nom = recette.getNom() != null ? recette.getNom().toLowerCase() : "";
                        String desc = recette.getDescription() != null ? recette.getDescription().toLowerCase() : "";
                        
                        if (nom.contains("braisé") || nom.contains("grillé") || nom.contains("brochette") || 
                            desc.contains("grillé") || desc.contains("braisé")) {
                            grilladesList.add(recette);
                        }
                        else if (nom.contains("sauce") || nom.contains("gombo") || nom.contains("ragoût") ||
                                 desc.contains("sauce")) {
                            saucesList.add(recette);
                        }
                        else if (nom.contains("foufou") || nom.contains("foutou") || nom.contains("attiéké") || 
                                 nom.contains("attieke") || nom.contains("riz") || nom.contains("igname") ||
                                 nom.contains("banane plantain") || nom.contains("placali") ||
                                 nom.contains("ablo") || nom.contains("pignon") || nom.contains("gari") ||
                                 nom.contains("koliko") || desc.contains("accompagnement")) {
                            accompagnementsList.add(recette);
                        }
                        else if (nom.contains("alloco") || nom.contains("accra") || nom.contains("beignet") ||
                                 nom.contains("kossam") || nom.contains("fataya") || nom.contains("samoussa") ||
                                 nom.contains("œuf") || nom.contains("oeuf") || nom.contains("salade") ||
                                 nom.contains("pâtes") || nom.contains("pates") || nom.contains("dégué") ||
                                 nom.contains("degue") || nom.contains("yaourt") || nom.contains("lait caillé") ||
                                 desc.contains("entrée") || desc.contains("apéritif") || desc.contains("dessert")) {
                            entreesList.add(recette);
                        }
                    }
                    
                    discoverList.addAll(recettes);
                    
                    binding.recyclerDiscover.getAdapter().notifyDataSetChanged();
                    binding.recyclerGrillades.getAdapter().notifyDataSetChanged();
                    binding.recyclerSauces.getAdapter().notifyDataSetChanged();
                    binding.recyclerAccompagnements.getAdapter().notifyDataSetChanged();
                    binding.recyclerEntrees.getAdapter().notifyDataSetChanged();
                    
                    Toast.makeText(requireContext(), 
                        recettes.size() + " recettes chargées", 
                        Toast.LENGTH_SHORT).show();
                });
            }
            
            @Override
            public void onError(String error) {
                if (getActivity() == null) return;
                
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), 
                        "Erreur de chargement: " + error, 
                        Toast.LENGTH_LONG).show();
                });
            }
        });
    }
    
    private void checkAllergies(Recette recette) {
        if (userAllergies == null || userAllergies.isEmpty()) {
            return;
        }
        
        String nom = recette.getNom() != null ? recette.getNom().toLowerCase() : "";
        String description = recette.getDescription() != null ? recette.getDescription().toLowerCase() : "";
        
        for (String allergie : userAllergies) {
            String allergieLC = allergie.toLowerCase();
            if (nom.contains(allergieLC) || description.contains(allergieLC)) {
                recette.setHasUserAllergy(true);
                return;
            }
        }
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
