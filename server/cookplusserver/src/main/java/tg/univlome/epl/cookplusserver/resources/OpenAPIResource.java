package tg.univlome.epl.cookplusserver.resources;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/openapi")
public class OpenAPIResource {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    private static volatile OpenAPI spec;
    
    @GET
    @Produces("application/json")
    public Response getOpenAPI() {
        if (spec == null) {
            synchronized (OpenAPIResource.class) {
                if (spec == null) {
                    spec = loadOpenAPI();
                }
            }
        }
        try {
            return Response.ok(mapper.writeValueAsString(spec)).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
    
    private static OpenAPI loadOpenAPI() {
        OpenAPI api = new OpenAPI();
        
        api.info(new Info()
            .title("CookPlus API")
            .version("1.0.0")
            .description("API REST pour application CookPlus"));
        
        api.addServersItem(new Server()
            .url("http://localhost:8080/cookplusserver")
            .description("Serveur local"));
        
        Paths paths = new Paths();
        
        // Auth endpoints
        paths.addPathItem("/rs/auth/login", createAuthEndpoint("POST", "login", "Login utilisateur", "UtilisateurDTO", "201"));
        paths.addPathItem("/rs/auth/validate", createAuthEndpoint("POST", "validateToken", "Valider token", "String", "200"));
        paths.addPathItem("/rs/auth/check-admin", createAuthEndpoint("GET", "checkAdmin", "Verifier admin", "Boolean", "200"));
        
        // Recettes endpoints
        paths.addPathItem("/rs/recettes", createEndpoint("GET", "findAll", "Lister toutes les recettes", "List<Recette>", "200"));
        paths.addPathItem("/rs/recettes", createEndpoint("POST", "create", "Creer une recette", "Recette", "201"));
        paths.addPathItem("/rs/recettes/{id}", createEndpointWithPath("GET", "findById", "Obtenir une recette", "Recette", "200"));
        paths.addPathItem("/rs/recettes/{id}", createEndpointWithPath("PUT", "update", "Mettre a jour", "Recette", "200"));
        paths.addPathItem("/rs/recettes/{id}", createEndpointWithPath("DELETE", "delete", "Supprimer", "Void", "204"));
        paths.addPathItem("/rs/recettes/search", createEndpoint("GET", "search", "Chercher recettes", "List<Recette>", "200"));
        paths.addPathItem("/rs/recettes/public", createEndpoint("GET", "findPublic", "Recettes publiques", "List<Recette>", "200"));
        
        // Ingredients endpoints
        paths.addPathItem("/rs/ingredients", createEndpoint("GET", "findAll", "Lister ingredients", "List<Ingredient>", "200"));
        paths.addPathItem("/rs/ingredients", createEndpoint("POST", "create", "Creer ingredient", "Ingredient", "201"));
        paths.addPathItem("/rs/ingredients/{id}", createEndpointWithPath("GET", "findById", "Obtenir ingredient", "Ingredient", "200"));
        paths.addPathItem("/rs/ingredients/{id}", createEndpointWithPath("PUT", "update", "Mettre a jour", "Ingredient", "200"));
        paths.addPathItem("/rs/ingredients/{id}", createEndpointWithPath("DELETE", "delete", "Supprimer", "Void", "204"));
        paths.addPathItem("/rs/ingredients/disponibles", createEndpoint("GET", "findAvailable", "Ingredients disponibles", "List<Ingredient>", "200"));
        
        // Utilisateurs endpoints
        paths.addPathItem("/rs/utilisateurs", createEndpoint("GET", "findAll", "Lister utilisateurs", "List<Utilisateur>", "200"));
        paths.addPathItem("/rs/utilisateurs", createEndpoint("POST", "create", "Creer utilisateur", "Utilisateur", "201"));
        paths.addPathItem("/rs/utilisateurs/{id}", createEndpointWithPath("GET", "findById", "Obtenir utilisateur", "Utilisateur", "200"));
        paths.addPathItem("/rs/utilisateurs/{id}", createEndpointWithPath("PUT", "update", "Mettre a jour", "Utilisateur", "200"));
        paths.addPathItem("/rs/utilisateurs/{id}", createEndpointWithPath("DELETE", "delete", "Supprimer", "Void", "204"));
        
        // Commentaires endpoints
        paths.addPathItem("/rs/commentaires", createEndpoint("GET", "findAll", "Lister commentaires", "List<Commentaire>", "200"));
        paths.addPathItem("/rs/commentaires", createEndpoint("POST", "create", "Creer commentaire", "Commentaire", "201"));
        paths.addPathItem("/rs/commentaires/{id}", createEndpointWithPath("GET", "findById", "Obtenir commentaire", "Commentaire", "200"));
        paths.addPathItem("/rs/commentaires/{id}", createEndpointWithPath("PUT", "update", "Mettre a jour", "Commentaire", "200"));
        paths.addPathItem("/rs/commentaires/{id}", createEndpointWithPath("DELETE", "delete", "Supprimer", "Void", "204"));
        
        // Notes endpoints
        paths.addPathItem("/rs/notes", createEndpoint("GET", "findAll", "Lister notes", "List<Note>", "200"));
        paths.addPathItem("/rs/notes", createEndpoint("POST", "create", "Creer note", "Note", "201"));
        paths.addPathItem("/rs/notes/{id}", createEndpointWithPath("GET", "findById", "Obtenir note", "Note", "200"));
        paths.addPathItem("/rs/notes/{id}", createEndpointWithPath("PUT", "update", "Mettre a jour", "Note", "200"));
        paths.addPathItem("/rs/notes/{id}", createEndpointWithPath("DELETE", "delete", "Supprimer", "Void", "204"));
        
        // Favoris endpoints
        paths.addPathItem("/rs/favoris", createEndpoint("GET", "findAll", "Lister favoris", "List<Favori>", "200"));
        paths.addPathItem("/rs/favoris", createEndpoint("POST", "create", "Creer favori", "Favori", "201"));
        paths.addPathItem("/rs/favoris/{id}", createEndpointWithPath("GET", "findById", "Obtenir favori", "Favori", "200"));
        paths.addPathItem("/rs/favoris/{id}", createEndpointWithPath("PUT", "update", "Mettre a jour", "Favori", "200"));
        paths.addPathItem("/rs/favoris/{id}", createEndpointWithPath("DELETE", "delete", "Supprimer", "Void", "204"));
        
        // Medias endpoints
        paths.addPathItem("/rs/medias", createEndpoint("GET", "findAll", "Lister medias", "List<Media>", "200"));
        paths.addPathItem("/rs/medias", createEndpoint("POST", "create", "Creer media", "Media", "201"));
        paths.addPathItem("/rs/medias/{id}", createEndpointWithPath("GET", "findById", "Obtenir media", "Media", "200"));
        paths.addPathItem("/rs/medias/{id}", createEndpointWithPath("PUT", "update", "Mettre a jour", "Media", "200"));
        paths.addPathItem("/rs/medias/{id}", createEndpointWithPath("DELETE", "delete", "Supprimer", "Void", "204"));
        
        // Notifications endpoints
        paths.addPathItem("/rs/notifications", createEndpoint("GET", "findAll", "Lister notifications", "List<Notification>", "200"));
        paths.addPathItem("/rs/notifications", createEndpoint("POST", "create", "Creer notification", "Notification", "201"));
        paths.addPathItem("/rs/notifications/{id}", createEndpointWithPath("GET", "findById", "Obtenir notification", "Notification", "200"));
        paths.addPathItem("/rs/notifications/{id}", createEndpointWithPath("PUT", "update", "Marquer comme lue", "Notification", "200"));
        paths.addPathItem("/rs/notifications/{id}", createEndpointWithPath("DELETE", "delete", "Supprimer", "Void", "204"));
        
        // Videos endpoints
        paths.addPathItem("/rs/videos", createEndpoint("GET", "findAll", "Lister videos", "List<Video>", "200"));
        paths.addPathItem("/rs/videos", createEndpoint("POST", "create", "Creer video", "Video", "201"));
        paths.addPathItem("/rs/videos/{id}", createEndpointWithPath("GET", "findById", "Obtenir video", "Video", "200"));
        paths.addPathItem("/rs/videos/{id}", createEndpointWithPath("PUT", "update", "Mettre a jour", "Video", "200"));
        paths.addPathItem("/rs/videos/{id}", createEndpointWithPath("DELETE", "delete", "Supprimer", "Void", "204"));
        
        // Etapes endpoints
        paths.addPathItem("/rs/etapes", createEndpoint("GET", "findAll", "Lister etapes", "List<Etape>", "200"));
        paths.addPathItem("/rs/etapes", createEndpoint("POST", "create", "Creer etape", "Etape", "201"));
        paths.addPathItem("/rs/etapes/{id}", createEndpointWithPath("GET", "findById", "Obtenir etape", "Etape", "200"));
        paths.addPathItem("/rs/etapes/{id}", createEndpointWithPath("PUT", "update", "Mettre a jour", "Etape", "200"));
        paths.addPathItem("/rs/etapes/{id}", createEndpointWithPath("DELETE", "delete", "Supprimer", "Void", "204"));
        
        api.setPaths(paths);
        return api;
    }
    
    private static PathItem createEndpoint(String method, String operationId, String description, String responseType, String responseCode) {
        Operation op = new Operation()
            .operationId(operationId)
            .summary(description)
            .addTagsItem("API");
        
        op.setResponses(createResponses(responseCode, responseType));
        
        PathItem item = new PathItem();
        setMethod(item, method, op);
        return item;
    }
    
    private static PathItem createEndpointWithPath(String method, String operationId, String description, String responseType, String responseCode) {
        Operation op = new Operation()
            .operationId(operationId)
            .summary(description)
            .addTagsItem("API");
        
        op.addParametersItem(new Parameter()
            .name("id")
            .in("path")
            .required(true)
            .description("Identifiant"));
        
        op.setResponses(createResponses(responseCode, responseType));
        
        PathItem item = new PathItem();
        setMethod(item, method, op);
        return item;
    }
    
    private static PathItem createAuthEndpoint(String method, String operationId, String description, String responseType, String responseCode) {
        Operation op = new Operation()
            .operationId(operationId)
            .summary(description)
            .addTagsItem("Auth");
        
        op.setResponses(createResponses(responseCode, responseType));
        
        PathItem item = new PathItem();
        setMethod(item, method, op);
        return item;
    }
    
    private static ApiResponses createResponses(String code, String responseType) {
        ApiResponses responses = new ApiResponses();
        ApiResponse response = new ApiResponse().description("Success");
        responses.addApiResponse(code, response);
        responses.addApiResponse("400", new ApiResponse().description("Bad Request"));
        responses.addApiResponse("500", new ApiResponse().description("Internal Server Error"));
        return responses;
    }
    
    private static void setMethod(PathItem item, String method, Operation op) {
        if ("GET".equals(method)) item.setGet(op);
        else if ("POST".equals(method)) item.setPost(op);
        else if ("PUT".equals(method)) item.setPut(op);
        else if ("DELETE".equals(method)) item.setDelete(op);
    }
}
