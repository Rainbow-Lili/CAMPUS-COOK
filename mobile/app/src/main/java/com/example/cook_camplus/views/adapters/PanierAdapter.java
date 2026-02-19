package com.example.cook_camplus.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cook_camplus.R;
import com.example.cook_camplus.datas.connection.Connection;
import com.example.cook_camplus.datas.models.PanierItem;

import java.util.List;
import java.util.Locale;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;

public class PanierAdapter extends RecyclerView.Adapter<PanierAdapter.PanierViewHolder> {

    private final Context context;
    private final List<PanierItem> items;
    private OnPanierItemListener listener;

    public interface OnPanierItemListener {
        void onQuantiteChanged(PanierItem item, int nouvelleQuantite);
        void onItemSupprimer(PanierItem item);
    }

    public PanierAdapter(Context context, List<PanierItem> items) {
        this.context = context;
        this.items = items;
    }

    public void setOnPanierItemListener(OnPanierItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PanierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_panier, parent, false);
        return new PanierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PanierViewHolder holder, int position) {
        PanierItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class PanierViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivPackImage;
        private final TextView tvPackNom;
        private final TextView tvPackPrix;
        private final TextView tvQuantite;
        private final ImageButton btnDiminuer;
        private final ImageButton btnAugmenter;
        private final ImageButton btnSupprimer;

        public PanierViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPackImage = itemView.findViewById(R.id.ivPackImage);
            tvPackNom = itemView.findViewById(R.id.tvPackNom);
            tvPackPrix = itemView.findViewById(R.id.tvPackPrix);
            tvQuantite = itemView.findViewById(R.id.tvQuantite);
            btnDiminuer = itemView.findViewById(R.id.btnDiminuer);
            btnAugmenter = itemView.findViewById(R.id.btnAugmenter);
            btnSupprimer = itemView.findViewById(R.id.btnSupprimer);
        }

        public void bind(PanierItem item) {
            // Nom du pack
            tvPackNom.setText(item.getPack().getNom());

            // Prix
            tvPackPrix.setText(String.format(Locale.getDefault(), "%.0f FCFA", item.getPack().getPrix()));

            // Quantité
            tvQuantite.setText(String.valueOf(item.getQuantite()));

            // Image
            if (item.getPack().getImageUrl() != null && !item.getPack().getImageUrl().isEmpty()) {
                String imageUrl = Connection.getImageUrl(item.getPack().getImageUrl());
                ImageLoader imageLoader = Coil.imageLoader(context);
                ImageRequest request = new ImageRequest.Builder(context)
                        .data(imageUrl)
                        .target(ivPackImage)
                        .placeholder(R.drawable.ic_pack)
                        .error(R.drawable.ic_pack)
                        .build();
                imageLoader.enqueue(request);
            } else {
                ivPackImage.setImageResource(R.drawable.ic_pack);
            }

            // Bouton diminuer
            btnDiminuer.setOnClickListener(v -> {
                if (listener != null) {
                    int nouvelleQuantite = item.getQuantite() - 1;
                    listener.onQuantiteChanged(item, nouvelleQuantite);
                }
            });

            // Bouton augmenter
            btnAugmenter.setOnClickListener(v -> {
                if (listener != null) {
                    int nouvelleQuantite = item.getQuantite() + 1;
                    listener.onQuantiteChanged(item, nouvelleQuantite);
                }
            });

            // Bouton supprimer
            btnSupprimer.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemSupprimer(item);
                }
            });
        }
    }
}
