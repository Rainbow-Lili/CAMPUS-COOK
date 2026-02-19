package com.example.cook_camplus.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cook_camplus.R;
import com.google.android.material.button.MaterialButton;

public class SplashActivity extends AppCompatActivity {
    
    private static final String TAG = "SplashActivity";
    private static final long BUTTON_DELAY = 2000;
    private MaterialButton btnCommencer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d(TAG, "onCreate: Starting SplashActivity");
        
        try {
            setContentView(R.layout.activity_splash);
            Log.d(TAG, "onCreate: Layout set successfully");
            
            btnCommencer = findViewById(R.id.btn_commencer);
            
            if (btnCommencer == null) {
                Log.e(TAG, "onCreate: Button not found!");
            } else {
                Log.d(TAG, "onCreate: Button found");
            }
            
            // Afficher le bouton après 2 secondes
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Log.d(TAG, "Handler: Showing button");
                if (btnCommencer != null) {
                    btnCommencer.setVisibility(View.VISIBLE);
                    btnCommencer.setAlpha(0f);
                    btnCommencer.animate()
                        .alpha(1f)
                        .setDuration(500)
                        .start();
                }
            }, BUTTON_DELAY);
            
            // Clic sur le bouton → WelcomeActivity
            if (btnCommencer != null) {
                btnCommencer.setOnClickListener(v -> {
                    Log.d(TAG, "Button clicked: Navigating to WelcomeActivity");
                    Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate: Exception occurred", e);
            e.printStackTrace();
            // En cas d'erreur, aller à WelcomeActivity quand même
            Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
