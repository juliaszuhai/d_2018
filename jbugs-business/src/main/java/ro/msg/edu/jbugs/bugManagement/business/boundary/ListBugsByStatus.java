package ro.msg.edu.jbugs.bugManagement.business.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.bugManagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugManagement.persistence.entity.Status;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/listBugsByStatus")
public class ListBugsByStatus {

    @EJB
    private BugManagement bugManagement;

    @GET
    // @Produces("application/json")
    // @Consumes("application/x-www-form-urlencoded")
    public String getBugsByStatus(Status status) throws JsonProcessingException, BusinessException {
        List<BugDTO> allBugs=bugManagement.getBugsByStatus(status);
        ObjectMapper mapper=new ObjectMapper();

        String jsonResponse=mapper.writeValueAsString(allBugs);

        return jsonResponse;
    }
}
