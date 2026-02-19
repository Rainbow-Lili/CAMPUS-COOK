package com.example.cook_camplus.views.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cook_camplus.datas.models.Recette;
import com.example.cook_camplus.views.fragments.IngredientsFragment;
import com.example.cook_camplus.views.fragments.InstructionsFragment;
import com.example.cook_camplus.views.fragments.VideoFragment;

public class RecipeTabsAdapter extends FragmentStateAdapter {
    
    private Recette recette;
    private int nombrePersonnes;
    private IngredientsFragment ingredientsFragment;
    private InstructionsFragment instructionsFragment;
    private VideoFragment videoFragment;
    
    public RecipeTabsAdapter(@NonNull FragmentActivity fragmentActivity, Recette recette, int nombrePersonnes) {
        super(fragmentActivity);
        this.recette = recette;
        this.nombrePersonnes = nombrePersonnes;
    }
    
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                if (ingredientsFragment == null) {
                    ingredientsFragment = IngredientsFragment.newInstance(recette, nombrePersonnes);
                }
                return ingredientsFragment;
            case 1:
                if (instructionsFragment == null) {
                    instructionsFragment = InstructionsFragment.newInstance(recette);
                }
                return instructionsFragment;
            case 2:
                if (videoFragment == null) {
                    videoFragment = VideoFragment.newInstance(recette);
                }
                return videoFragment;
            default:
                return IngredientsFragment.newInstance(recette, nombrePersonnes);
        }
    }
    
    @Override
    public int getItemCount() {
        return 3; // Ingrédients, Instructions, Vidéo
    }
    
    public void updateRecette(Recette recette) {
        this.recette = recette;
        if (ingredientsFragment != null) {
            ingredientsFragment.updateRecette(recette);
        }
        if (instructionsFragment != null) {
            instructionsFragment.updateRecette(recette);
        }
        if (videoFragment != null) {
            videoFragment.updateRecette(recette);
        }
    }
}
