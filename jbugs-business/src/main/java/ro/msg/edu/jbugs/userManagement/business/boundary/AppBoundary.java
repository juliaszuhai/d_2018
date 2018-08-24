package ro.msg.edu.jbugs.userManagement.business.boundary;


import ro.msg.edu.jbugs.bugManagement.business.boundary.*;

import ro.msg.edu.jbugs.bugManagement.business.boundary.GeneratePdf;
import ro.msg.edu.jbugs.internationalization.business.boundary.Language;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest/")
public class AppBoundary extends Application {

    public Set<Class<?>> getClasses(){
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(Authentication.class);
        classes.add(AuthenticationFilter.class);
        classes.add(Registration.class);
        classes.add(GeneratePdf.class);
        classes.add(AddBug.class);
        classes.add(GenerateExcel.class);
        classes.add(ListBugs.class);
        classes.add(ListBugsByTitle.class);
        classes.add(ListBugsByStatus.class);
        classes.add(ListBugsBySeverity.class);
        classes.add(ListBugsByDescription.class);
        classes.add(Language.class);
        classes.add(ManageUsers.class);
        classes.add(ManagePermissions.class);
        classes.add(CorsFilter.class);
        return classes;
    }
}
