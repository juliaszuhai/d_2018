package ro.msg.edu.jbugs.userManagement.business.boundary;


import ro.msg.edu.jbugs.bugManagement.business.boundary.*;

import ro.msg.edu.jbugs.bugManagement.business.boundary.ViewBug;
import ro.msg.edu.jbugs.internationalization.business.boundle.Language;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest/")
public class AppBoundary extends Application {

    public Set<Class<?>> getClasses(){
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(Authentication.class);
        classes.add(Registration.class);
        classes.add(ViewBug.class);

        classes.add(GenerateExcel.class);
        classes.add(ListBugs.class);
<<<<<<< HEAD
        classes.add(ListBugsByTitle.class);
        classes.add(ListBugsByStatus.class);
        classes.add(ListBugsBySeverity.class);
        classes.add(ListBugsByDescription.class);
//        classes.add(ViewBug.class);
        classes.add(ActivateUser.class);
        classes.add(Language.class);
        classes.add(AddRole.class);
=======
        classes.add(GenerateExcel.class);
        classes.add(ViewBug.class);
        classes.add(Language.class);
>>>>>>> d592d75e5a6887668850991edb29673d35ffd653
        return classes;
    }
}
