package com.example.cook_camplus.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.cook_camplus.R;
import com.example.cook_camplus.databinding.ActivityFournisseurMainBinding;
import com.example.cook_camplus.views.fournisseur.FournisseurBoutiqueFragment;
import com.example.cook_camplus.views.fournisseur.FournisseurHomeFragment;
import com.example.cook_camplus.views.fournisseur.FournisseurPacksFragment;
import com.example.cook_camplus.views.fournisseur.FournisseurProfilFragment;

public class FournisseurMainActivity extends AppCompatActivity {

    private ActivityFournisseurMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFournisseurMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Fragment par défaut
        if (savedInstanceState == null) {
            loadFragment(new FournisseurHomeFragment());
        }

        binding.fournisseurBottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment;
            int id = item.getItemId();

            if (id == R.id.nav_fournisseur_boutique) {
                fragment = new FournisseurBoutiqueFragment();
            } else if (id == R.id.nav_fournisseur_packs) {
                fragment = new FournisseurPacksFragment();
            } else if (id == R.id.nav_fournisseur_profil) {
                fragment = new FournisseurProfilFragment();
            } else {
                fragment = new FournisseurHomeFragment();
            }
            return loadFragment(fragment);
        });
    }

    private boolean loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fournisseur_nav_host_fragment, fragment)
                .commit();
        return true;
    }

    public static void logout(android.content.Context context) {
        SharedPreferences prefs = context.getSharedPreferences("cook_camplus_prefs", MODE_PRIVATE);
        prefs.edit().clear().apply();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
