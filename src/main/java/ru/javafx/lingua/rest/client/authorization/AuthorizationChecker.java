
package ru.javafx.lingua.rest.client.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationChecker {
    
    private boolean isAuthorizate = false;
    
    @Autowired
    private AuthorizationProperties authorizationProperties;
    
    private boolean checkPpoperties() {
        return authorizationProperties.isUsernameAndPassword();
    }
    
    private boolean checkAuthorization() {
        return true;
    }
      
    public boolean check() {
        isAuthorizate = checkPpoperties() && checkAuthorization();
        return isAuthorizate;       
    }

    public boolean isAuthorizate() {
        return isAuthorizate;
    }
    
}
