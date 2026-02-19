package com.example.cook_camplus.datas.models;

import java.io.Serializable;

public class Message implements Serializable {
    private Long id;
    private Long expediteurId;
    private Long destinataireId;
    private Long boutiqueId;
    private String contenu;
    private String type; // TEXT, PANIER
    private String dateEnvoi;
    private Boolean lu;
    private PanierMessage panierData; // Si type = PANIER
    
    public Message() {
    }

    public Message(Long expediteurId, Long destinataireId, Long boutiqueId, String contenu, String type) {
        this.expediteurId = expediteurId;
        this.destinataireId = destinataireId;
        this.boutiqueId = boutiqueId;
        this.contenu = contenu;
        this.type = type;
        this.lu = false;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExpediteurId() {
        return expediteurId;
    }

    public void setExpediteurId(Long expediteurId) {
        this.expediteurId = expediteurId;
    }

    public Long getDestinataireId() {
        return destinataireId;
    }

    public void setDestinataireId(Long destinataireId) {
        this.destinataireId = destinataireId;
    }

    public Long getBoutiqueId() {
        return boutiqueId;
    }

    public void setBoutiqueId(Long boutiqueId) {
        this.boutiqueId = boutiqueId;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(String dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public Boolean getLu() {
        return lu;
    }

    public void setLu(Boolean lu) {
        this.lu = lu;
    }

    public PanierMessage getPanierData() {
        return panierData;
    }

    public void setPanierData(PanierMessage panierData) {
        this.panierData = panierData;
    }
}
