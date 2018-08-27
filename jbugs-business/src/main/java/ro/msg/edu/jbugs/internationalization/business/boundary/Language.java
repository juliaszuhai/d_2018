package ro.msg.edu.jbugs.internationalization.business.boundary;

import ro.msg.edu.jbugs.internationalization.business.control.LanguageTranslator;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("/language")
public class Language {

    @EJB
    private LanguageTranslator languageTranslator;

    /**
     * Retrieve JSON File specific to each language available
     *
     * @param id
     * @return Response with JSON File
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getTranslation(@PathParam("id") int id) {

        try{
            Response.ResponseBuilder response = Response.ok(getClass().getClassLoader().getResourceAsStream(
                    "Language_" + languageTranslator.getLanguageSelected(id) + ".json"));

            return response.build();
        }catch (Exception e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
}
