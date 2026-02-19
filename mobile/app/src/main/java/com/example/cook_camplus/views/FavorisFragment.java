package com.example.cook_camplus.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cook_camplus.databinding.FragmentFavorisBinding;
import com.example.cook_camplus.datas.models.Favori;
import com.example.cook_camplus.datas.repositories.FavoriRepository;

import java.util.ArrayList;
import java.util.List;

public class FavorisFragment extends Fragment {
    
    private FragmentFavorisBinding binding;
    private FavoriRepository favoriRepository;
    private List<Favori> favorisList;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavorisBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        favoriRepository = new FavoriRepository(requireContext());
        favorisList = new ArrayList<>();
        
        setupRecyclerView();
        loadFavoris();
    }
    
    private void setupRecyclerView() {
        binding.recyclerViewFavoris.setLayoutManager(new LinearLayoutManager(requireContext()));
        // TODO: Créer et attacher l'adapter
    }
    
    private void loadFavoris() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.tvEmptyState.setVisibility(View.GONE);
        
        favoriRepository.getFavoris(new FavoriRepository.FavoriListCallback() {
            @Override
            public void onSuccess(List<Favori> favoris) {
                if (getActivity() == null) return;
                
                getActivity().runOnUiThread(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    favorisList.clear();
                    favorisList.addAll(favoris);
                    
                    if (favoris.isEmpty()) {
                        binding.tvEmptyState.setVisibility(View.VISIBLE);
                    } else {
                        binding.tvEmptyState.setVisibility(View.GONE);
                        // TODO: Notifier l'adapter
                    }
                });
            }
            
            @Override
            public void onError(String error) {
                if (getActivity() == null) return;
                
                getActivity().runOnUiThread(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.tvEmptyState.setText(error);
                    binding.tvEmptyState.setVisibility(View.VISIBLE);
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
