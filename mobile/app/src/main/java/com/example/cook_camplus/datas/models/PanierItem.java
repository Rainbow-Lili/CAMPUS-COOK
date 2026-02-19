package com.example.cook_camplus.datas.models;

import java.io.Serializable;

public class PanierItem implements Serializable {
    private Pack pack;
    private int quantite;
    private Long boutiqueId;

    public PanierItem() {
    }

    public PanierItem(Pack pack, int quantite) {
        this.pack = pack;
        this.quantite = quantite;
        this.boutiqueId = pack.getBoutiqueId();
    }

    // Getters & Setters
    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Long getBoutiqueId() {
        return boutiqueId;
    }

    public void setBoutiqueId(Long boutiqueId) {
        this.boutiqueId = boutiqueId;
    }

    public double getSousTotal() {
        return pack.getPrix() * quantite;
    }
}
