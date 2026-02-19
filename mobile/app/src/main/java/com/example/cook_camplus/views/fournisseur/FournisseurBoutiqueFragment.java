package com.example.cook_camplus.views.fournisseur;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cook_camplus.databinding.FragmentFournisseurBoutiqueBinding;
import com.example.cook_camplus.datas.models.Boutique;
import com.example.cook_camplus.datas.repositories.BoutiqueRepository;

public class FournisseurBoutiqueFragment extends Fragment {

    private FragmentFournisseurBoutiqueBinding binding;
    private BoutiqueRepository boutiqueRepository;
    private Boutique boutiqueActuelle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFournisseurBoutiqueBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boutiqueRepository = new BoutiqueRepository(requireContext());

        chargerMaBoutique();

        binding.btnSaveBoutique.setOnClickListener(v -> sauvegarder());
    }

    private void chargerMaBoutique() {
        SharedPreferences prefs = requireContext().getSharedPreferences("cook_camplus_prefs", 0);
        Long boutiqueId = prefs.getLong("boutique_id", -1L);

        if (boutiqueId == -1L) {
            // Pas encore de boutique liée
            return;
        }

        boutiqueRepository.getBoutiqueById(boutiqueId, new BoutiqueRepository.BoutiqueCallback() {
            @Override
            public void onSuccess(Boutique boutique) {
                if (getActivity() == null) return;
                requireActivity().runOnUiThread(() -> {
                    boutiqueActuelle = boutique;
                    binding.etBoutiqueNom.setText(boutique.getNom());
                    binding.etBoutiqueDescription.setText(boutique.getDescription());
                    binding.etBoutiqueAdresse.setText(boutique.getAdresse());
                    binding.etBoutiqueTelephone.setText(boutique.getTelephone());
                });
            }

            @Override
            public void onError(String error) {
                // Boutique non trouvée, formulaire vide pour créer
            }
        });
    }

    private void sauvegarder() {
        String nom = binding.etBoutiqueNom.getText() != null
                ? binding.etBoutiqueNom.getText().toString().trim() : "";
        String description = binding.etBoutiqueDescription.getText() != null
                ? binding.etBoutiqueDescription.getText().toString().trim() : "";
        String adresse = binding.etBoutiqueAdresse.getText() != null
                ? binding.etBoutiqueAdresse.getText().toString().trim() : "";
        String telephone = binding.etBoutiqueTelephone.getText() != null
                ? binding.etBoutiqueTelephone.getText().toString().trim() : "";

        if (nom.isEmpty()) {
            binding.etBoutiqueNom.setError("Nom requis");
            return;
        }

        binding.progressBoutique.setVisibility(View.VISIBLE);
        binding.btnSaveBoutique.setEnabled(false);

        Boutique b = boutiqueActuelle != null ? boutiqueActuelle : new Boutique();
        b.setNom(nom);
        b.setDescription(description);
        b.setAdresse(adresse);
        b.setTelephone(telephone);

        SharedPreferences prefs = requireContext().getSharedPreferences("cook_camplus_prefs", 0);
        String token = prefs.getString("auth_token", "");

        boutiqueRepository.saveBoutique(b, token, new BoutiqueRepository.BoutiqueCallback() {
            @Override
            public void onSuccess(Boutique boutique) {
                if (getActivity() == null) return;
                requireActivity().runOnUiThread(() -> {
                    binding.progressBoutique.setVisibility(View.GONE);
                    binding.btnSaveBoutique.setEnabled(true);
                    boutiqueActuelle = boutique;
                    prefs.edit().putLong("boutique_id", boutique.getId()).apply();
                    Toast.makeText(requireContext(), "✅ Boutique sauvegardée !", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onError(String error) {
                if (getActivity() == null) return;
                requireActivity().runOnUiThread(() -> {
                    binding.progressBoutique.setVisibility(View.GONE);
                    binding.btnSaveBoutique.setEnabled(true);
                    Toast.makeText(requireContext(), "Erreur: " + error, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
