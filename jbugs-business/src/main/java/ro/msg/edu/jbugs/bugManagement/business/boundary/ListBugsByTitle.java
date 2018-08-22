package ro.msg.edu.jbugs.bugManagement.business.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.bugManagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugManagement.persistence.entity.Bug;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.util.List;

@Path("/listBugsByTitle")
public class ListBugsByTitle {

    @EJB
    private BugManagement bugManagement;

    @GET
    @Path("{title}")
    @Produces("application/json")
//     @Consumes("application/x-www-form-urlencoded")
    public String getBugsByTitle(@PathParam("title") String title) throws JsonProcessingException, BusinessException {
        List<BugDTO> allBugs=bugManagement.getBugsByTitle(title);
        ObjectMapper mapper=new ObjectMapper();

        String jsonResponse=mapper.writeValueAsString(allBugs);

        return jsonResponse;
    }
}
