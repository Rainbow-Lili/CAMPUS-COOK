package tg.univlome.epl.cookplusserver.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Endpoint JAX-RS pour gérer les images des recettes
 * @author DAKEY Ahoefa Light
 */
@Path("/images")
public class ImageResource {
    
    @Context
    private jakarta.servlet.ServletContext servletContext;
    
    /**
     * Obtient le chemin physique du dossier uploads/images
     */
    private String getImageDirectory() {
        if (servletContext != null) {
            return servletContext.getRealPath("/uploads/images/");
        }
        // Fallback si ServletContext n'est pas disponible
        return System.getProperty("user.home") + "/cookplus_images/";
    }
    
    /**
     * Servir une image par son nom de fichier
     * Exemple: GET http://localhost:8080/cookplusserver/rs/images/ademe.png
     */
    @GET
    @jakarta.ws.rs.Path("/{filename}")
    @Produces({"image/png", "image/jpeg", "image/jpg", "image/gif"})
    public Response getImage(@PathParam("filename") String filename) {
        try {
            String imageDir = getImageDirectory();
            System.out.println("DEBUG - Image directory: " + imageDir);
            System.out.println("DEBUG - Requested filename: " + filename);
            
            java.nio.file.Path imagePath = Paths.get(imageDir, filename);
            System.out.println("DEBUG - Full image path: " + imagePath.toAbsolutePath());
            
            if (!Files.exists(imagePath)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Image non trouvée: " + filename)
                        .build();
            }
            
            // Déterminer le type MIME
            String contentType = Files.probeContentType(imagePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            
            // Lire le fichier
            byte[] imageData = Files.readAllBytes(imagePath);
            
            return Response.ok(imageData)
                    .type(contentType)
                    .header("Content-Disposition", "inline; filename=\"" + filename + "\"")
                    .build();
                    
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur lors de la lecture de l'image: " + e.getMessage())
                    .build();
        }
    }
    
    /**
     * Upload une nouvelle image
     * Exemple: POST http://localhost:8080/cookplusserver/rs/images/upload
     * Content-Type: multipart/form-data
     */
    @POST
    @jakarta.ws.rs.Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(
            @FormParam("file") InputStream fileInputStream,
            @FormParam("filename") String filename) {
        
        if (fileInputStream == null || filename == null || filename.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Fichier ou nom manquant\"}")
                    .build();
        }
        
        try {
            // Nettoyer le nom de fichier
            String sanitizedFilename = filename.replaceAll("[^a-zA-Z0-9._-]", "_");
            
            // Ajouter timestamp pour éviter les doublons
            String timestamp = String.valueOf(System.currentTimeMillis());
            String extension = "";
            int dotIndex = sanitizedFilename.lastIndexOf('.');
            if (dotIndex > 0) {
                extension = sanitizedFilename.substring(dotIndex);
                sanitizedFilename = sanitizedFilename.substring(0, dotIndex) + "_" + timestamp + extension;
            } else {
                sanitizedFilename = sanitizedFilename + "_" + timestamp;
            }
            
            java.nio.file.Path targetPath = Paths.get(getImageDirectory(), sanitizedFilename);
            
            // Sauvegarder le fichier
            Files.copy(fileInputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            
            // Construire l'URL de l'image
            String imageUrl = sanitizedFilename;
            
            return Response.ok()
                    .entity("{\"success\": true, \"imageUrl\": \"" + imageUrl + "\", \"message\": \"Image uploadée avec succès\"}")
                    .build();
                    
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erreur lors de l'upload: " + e.getMessage() + "\"}")
                    .build();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Lister toutes les images disponibles
     */
    @GET
    @jakarta.ws.rs.Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listImages() {
        try {
            File directory = new File(getImageDirectory());
            File[] files = directory.listFiles((dir, name) -> 
                name.toLowerCase().matches(".*\\.(png|jpg|jpeg|gif)$")
            );
            
            if (files == null) {
                return Response.ok("[]").build();
            }
            
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < files.length; i++) {
                if (i > 0) json.append(",");
                json.append("\"").append(files[i].getName()).append("\"");
            }
            json.append("]");
            
            return Response.ok(json.toString()).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
