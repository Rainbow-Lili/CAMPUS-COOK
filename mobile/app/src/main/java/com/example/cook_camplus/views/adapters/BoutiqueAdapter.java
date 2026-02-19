package com.example.cook_camplus.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cook_camplus.R;
import com.example.cook_camplus.datas.connection.Connection;
import com.example.cook_camplus.datas.models.Boutique;

import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;

public class BoutiqueAdapter extends RecyclerView.Adapter<BoutiqueAdapter.BoutiqueViewHolder> {
    
    private final Context context;
    private final List<Boutique> boutiques;
    private OnBoutiqueClickListener listener;
    
    public interface OnBoutiqueClickListener {
        void onBoutiqueClick(Boutique boutique);
    }
    
    public BoutiqueAdapter(Context context, List<Boutique> boutiques) {
        this.context = context;
        this.boutiques = boutiques;
    }
    
    public void setOnBoutiqueClickListener(OnBoutiqueClickListener listener) {
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public BoutiqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_boutique, parent, false);
        return new BoutiqueViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull BoutiqueViewHolder holder, int position) {
        Boutique boutique = boutiques.get(position);
        holder.bind(boutique);
    }
    
    @Override
    public int getItemCount() {
        return boutiques.size();
    }
    
    class BoutiqueViewHolder extends RecyclerView.ViewHolder {
        
        private final ImageView ivBoutiqueLogo;
        private final TextView tvBoutiqueNom;
        private final TextView tvBoutiqueCategorie;
        private final TextView tvBoutiqueDescription;
        private final TextView tvBoutiqueNote;
        private final TextView tvBoutiqueAdresse;
        
        public BoutiqueViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBoutiqueLogo = itemView.findViewById(R.id.iv_boutique_logo);
            tvBoutiqueNom = itemView.findViewById(R.id.tv_boutique_nom);
            tvBoutiqueCategorie = itemView.findViewById(R.id.tv_boutique_categorie);
            tvBoutiqueDescription = itemView.findViewById(R.id.tv_boutique_description);
            tvBoutiqueNote = itemView.findViewById(R.id.tv_boutique_note);
            tvBoutiqueAdresse = itemView.findViewById(R.id.tv_boutique_adresse);
        }
        
        public void bind(Boutique boutique) {
            // Nom de la boutique
            tvBoutiqueNom.setText(boutique.getNom());
            
            // Type/Catégorie
            String categorieText = boutique.estBoutiqueSpecialisee() 
                ? "Boutique spécialisée" 
                : boutique.getCategorie() != null ? boutique.getCategorie() : "Boutique";
            tvBoutiqueCategorie.setText(categorieText);
            
            // Description
            if (boutique.getDescription() != null && !boutique.getDescription().isEmpty()) {
                tvBoutiqueDescription.setText(boutique.getDescription());
                tvBoutiqueDescription.setVisibility(View.VISIBLE);
            } else {
                tvBoutiqueDescription.setVisibility(View.GONE);
            }
            
            // Note
            tvBoutiqueNote.setText(boutique.getNoteFormatee());
            
            // Adresse
            if (boutique.getAdresse() != null && !boutique.getAdresse().isEmpty()) {
                tvBoutiqueAdresse.setText(boutique.getAdresse());
                tvBoutiqueAdresse.setVisibility(View.VISIBLE);
            } else {
                tvBoutiqueAdresse.setVisibility(View.GONE);
            }
            
            // Logo de la boutique avec Coil
            String logoUrl = boutique.getLogoUrl();
            if (logoUrl != null && !logoUrl.isEmpty()) {
                String fullUrl = Connection.getImageUrl(logoUrl);
                ImageLoader imageLoader = Coil.imageLoader(context);
                ImageRequest request = new ImageRequest.Builder(context)
                    .data(fullUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_store)
                    .error(R.drawable.ic_store)
                    .target(ivBoutiqueLogo)
                    .build();
                imageLoader.enqueue(request);
            } else {
                ivBoutiqueLogo.setImageResource(R.drawable.ic_store);
            }
            
            // Click sur la card
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onBoutiqueClick(boutique);
                }
            });
        }
    }
}
