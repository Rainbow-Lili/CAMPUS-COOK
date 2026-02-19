package com.example.cook_camplus.views.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cook_camplus.R;
import com.example.cook_camplus.datas.models.Etape;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class EtapeAdapter extends RecyclerView.Adapter<EtapeAdapter.EtapeViewHolder> {
    
    private final Context context;
    private final List<Etape> etapes;
    
    public EtapeAdapter(Context context, List<Etape> etapes) {
        this.context = context;
        this.etapes = etapes;
    }
    
    @NonNull
    @Override
    public EtapeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_etape, parent, false);
        return new EtapeViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull EtapeViewHolder holder, int position) {
        Etape etape = etapes.get(position);
        holder.bind(etape, position);
    }
    
    @Override
    public int getItemCount() {
        return etapes.size();
    }
    
    class EtapeViewHolder extends RecyclerView.ViewHolder {
        
        private final CardView cardEtape;
        private final TextView tvBadgeNumero;
        private final TextView tvDescription;
        private final TextView tvDuree;
        
        public EtapeViewHolder(@NonNull View itemView) {
            super(itemView);
            cardEtape = itemView.findViewById(R.id.card_etape);
            tvBadgeNumero = itemView.findViewById(R.id.tv_badge_numero);
            tvDescription = itemView.findViewById(R.id.tv_etape_description);
            tvDuree = itemView.findViewById(R.id.tv_etape_duree);
        }
        
        public void bind(Etape etape, int position) {
            // Numéro dans le badge circulaire
            tvBadgeNumero.setText(String.valueOf(etape.getNumeroEtape()));
            
            // Description de l'instruction (preview sur 2 lignes)
            tvDescription.setText(etape.getDescription());
            
            // Durée estimée (optionnelle)
            if (etape.getDureeEstimee() != null && etape.getDureeEstimee() > 0) {
                tvDuree.setText(etape.getDureeFormatee());
                tvDuree.setVisibility(View.VISIBLE);
            } else {
                tvDuree.setVisibility(View.GONE);
            }
            
            // Click listener pour afficher le dialog de détails
            cardEtape.setOnClickListener(v -> afficherDialogDetails(etape, position));
        }
    }
    
    private void afficherDialogDetails(Etape etape, int position) {
        // Créer le dialog customisé
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_etape_detail, null);
        builder.setView(dialogView);
        
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        
        // Récupérer les vues du dialog
        TextView tvTitre = dialogView.findViewById(R.id.tv_dialog_etape_numero);
        TextView tvDuree = dialogView.findViewById(R.id.tv_dialog_duree);
        TextView tvDescription = dialogView.findViewById(R.id.tv_dialog_description);
        TextView tvMessage = dialogView.findViewById(R.id.tv_message);
        MaterialButton btnCompris = dialogView.findViewById(R.id.btn_fermer);
        
        // Remplir les informations
        tvTitre.setText("Étape " + etape.getNumeroEtape());
        
        if (etape.getDureeEstimee() != null && etape.getDureeEstimee() > 0) {
            tvDuree.setText(etape.getDureeFormatee());
            tvDuree.setVisibility(View.VISIBLE);
        } else {
            tvDuree.setVisibility(View.GONE);
        }
        
        tvDescription.setText(etape.getDescription());
        
        // Générer un message motivant selon la position
        String messageMotivant = genererMessageMotivant(position);
        tvMessage.setText(messageMotivant);
        
        // Fermer le dialog
        btnCompris.setOnClickListener(v -> dialog.dismiss());
        
        dialog.show();
    }
    
    private String genererMessageMotivant(int position) {
        int numeroEtape = position + 1;
        int totalEtapes = etapes.size();
        
        if (numeroEtape == totalEtapes) {
            // Dernière étape
            return "Dernière étape ! Vous avez presque fini, bon appétit !";
        } else {
            // Étapes intermédiaires
            int prochaineEtape = numeroEtape + 1;
            return "Passons à l'étape " + prochaineEtape + " !";
        }
    }
}
