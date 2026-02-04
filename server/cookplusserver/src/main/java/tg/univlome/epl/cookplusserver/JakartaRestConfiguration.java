package tg.univlome.epl.cookplusserver;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Set;
import java.util.HashSet;
import tg.univlome.epl.cookplusserver.resources.AuthResource;
import tg.univlome.epl.cookplusserver.resources.CommentaireResource;
import tg.univlome.epl.cookplusserver.resources.EtapeResource;
import tg.univlome.epl.cookplusserver.resources.FavoriResource;
import tg.univlome.epl.cookplusserver.resources.IngredientResource;
import tg.univlome.epl.cookplusserver.resources.MediaResource;
import tg.univlome.epl.cookplusserver.resources.NoteResource;
import tg.univlome.epl.cookplusserver.resources.NotificationResource;
import tg.univlome.epl.cookplusserver.resources.OpenAPIResource;
import tg.univlome.epl.cookplusserver.resources.OrigineResource;
import tg.univlome.epl.cookplusserver.resources.RecetteIngredientResource;
import tg.univlome.epl.cookplusserver.resources.RecetteResource;
import tg.univlome.epl.cookplusserver.resources.UtilisateurResource;
import tg.univlome.epl.cookplusserver.resources.VideoResource;

/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author DAKEY  Ahoefa Light
 */
@ApplicationPath("/rs")
@OpenAPIDefinition(
    info = @Info(
        title = "CookPlus API",
        version = "1.0",
        description = "API REST pour l'application CookPlus - Partage de recettes"
    ),
    servers = {
        @Server(url = "http://localhost:8080/cookplusserver", description = "Development Server")
    }
)
public class JakartaRestConfiguration extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(AuthResource.class);
        classes.add(CommentaireResource.class);
        classes.add(EtapeResource.class);
        classes.add(FavoriResource.class);
        classes.add(IngredientResource.class);
        classes.add(MediaResource.class);
        classes.add(NoteResource.class);
        classes.add(NotificationResource.class);
        classes.add(OrigineResource.class);
        classes.add(RecetteResource.class);
        classes.add(RecetteIngredientResource.class);
        classes.add(UtilisateurResource.class);
        classes.add(VideoResource.class);
        classes.add(OpenAPIResource.class);
        return classes;
    }
}
