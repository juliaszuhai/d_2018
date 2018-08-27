package ro.msg.edu.jbugs.boundary;


import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import ro.msg.edu.jbugs.bugmanagement.business.boundary.AddBug;
import ro.msg.edu.jbugs.bugmanagement.business.boundary.GenerateExcel;
import ro.msg.edu.jbugs.bugmanagement.business.boundary.GeneratePdf;
import ro.msg.edu.jbugs.bugmanagement.business.boundary.ListBugs;
import ro.msg.edu.jbugs.usermanagement.business.control.AuthenticationManager;
import ro.msg.edu.jbugs.usermanagement.business.control.PermissionManager;
import ro.msg.edu.jbugs.usermanagement.business.control.UserManager;
import ro.msg.edu.jbugs.usermanagement.business.filter.AuthenticationFilter;
import ro.msg.edu.jbugs.usermanagement.business.filter.CorsFilter;

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
        classes.add(AuthenticationManager.class);
        classes.add(AuthenticationFilter.class);
        classes.add(Registration.class);
        classes.add(GeneratePdf.class);
        classes.add(AddBug.class);
        classes.add(GenerateExcel.class);
        classes.add(ListBugs.class);
        classes.add(UserManager.class);
        classes.add(PermissionManager.class);
        classes.add(CorsFilter.class);
        return classes;
    }
}
