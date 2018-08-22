package ro.msg.edu.jbugs.internationalization.business.boundle;

import org.json.JSONObject;
import ro.msg.edu.jbugs.internationalization.business.control.LanguageTranslator;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Path("/language")
public class Language {

    @EJB
    private LanguageTranslator languageTranslator;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getFile(@PathParam("id") int id) {
        Locale locale = languageTranslator.getCurrentLocale(id);

        ResourceBundle resourceBundle = languageTranslator.getBundle(locale);

        Map<String, String> mapBoundle = languageTranslator.convertResourceBundleToMap(resourceBundle);

        Response.ResponseBuilder response = Response.ok(mapBoundle);
        return response.build();
    }

}
