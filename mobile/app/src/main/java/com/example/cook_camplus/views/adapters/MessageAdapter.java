package com.example.cook_camplus.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cook_camplus.R;
import com.example.cook_camplus.datas.models.Message;
import com.example.cook_camplus.datas.models.PanierItem;

import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_RECU = 1;
    private static final int VIEW_TYPE_ENVOYE = 2;
    private static final int VIEW_TYPE_PANIER = 3;

    private final Context context;
    private final List<Message> messages;
    private final Long userId;

    public MessageAdapter(Context context, List<Message> messages, Long userId) {
        this.context = context;
        this.messages = messages;
        this.userId = userId;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        
        if ("PANIER".equals(message.getType())) {
            return VIEW_TYPE_PANIER;
        } else if (message.getExpediteurId().equals(userId)) {
            return VIEW_TYPE_ENVOYE;
        } else {
            return VIEW_TYPE_RECU;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_PANIER:
                view = LayoutInflater.from(context).inflate(R.layout.item_message_panier, parent, false);
                return new PanierViewHolder(view);
            case VIEW_TYPE_ENVOYE:
                view = LayoutInflater.from(context).inflate(R.layout.item_message_envoye, parent, false);
                return new MessageEnvoyeViewHolder(view);
            case VIEW_TYPE_RECU:
            default:
                view = LayoutInflater.from(context).inflate(R.layout.item_message_recu, parent, false);
                return new MessageRecuViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);

        if (holder instanceof PanierViewHolder) {
            ((PanierViewHolder) holder).bind(message);
        } else if (holder instanceof MessageEnvoyeViewHolder) {
            ((MessageEnvoyeViewHolder) holder).bind(message);
        } else if (holder instanceof MessageRecuViewHolder) {
            ((MessageRecuViewHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    // ViewHolder pour les messages reçus
    static class MessageRecuViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessageContenu;
        TextView tvMessageHeure;

        public MessageRecuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessageContenu = itemView.findViewById(R.id.tvMessageContenu);
            tvMessageHeure = itemView.findViewById(R.id.tvMessageHeure);
        }

        public void bind(Message message) {
            tvMessageContenu.setText(message.getContenu());
            tvMessageHeure.setText(formatHeure(message.getDateEnvoi()));
        }
    }

    // ViewHolder pour les messages envoyés
    static class MessageEnvoyeViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessageContenu;
        TextView tvMessageHeure;

        public MessageEnvoyeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessageContenu = itemView.findViewById(R.id.tvMessageContenu);
            tvMessageHeure = itemView.findViewById(R.id.tvMessageHeure);
        }

        public void bind(Message message) {
            tvMessageContenu.setText(message.getContenu());
            tvMessageHeure.setText(formatHeure(message.getDateEnvoi()));
        }
    }

    // ViewHolder pour les messages de type panier
    class PanierViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutPacksList;
        TextView tvPanierTotal;
        TextView tvStatut;
        TextView tvMessageHeure;

        public PanierViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutPacksList = itemView.findViewById(R.id.layoutPacksList);
            tvPanierTotal = itemView.findViewById(R.id.tvPanierTotal);
            tvStatut = itemView.findViewById(R.id.tvStatut);
            tvMessageHeure = itemView.findViewById(R.id.tvMessageHeure);
        }

        public void bind(Message message) {
            if (message.getPanierData() != null) {
                // Afficher les packs
                layoutPacksList.removeAllViews();
                List<PanierItem> items = message.getPanierData().getItems();
                
                for (PanierItem item : items) {
                    TextView tvPack = new TextView(context);
                    tvPack.setText(String.format(Locale.getDefault(), 
                        "• %s x%d - %.0f FCFA", 
                        item.getPack().getNom(), 
                        item.getQuantite(), 
                        item.getSousTotal()));
                    tvPack.setTextColor(context.getResources().getColor(R.color.textPrimary));
                    tvPack.setPadding(0, 8, 0, 8);
                    layoutPacksList.addView(tvPack);
                }

                // Total
                tvPanierTotal.setText(String.format(Locale.getDefault(), 
                    "%.0f FCFA", 
                    message.getPanierData().getTotal()));

                // Statut
                String statut = message.getPanierData().getStatut();
                tvStatut.setText(getStatutText(statut));
            }

            tvMessageHeure.setText(formatHeure(message.getDateEnvoi()));
        }

        private String getStatutText(String statut) {
            switch (statut) {
                case "EN_ATTENTE":
                    return "⏳ En attente";
                case "ACCEPTE":
                    return "✅ Accepté";
                case "REFUSE":
                    return "❌ Refusé";
                case "LIVRE":
                    return "📦 Livré";
                default:
                    return statut;
            }
        }
    }

    private static String formatHeure(String dateEnvoi) {
        if (dateEnvoi == null || dateEnvoi.isEmpty()) {
            return "";
        }
        // Format simple pour l'instant
        try {
            String[] parts = dateEnvoi.split("T");
            if (parts.length > 1) {
                String[] timeParts = parts[1].split(":");
                if (timeParts.length >= 2) {
                    return timeParts[0] + ":" + timeParts[1];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateEnvoi;
    }
}
