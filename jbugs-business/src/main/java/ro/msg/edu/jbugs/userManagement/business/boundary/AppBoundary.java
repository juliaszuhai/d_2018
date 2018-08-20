package ro.msg.edu.jbugs.userManagement.business.boundary;


import ro.msg.edu.jbugs.bugManagement.business.boundary.GenerateExcel;
import ro.msg.edu.jbugs.bugManagement.business.boundary.ListBugs;

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

        classes.add(GenerateExcel.class);
        classes.add(ListBugs.class);
        return classes;
    }
}
