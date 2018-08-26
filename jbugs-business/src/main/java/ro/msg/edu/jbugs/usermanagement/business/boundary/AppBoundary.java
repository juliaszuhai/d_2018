package ro.msg.edu.jbugs.usermanagement.business.boundary;


import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import ro.msg.edu.jbugs.bugmanagement.business.boundary.AddBug;
import ro.msg.edu.jbugs.bugmanagement.business.boundary.GeneratePdf;
import ro.msg.edu.jbugs.bugmanagement.business.boundary.ListBugs;
import ro.msg.edu.jbugs.internationalization.business.boundary.Language;

import javax.servlet.Registration;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest/")
public class AppBoundary extends Application {

    @Override
    public Set<Class<?>> getClasses(){
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(JacksonJaxbJsonProvider.class);
        classes.add(Authentication.class);
        classes.add(AuthenticationFilter.class);
        classes.add(Registration.class);
        classes.add(GeneratePdf.class);
        classes.add(AddBug.class);
        classes.add(ro.msg.edu.jbugs.bugManagement.business.boundary.GenerateExcel.class);
        classes.add(ListBugs.class);
        classes.add(Language.class);
        classes.add(ManageUsers.class);
        classes.add(ManagePermissions.class);
        classes.add(CorsFilter.class);
        return classes;
    }
}
