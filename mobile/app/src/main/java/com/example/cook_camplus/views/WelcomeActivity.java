package com.example.cook_camplus.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cook_camplus.R;

public class WelcomeActivity extends AppCompatActivity {
    
    private static final long WELCOME_DELAY = 4000; // 4 secondes
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        // Après 4 secondes, naviguer vers LoginActivity temporairement
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, WELCOME_DELAY);
    }
}
