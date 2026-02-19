package com.example.cook_camplus.datas.models;

public class Video {
    private Long id;
    private String titre;
    private String url;
    private String description;
    private Integer duree;
    private String dateAjout;

    public Video() {}

    public Video(String titre, String url) {
        this.titre = titre;
        this.url = url;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getDuree() { return duree; }
    public void setDuree(Integer duree) { this.duree = duree; }

    public String getDateAjout() { return dateAjout; }
    public void setDateAjout(String dateAjout) { this.dateAjout = dateAjout; }

    // Helper
    public String getDureeFormatee() {
        if (duree == null) return "0:00";
        int minutes = duree / 60;
        int secondes = duree % 60;
        return String.format("%d:%02d", minutes, secondes);
    }

    public String getFullUrl(String baseUrl) {
        if (url != null && url.startsWith("http")) {
            return url;
        }
        return baseUrl + "/uploads/videos/" + url;
    }
}
