package com.example.cook_camplus.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cook_camplus.R;
import com.example.cook_camplus.datas.connection.Connection;
import com.example.cook_camplus.datas.models.Recette;
import com.example.cook_camplus.views.RecipeDetailActivity;

import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;

public class HorizontalRecetteAdapter extends RecyclerView.Adapter<HorizontalRecetteAdapter.HorizontalRecetteViewHolder> {
    
    private final Context context;
    private final List<Recette> recettes;
    
    // Couleur sombre uniforme pour le dark theme
    private final int[] backgroundColors = {
        R.color.darkCard,
        R.color.darkCard,
        R.color.darkCard,
        R.color.darkCard,
        R.color.darkCard,
        R.color.darkCard
    };
    
    public HorizontalRecetteAdapter(Context context, List<Recette> recettes) {
        this.context = context;
        this.recettes = recettes;
    }
    
    @NonNull
    @Override
    public HorizontalRecetteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe_card, parent, false);
        return new HorizontalRecetteViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull HorizontalRecetteViewHolder holder, int position) {
        Recette recette = recettes.get(position);
        
        // Appliquer la couleur alternée
        int colorIndex = position % backgroundColors.length;
        int color = ContextCompat.getColor(context, backgroundColors[colorIndex]);
        holder.cardBackground.setBackgroundColor(color);
        
        holder.bind(recette);
    }
    
    @Override
    public int getItemCount() {
        return recettes.size();
    }
    
    class HorizontalRecetteViewHolder extends RecyclerView.ViewHolder {
        
        private final ConstraintLayout cardBackground;
        private final ImageView ivRecipeImage;
        private final TextView tvRecipeTitle;
        private final TextView tvTime;
        private final TextView tvDifficulty;
        private final ImageView ivHeart;
        private final ImageView ivPlus;
        private final LinearLayout badgeAllergy;
        
        public HorizontalRecetteViewHolder(@NonNull View itemView) {
            super(itemView);
            cardBackground = itemView.findViewById(R.id.cardBackground);
            ivRecipeImage = itemView.findViewById(R.id.iv_recipe_image);
            tvRecipeTitle = itemView.findViewById(R.id.tv_recipe_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvDifficulty = itemView.findViewById(R.id.tv_difficulty);
            ivHeart = itemView.findViewById(R.id.iv_heart);
            ivPlus = itemView.findViewById(R.id.iv_plus);
            badgeAllergy = itemView.findViewById(R.id.badge_allergy);
        }
        
        public void bind(Recette recette) {
            // Titre de la recette
            tvRecipeTitle.setText(recette.getNom());
            
            // Temps de préparation
            tvTime.setText(recette.getTempsFormate());
            tvTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_timer, 0, 0, 0);
            tvTime.setCompoundDrawablePadding(8);
            
            // Difficulté avec icône
            tvDifficulty.setText(recette.getDifficulte());
            tvDifficulty.setCompoundDrawablesWithIntrinsicBounds(recette.getDifficulteIconResId(), 0, 0, 0);
            tvDifficulty.setCompoundDrawablePadding(8);
            
            // Image de la recette avec Coil
            String imageUrl = recette.getImagePrincipale();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                String fullUrl = Connection.getImageUrl(imageUrl);
                ImageLoader imageLoader = Coil.imageLoader(context);
                ImageRequest request = new ImageRequest.Builder(context)
                    .data(fullUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .target(ivRecipeImage)
                    .build();
                imageLoader.enqueue(request);
            }
            
            // Badge allergie
            if (recette.hasUserAllergy()) {
                badgeAllergy.setVisibility(View.VISIBLE);
            } else {
                badgeAllergy.setVisibility(View.GONE);
            }
            
            // Click sur la card -> ouvrir le détail
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra("recette_id", recette.getId());
                intent.putExtra("recette_nom", recette.getNom());
                context.startActivity(intent);
            });
            
            // Click sur le favori (heart icon)
            ivHeart.setOnClickListener(v -> {
                // TODO: Implémenter ajout/retrait des favoris
            });
            
            // Click sur le plus icon
            ivPlus.setOnClickListener(v -> {
                // TODO: Implémenter ajout au menu planning
            });
        }
    }
}
