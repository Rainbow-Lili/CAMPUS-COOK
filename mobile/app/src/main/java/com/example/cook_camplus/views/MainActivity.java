package com.example.cook_camplus.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.cook_camplus.R;
import com.example.cook_camplus.databinding.ActivityMainBinding;
import com.example.cook_camplus.datas.repositories.AuthRepository;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    
    private ActivityMainBinding binding;
    private AuthRepository authRepository;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        authRepository = new AuthRepository(this);
        
        // Vérifier l'authentification
        if (!authRepository.isLoggedIn()) {
            redirectToLogin();
            return;
        }
        
        setupBottomNavigation();
        
        // Afficher le fragment Home par défaut
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }
    
    private void setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    fragment = new HomeFragment();
                } else if (itemId == R.id.navigation_favoris) {
                    fragment = new FavorisFragment();
                } else if (itemId == R.id.navigation_marketplace) {
                    // Ouvrir MarketplaceActivity au lieu d'un fragment
                    Intent intent = new Intent(MainActivity.this, MarketplaceActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    fragment = new ProfileFragment();
                }
                
                return fragment != null && loadFragment(fragment);
            }
        });
    }
    
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
            return true;
        }
        return false;
    }
    
    private void redirectToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
