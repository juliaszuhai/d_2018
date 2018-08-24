package ro.msg.edu.jbugs.userManagement.business.boundary;


import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import ro.msg.edu.jbugs.bugManagement.business.boundary.*;

import ro.msg.edu.jbugs.bugManagement.business.boundary.ViewBug;
import ro.msg.edu.jbugs.internationalization.business.boundary.Language;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationPath("rest/")
public class AppBoundary extends Application {

    public Set<Class<?>> getClasses(){
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(JacksonJaxbJsonProvider.class);
        classes.add(Authentication.class);
        classes.add(Registration.class);
        classes.add(ViewBug.class);
        classes.add(AddBug.class);
        classes.add(GenerateExcel.class);
        classes.add(ListBugs.class);
        classes.add(Language.class);
        classes.add(ManageUsers.class);
        return classes;
    }
}
