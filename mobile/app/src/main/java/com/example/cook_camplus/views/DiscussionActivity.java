package com.example.cook_camplus.views;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cook_camplus.R;
import com.example.cook_camplus.datas.connection.Connection;
import com.example.cook_camplus.datas.models.Boutique;
import com.example.cook_camplus.datas.models.Message;
import com.example.cook_camplus.datas.models.PanierItem;
import com.example.cook_camplus.datas.models.PanierMessage;
import com.example.cook_camplus.datas.repositories.AuthRepository;
import com.example.cook_camplus.datas.repositories.PanierManager;
import com.example.cook_camplus.views.adapters.MessageAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;

public class DiscussionActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private ImageView ivBoutiqueLogo;
    private TextView tvBoutiqueNom;
    private TextView tvBoutiqueStatut;
    private RecyclerView recyclerMessages;
    private EditText etMessage;
    private ImageButton btnEnvoyer;

    private Long boutiqueId;
    private Boutique boutique;
    private Long userId;
    private List<Message> messages;
    private MessageAdapter adapter;
    private boolean envoyerPanier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        initViews();
        loadIntentData();
        setupRecyclerView();
        setupListeners();

        // Si on doit envoyer le panier
        if (envoyerPanier) {
            envoyerPanierAuFournisseur();
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        ivBoutiqueLogo = findViewById(R.id.ivBoutiqueLogo);
        tvBoutiqueNom = findViewById(R.id.tvBoutiqueNom);
        tvBoutiqueStatut = findViewById(R.id.tvBoutiqueStatut);
        recyclerMessages = findViewById(R.id.recyclerMessages);
        etMessage = findViewById(R.id.etMessage);
        btnEnvoyer = findViewById(R.id.btnEnvoyer);
        
        // Configuration de la toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void loadIntentData() {
        boutiqueId = getIntent().getLongExtra("BOUTIQUE_ID", -1);
        envoyerPanier = getIntent().getBooleanExtra("ENVOYER_PANIER", false);
        
        // Récupérer l'utilisateur connecté
        AuthRepository authRepository = new AuthRepository(this);
        userId = authRepository.getUserId();

        // Récupérer la boutique (pour l'instant, données fictives)
        // TODO: Charger depuis l'API
        boutique = new Boutique();
        boutique.setId(boutiqueId);
        boutique.setNom("Boutique " + boutiqueId);
        
        tvBoutiqueNom.setText(boutique.getNom());
        tvBoutiqueStatut.setText("En ligne");

        // Charger le logo si disponible
        if (boutique.getLogoUrl() != null && !boutique.getLogoUrl().isEmpty()) {
            String logoUrl = Connection.getImageUrl(boutique.getLogoUrl());
            ImageLoader imageLoader = Coil.imageLoader(this);
            ImageRequest request = new ImageRequest.Builder(this)
                    .data(logoUrl)
                    .target(ivBoutiqueLogo)
                    .placeholder(R.drawable.ic_store)
                    .error(R.drawable.ic_store)
                    .build();
            imageLoader.enqueue(request);
        } else {
            ivBoutiqueLogo.setImageResource(R.drawable.ic_store);
        }
    }

    private void setupRecyclerView() {
        messages = new ArrayList<>();
        
        // TODO: Charger les messages depuis l'API
        // Pour l'instant, liste vide
        
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true); // Commencer par le bas
        recyclerMessages.setLayoutManager(layoutManager);
        
        adapter = new MessageAdapter(this, messages, userId);
        recyclerMessages.setAdapter(adapter);
    }

    private void setupListeners() {
        btnEnvoyer.setOnClickListener(v -> {
            String contenu = etMessage.getText().toString().trim();
            if (!contenu.isEmpty()) {
                envoyerMessage(contenu);
            }
        });
    }

    private void envoyerMessage(String contenu) {
        // Créer un nouveau message
        Message message = new Message(
            userId,
            null, // ID du fournisseur (à déterminer)
            boutiqueId,
            contenu,
            "TEXT"
        );
        message.setDateEnvoi(getCurrentDateTime());

        // Ajouter à la liste locale
        messages.add(message);
        adapter.notifyItemInserted(messages.size() - 1);
        recyclerMessages.scrollToPosition(messages.size() - 1);

        // Effacer le champ
        etMessage.setText("");

        // TODO: Envoyer au serveur via API

        Toast.makeText(this, "Message envoyé", Toast.LENGTH_SHORT).show();
    }

    private void envoyerPanierAuFournisseur() {
        PanierManager panierManager = PanierManager.getInstance();
        List<PanierItem> items = panierManager.getItemsParBoutique(boutiqueId);

        if (items.isEmpty()) {
            Toast.makeText(this, "Panier vide", Toast.LENGTH_SHORT).show();
            return;
        }

        // Créer une copie de la liste pour éviter les problèmes de référence
        List<PanierItem> itemsCopie = new ArrayList<>();
        for (PanierItem item : items) {
            PanierItem copie = new PanierItem(item.getPack(), item.getQuantite());
            itemsCopie.add(copie);
        }

        // Créer le message de type panier
        PanierMessage panierData = new PanierMessage(
            itemsCopie,
            panierManager.getTotalParBoutique(boutiqueId),
            boutiqueId
        );

        Message message = new Message(
            userId,
            null, // ID du fournisseur
            boutiqueId,
            "Nouvelle commande",
            "PANIER"
        );
        message.setPanierData(panierData);
        message.setDateEnvoi(getCurrentDateTime());

        // Ajouter à la liste
        messages.add(message);
        adapter.notifyItemInserted(messages.size() - 1);
        recyclerMessages.scrollToPosition(messages.size() - 1);

        // Vider le panier de cette boutique
        panierManager.viderPanierBoutique(boutiqueId);

        // TODO: Envoyer au serveur via API

        Toast.makeText(this, "Commande envoyée au fournisseur !", Toast.LENGTH_SHORT).show();
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
