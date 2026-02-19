package com.example.cook_camplus.views.fournisseur;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cook_camplus.databinding.FragmentFournisseurPacksBinding;
import com.example.cook_camplus.datas.models.Pack;
import com.example.cook_camplus.datas.repositories.PackRepository;
import com.example.cook_camplus.views.PackDetailActivity;
import com.example.cook_camplus.views.adapters.PackAdapter;

import java.util.ArrayList;
import java.util.List;

public class FournisseurPacksFragment extends Fragment {

    private FragmentFournisseurPacksBinding binding;
    private PackRepository packRepository;
    private PackAdapter adapter;
    private List<Pack> packs = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFournisseurPacksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        packRepository = new PackRepository(requireContext());

        binding.recyclerFournisseurPacks.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new PackAdapter(requireContext(), packs);
        adapter.setOnPackClickListener(pack -> {
            Intent intent = new Intent(getActivity(), PackDetailActivity.class);
            intent.putExtra("pack", pack);
            startActivity(intent);
        });
        binding.recyclerFournisseurPacks.setAdapter(adapter);

        SharedPreferences prefs = requireContext().getSharedPreferences("cook_camplus_prefs", 0);
        Long boutiqueId = prefs.getLong("boutique_id", -1L);

        if (boutiqueId != -1L) {
            chargerPacks(boutiqueId);
        } else {
            binding.tvNoPacks.setVisibility(View.VISIBLE);
        }

        binding.btnAddPack.setOnClickListener(v -> {
            // TODO: ouvrir dialog/activity création pack
            android.widget.Toast.makeText(requireContext(),
                    "Fonctionnalité en cours de développement", android.widget.Toast.LENGTH_SHORT).show();
        });
    }

    private void chargerPacks(Long boutiqueId) {
        packRepository.getPacksByBoutique(boutiqueId, new PackRepository.PackListCallback() {
            @Override
            public void onSuccess(List<Pack> result) {
                if (getActivity() == null) return;
                requireActivity().runOnUiThread(() -> {
                    packs.clear();
                    packs.addAll(result);
                    adapter.notifyDataSetChanged();
                    binding.tvNoPacks.setVisibility(packs.isEmpty() ? View.VISIBLE : View.GONE);
                });
            }

            @Override
            public void onError(String error) {
                if (getActivity() == null) return;
                requireActivity().runOnUiThread(() ->
                        binding.tvNoPacks.setVisibility(View.VISIBLE));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
