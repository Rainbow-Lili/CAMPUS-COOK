package com.example.cook_camplus.views.fournisseur;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cook_camplus.databinding.FragmentFournisseurHomeBinding;
import com.example.cook_camplus.views.fournisseur.FournisseurBoutiqueFragment;
import com.example.cook_camplus.views.fournisseur.FournisseurPacksFragment;
import com.example.cook_camplus.R;

public class FournisseurHomeFragment extends Fragment {

    private FragmentFournisseurHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFournisseurHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Charger le nom du fournisseur depuis les prefs
        SharedPreferences prefs = requireContext().getSharedPreferences("cook_camplus_prefs", 0);
        String nom = prefs.getString("user_nom", "Fournisseur");
        binding.tvFournisseurNom.setText(nom);

        // Navigation rapide vers boutique
        binding.cardActionBoutique.setOnClickListener(v -> navigateTo(new FournisseurBoutiqueFragment()));
        binding.cardActionAddPack.setOnClickListener(v -> navigateTo(new FournisseurPacksFragment()));
        binding.cardActionViewPacks.setOnClickListener(v -> navigateTo(new FournisseurPacksFragment()));
    }

    private void navigateTo(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fournisseur_nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
