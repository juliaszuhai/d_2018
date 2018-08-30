package ro.msg.edu.jbugs.boundary;


import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import ro.msg.edu.jbugs.bugmanagement.business.control.BugAdditionManager;
import ro.msg.edu.jbugs.bugmanagement.business.control.BugManagementController;
import ro.msg.edu.jbugs.bugmanagement.business.control.ExcelGeneratorController;
import ro.msg.edu.jbugs.bugmanagement.business.control.PdfGeneratorController;
import ro.msg.edu.jbugs.notificationmanagement.business.control.NotificationManager;
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
        classes.add(PdfGeneratorController.class);
        classes.add(BugAdditionManager.class);
        classes.add(ExcelGeneratorController.class);
        classes.add(BugManagementController.class);
        classes.add(UserManager.class);
        classes.add(PermissionManager.class);
        classes.add(CorsFilter.class);
        classes.add(NotificationManager.class);
        classes.add(MultiPartFeature.class);
        return classes;
    }
}
