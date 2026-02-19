package com.example.cook_camplus.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cook_camplus.R;
import com.example.cook_camplus.datas.connection.Connection;
import com.example.cook_camplus.datas.models.Pack;

import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;

public class PackAdapter extends RecyclerView.Adapter<PackAdapter.PackViewHolder> {
    
    private final Context context;
    private final List<Pack> packs;
    private OnPackClickListener listener;
    private OnPackAddToPanierListener addToPanierListener;
    
    public interface OnPackClickListener {
        void onPackClick(Pack pack);
    }
    
    public interface OnPackAddToPanierListener {
        void onAddToPanier(Pack pack);
    }
    
    public PackAdapter(Context context, List<Pack> packs) {
        this.context = context;
        this.packs = packs;
    }
    
    public void setOnPackClickListener(OnPackClickListener listener) {
        this.listener = listener;
    }
    
    public void setOnPackAddToPanierListener(OnPackAddToPanierListener listener) {
        this.addToPanierListener = listener;
    }
    
    @NonNull
    @Override
    public PackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pack, parent, false);
        return new PackViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull PackViewHolder holder, int position) {
        Pack pack = packs.get(position);
        holder.bind(pack);
    }
    
    @Override
    public int getItemCount() {
        return packs.size();
    }
    
    class PackViewHolder extends RecyclerView.ViewHolder {
        
        private final ImageView ivPackImage;
        private final TextView tvBudget;
        private final TextView tvPackNom;
        private final TextView tvPackDescription;
        private final TextView tvPackPrix;
        private final TextView tvPackStock;
        private final TextView tvBoutiqueNom;
        private final Button btnAjouterPanier;
        
        public PackViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPackImage = itemView.findViewById(R.id.iv_pack_image);
            tvBudget = itemView.findViewById(R.id.tv_budget);
            tvPackNom = itemView.findViewById(R.id.tv_pack_nom);
            tvPackDescription = itemView.findViewById(R.id.tv_pack_description);
            tvPackPrix = itemView.findViewById(R.id.tv_pack_prix);
            tvPackStock = itemView.findViewById(R.id.tv_pack_stock);
            tvBoutiqueNom = itemView.findViewById(R.id.tv_boutique_nom);
            btnAjouterPanier = itemView.findViewById(R.id.btn_ajouter_panier);
        }
        
        public void bind(Pack pack) {
            // Nom du pack
            tvPackNom.setText(pack.getNom());
            
            // Description
            if (pack.getDescription() != null && !pack.getDescription().isEmpty()) {
                tvPackDescription.setText(pack.getDescription());
                tvPackDescription.setVisibility(View.VISIBLE);
            } else {
                tvPackDescription.setVisibility(View.GONE);
            }
            
            // Budget (badge)
            if (pack.getBudget() != null) {
                tvBudget.setText(pack.getBudget());
                tvBudget.setVisibility(View.VISIBLE);
            } else {
                tvBudget.setVisibility(View.GONE);
            }
            
            // Prix
            tvPackPrix.setText(pack.getPrixFormate());
            
            // Stock
            if (pack.getStock() != null) {
                if (pack.getStock() > 0) {
                    tvPackStock.setText("En stock: " + pack.getStock());
                    tvPackStock.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
                } else {
                    tvPackStock.setText("Rupture de stock");
                    tvPackStock.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
                }
            }
            
            // Nom de la boutique
            if (pack.getBoutique() != null && pack.getBoutique().getNom() != null) {
                tvBoutiqueNom.setText("🏪 " + pack.getBoutique().getNom());
                tvBoutiqueNom.setVisibility(View.VISIBLE);
            } else {
                tvBoutiqueNom.setVisibility(View.GONE);
            }
            
            // Image du pack avec Coil
            String imageUrl = pack.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                String fullUrl = Connection.getImageUrl(imageUrl);
                ImageLoader imageLoader = Coil.imageLoader(context);
                ImageRequest request = new ImageRequest.Builder(context)
                    .data(fullUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_pack)
                    .error(R.drawable.ic_pack)
                    .target(ivPackImage)
                    .build();
                imageLoader.enqueue(request);
            } else {
                ivPackImage.setImageResource(R.drawable.ic_pack);
            }
            
            // Click sur la card
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPackClick(pack);
                }
            });
            
            // Bouton Ajouter au panier
            btnAjouterPanier.setOnClickListener(v -> {
                if (addToPanierListener != null) {
                    addToPanierListener.onAddToPanier(pack);
                }
            });
        }
    }
}
