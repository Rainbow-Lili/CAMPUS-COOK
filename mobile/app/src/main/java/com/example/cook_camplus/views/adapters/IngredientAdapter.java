package com.example.cook_camplus.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cook_camplus.R;
import com.example.cook_camplus.datas.models.RecetteIngredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    
    private final Context context;
    private final List<RecetteIngredient> ingredients;
    
    public IngredientAdapter(Context context, List<RecetteIngredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }
    
    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        RecetteIngredient ingredient = ingredients.get(position);
        holder.bind(ingredient);
    }
    
    @Override
    public int getItemCount() {
        return ingredients.size();
    }
    
    class IngredientViewHolder extends RecyclerView.ViewHolder {
        
        private final TextView tvNom;
        private final TextView tvQuantite;
        
        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.tv_ingredient_nom);
            tvQuantite = itemView.findViewById(R.id.tv_ingredient_quantite);
        }
        
        public void bind(RecetteIngredient recetteIngredient) {
            if (recetteIngredient.getIngredient() != null) {
                tvNom.setText(recetteIngredient.getIngredient().getNom());
            }
            
            tvQuantite.setText(recetteIngredient.getDisplayText());
        }
    }
}
