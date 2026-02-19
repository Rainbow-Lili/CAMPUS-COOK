package com.example.cook_camplus.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class RecetteAdapter extends RecyclerView.Adapter<RecetteAdapter.RecetteViewHolder> {
    
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
    
    public RecetteAdapter(Context context, List<Recette> recettes) {
        this.context = context;
        this.recettes = recettes;
    }
    
    @NonNull
    @Override
    public RecetteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recette, parent, false);
        return new RecetteViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull RecetteViewHolder holder, int position) {
        Recette recette = recettes.get(position);
        
        // Appliquer la couleur alternée
        int colorIndex = position % backgroundColors.length;
        int color = ContextCompat.getColor(context, backgroundColors[colorIndex]);
        holder.cardContent.setBackgroundColor(color);
        
        holder.bind(recette);
    }
    
    @Override
    public int getItemCount() {
        return recettes.size();
    }
    
    class RecetteViewHolder extends RecyclerView.ViewHolder {
        
        private final View cardContent;
        private final ImageView ivRecette;
        private final TextView tvNom;
        private final TextView tvDescription;
        private final TextView tvTemps;
        private final TextView tvDifficulte;
        private final TextView tvCout;
        private final ImageView ivFavori;
        
        public RecetteViewHolder(@NonNull View itemView) {
            super(itemView);
            cardContent = itemView.findViewById(R.id.card_content);
            ivRecette = itemView.findViewById(R.id.iv_recette);
            tvNom = itemView.findViewById(R.id.tv_nom);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvTemps = itemView.findViewById(R.id.tv_temps);
            tvDifficulte = itemView.findViewById(R.id.tv_difficulte);
            tvCout = itemView.findViewById(R.id.tv_cout);
            ivFavori = itemView.findViewById(R.id.iv_favori);
        }
        
        public void bind(Recette recette) {
            // Nom de la recette
            tvNom.setText(recette.getNom());
            
            // Description (max 2 lignes)
            if (recette.getDescription() != null && !recette.getDescription().isEmpty()) {
                tvDescription.setText(recette.getDescription());
                tvDescription.setVisibility(View.VISIBLE);
            } else {
                tvDescription.setVisibility(View.GONE);
            }
            
            // Temps de préparation
            tvTemps.setText(recette.getTempsFormate());
            tvTemps.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_timer, 0, 0, 0);
            tvTemps.setCompoundDrawablePadding(8);
            
            // Difficulté avec icône
            tvDifficulte.setText(recette.getDifficulte());
            tvDifficulte.setCompoundDrawablesWithIntrinsicBounds(recette.getDifficulteIconResId(), 0, 0, 0);
            tvDifficulte.setCompoundDrawablePadding(8);
            
            // Coût total
            tvCout.setText(recette.getCoutTotalFormate());
            
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
                    .target(ivRecette)
                    .build();
                imageLoader.enqueue(request);
            }
            
            // Click sur la card -> ouvrir le détail
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra("recette_id", recette.getId());
                intent.putExtra("recette_nom", recette.getNom());
                context.startActivity(intent);
            });
            
            // Click sur le favori
            ivFavori.setOnClickListener(v -> {
                // TODO: Implémenter ajout/retrait des favoris
            });
        }
    }
}
