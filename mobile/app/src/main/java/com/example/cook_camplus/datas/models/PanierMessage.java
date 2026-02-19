package com.example.cook_camplus.datas.models;

import java.io.Serializable;
import java.util.List;

public class PanierMessage implements Serializable {
    private List<PanierItem> items;
    private double total;
    private Long boutiqueId;
    private String statut; // EN_ATTENTE, ACCEPTE, REFUSE, LIVRE

    public PanierMessage() {
    }

    public PanierMessage(List<PanierItem> items, double total, Long boutiqueId) {
        this.items = items;
        this.total = total;
        this.boutiqueId = boutiqueId;
        this.statut = "EN_ATTENTE";
    }

    // Getters & Setters
    public List<PanierItem> getItems() {
        return items;
    }

    public void setItems(List<PanierItem> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Long getBoutiqueId() {
        return boutiqueId;
    }

    public void setBoutiqueId(Long boutiqueId) {
        this.boutiqueId = boutiqueId;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
