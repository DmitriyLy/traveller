package org.dmly.traveller.presentation.admin.security;

import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.env.WebEnvironment;
import org.dmly.traveller.app.service.UserService;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;

@WebListener
public class CdiEnvironmentLoaderListener extends EnvironmentLoaderListener {
    @Inject
    private UserService userService;

    @Override
    protected WebEnvironment createEnvironment(ServletContext sc) {
        WebEnvironment environment = super.createEnvironment(sc);

        RealmSecurityManager manager = (RealmSecurityManager) environment.getWebSecurityManager();

        manager.setRealm(new CDIRealm(userService));

        return environment;
    }
}
