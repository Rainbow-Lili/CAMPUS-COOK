package com.example.cook_camplus.views.fournisseur;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cook_camplus.databinding.FragmentFournisseurProfilBinding;
import com.example.cook_camplus.views.FournisseurMainActivity;

public class FournisseurProfilFragment extends Fragment {

    private FragmentFournisseurProfilBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFournisseurProfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = requireContext().getSharedPreferences("cook_camplus_prefs", 0);
        String nom = prefs.getString("user_nom", "—");
        String email = prefs.getString("user_email", "—");

        binding.tvProfilNom.setText(nom);
        binding.tvProfilEmail.setText(email);
        binding.tvInfoNom.setText(nom);
        binding.tvInfoEmail.setText(email);

        binding.btnLogoutFournisseur.setOnClickListener(v ->
                FournisseurMainActivity.logout(requireContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
