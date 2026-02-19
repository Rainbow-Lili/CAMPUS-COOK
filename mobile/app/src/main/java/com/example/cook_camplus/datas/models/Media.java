package com.example.cook_camplus.datas.models;

public class Media {
    private Long id;
    private String url;
    private String type;
    private String description;
    private Integer ordre;
    private String dateAjout;

    public Media() {}

    public Media(String url, String type) {
        this.url = url;
        this.type = type;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getOrdre() { return ordre; }
    public void setOrdre(Integer ordre) { this.ordre = ordre; }

    public String getDateAjout() { return dateAjout; }
    public void setDateAjout(String dateAjout) { this.dateAjout = dateAjout; }

    // Helper
    public boolean isImage() {
        return type != null && type.toLowerCase().contains("image");
    }

    public String getFullUrl(String baseUrl) {
        if (url != null && url.startsWith("http")) {
            return url;
        }
        return baseUrl + "/uploads/" + url;
    }
}
