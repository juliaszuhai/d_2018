package ro.msg.edu.jbugs.utils;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class ExceptionInterceptor {

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        System.out.println("*** DefaultInterceptor intercepting " + ctx.getMethod().getName());
        try {
            return ctx.proceed();
        } finally {
            System.out.println("*** DefaultInterceptor exiting");
        }
    }

}
