package org.dmly.traveller.presentation.admin.security;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.dmly.traveller.app.model.entity.person.User;
import org.dmly.traveller.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class CDIRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger(CDIRealm.class);

    private final UserService userService;

    public CDIRealm(UserService userService) {
        this.userService = userService;

        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("SHA-256");
        credentialsMatcher.setStoredCredentialsHexEncoded(true);

        setCredentialsMatcher(credentialsMatcher);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAccount();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        try {
            String password = Optional.ofNullable(username)
                    .flatMap(name -> userService.findByUserName(name))
                    .map(User::getPassword)
                    .orElseThrow(() -> new UnknownAccountException("No account found for user " + username));

            return new SimpleAuthenticationInfo(username, password.toCharArray(), getName());

        } catch (Exception e) {
            String message = "There was an error while authenticating user " + username;
            LOGGER.error(message, e);

            throw new AuthenticationException(message, e);
        }
    }
}
