package com.example.cook_camplus.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cook_camplus.R;
import com.google.android.material.button.MaterialButton;

public class BienvenuActivity extends AppCompatActivity {
    
    private static final String TAG = "BienvenuActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d(TAG, "onCreate: Starting BienvenuActivity");
        
        try {
            setContentView(R.layout.activity_bienvenu);
            Log.d(TAG, "onCreate: Layout set successfully");
            
            MaterialButton btnContinue = findViewById(R.id.btn_continue);
            if (btnContinue == null) {
                Log.e(TAG, "onCreate: Button not found!");
            } else {
                Log.d(TAG, "onCreate: Button found");
                btnContinue.setOnClickListener(v -> {
                    Log.d(TAG, "Button clicked: Navigating to LoginActivity");
                    Intent intent = new Intent(BienvenuActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate: Exception", e);
            e.printStackTrace();
            // En cas d'erreur, aller directement au login
            Intent intent = new Intent(BienvenuActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
