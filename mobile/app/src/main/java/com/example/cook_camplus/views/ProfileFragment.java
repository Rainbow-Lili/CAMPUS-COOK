package com.example.cook_camplus.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cook_camplus.databinding.FragmentProfileBinding;
import com.example.cook_camplus.datas.repositories.AuthRepository;

public class ProfileFragment extends Fragment {
    
    private FragmentProfileBinding binding;
    private AuthRepository authRepository;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        authRepository = new AuthRepository(requireContext());
        
        displayUserInfo();
        setupListeners();
    }
    
    private void displayUserInfo() {
        String userName = authRepository.getUserName();
        String userEmail = authRepository.getUserEmail();
        
        binding.tvUserName.setText(userName);
        binding.tvUserEmail.setText(userEmail);
    }
    
    private void setupListeners() {
        // Bouton de déconnexion
        binding.btnLogout.setOnClickListener(v -> {
            authRepository.logout();
            
            Toast.makeText(requireContext(), "Déconnexion réussie", Toast.LENGTH_SHORT).show();
            
            // Rediriger vers LoginActivity
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
