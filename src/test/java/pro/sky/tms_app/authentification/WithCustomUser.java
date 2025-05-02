package pro.sky.tms_app.authentification;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = CustomSecurityContextFactory.class)
public @interface WithCustomUser {

    String username();

}
