package com.example.cook_camplus.datas.connection;

public class Connection {
    
    // URL de base du serveur backend
    private static final String BASE_URL = "http://10.0.2.2:8080/cookplusserver/rs/";
    
    // Note: 10.0.2.2 est l'adresse spéciale pour accéder à localhost depuis l'émulateur Android
    // Si vous utilisez un appareil physique, remplacez par l'adresse IP réelle de votre machine
    // Exemple: "http://192.168.1.10:8080/cookplusserver/rs/"
    
    /**
     * Récupère l'URL de base de l'API
     * @return L'URL complète du serveur avec le path de base
     */
    public static String getBaseUrl() {
        return BASE_URL;
    }
    
    /**
     * Récupère l'URL complète pour les uploads (images, vidéos)
     * @return L'URL du dossier uploads
     */
    public static String getUploadsUrl() {
        return BASE_URL.replace("/rs/", "/uploads/");
    }
    
    /**
     * Construit l'URL complète d'une image
     * @param imagePath Le nom du fichier image (ex: "ademe.png" ou "packs/viandes/pack-viande-500f.jpg")
     * @return L'URL complète de l'image
     */
    public static String getImageUrl(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return null;
        }
        // Si c'est déjà une URL complète (http/https), la retourner telle quelle
        if (imagePath.startsWith("http")) {
            return imagePath;
        }
        
        // Si le chemin commence par "packs/", c'est une image de pack/boutique
        // qui est déjà dans le bon dossier uploads/
        if (imagePath.startsWith("packs/")) {
            return BASE_URL.replace("/rs/", "/uploads/") + imagePath;
        }
        
        // Pour les autres images (recettes, etc.), elles sont dans uploads/images/
        return BASE_URL.replace("/rs/", "/uploads/images/") + imagePath;
    }
    
    /**
     * Construit l'URL complète pour une vidéo
     * @param videoPath Le chemin relatif de la vidéo (ex: "attieke_video.mp4")
     * @return L'URL complète de la vidéo
     */
    public static String getVideoUrl(String videoPath) {
        if (videoPath == null || videoPath.isEmpty()) {
            return null;
        }
        if (videoPath.startsWith("http")) {
            return videoPath;
        }
        return BASE_URL + "/uploads/videos/" + videoPath;
    }
    
    /**
     * Vérifie si l'URL est configurée pour l'émulateur
     * @return true si c'est l'adresse de l'émulateur
     */
    public static boolean isEmulatorUrl() {
        return BASE_URL.contains("10.0.2.2");
    }
}
