
package ru.javafx.lingua.rest.client.authorization;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import ru.javafx.lingua.rest.client.entity.User;
import ru.javafx.lingua.rest.client.repository.UserRepository;

@Component
public class AuthorizationChecker {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private boolean isAuthorize = false;
    private User user;
    
    @Autowired
    private AuthorizationProperties authorizationProperties;
    @Autowired
    private UserRepository userRepository;
    
    private boolean checkPpoperties() {
        return authorizationProperties.isUsernameAndPassword();
    }
          
    public boolean check() {
        isAuthorize = checkPpoperties() && checkAuthorization();       
        if (isAuthorize()) {
            try {
                user = userRepository.findByUsername(authorizationProperties.getUsername()).getContent();
            } catch (URISyntaxException ex) {
                logger.error(ex.getMessage());
                isAuthorize = false;
            }
        }
        return isAuthorize;       
    }

    public boolean isAuthorize() {
        return isAuthorize;
    }
    
    private boolean checkAuthorization() {           
        try {			                      
			URL url = new URL(authorizationProperties.getUrl());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            String authorStr = authorizationProperties.getUsername() + ":" + authorizationProperties.getPassword();
            String encoding = Base64.getEncoder().encodeToString(authorStr.getBytes("utf-8"));
			connection.setRequestProperty(HttpHeaders.AUTHORIZATION, "Basic " + encoding); 
           
            logger.info("ResponseCode: {}", connection.getResponseCode());            
            switch (connection.getResponseCode()) {
                case 200:
                    return true;
                case 401:
                    return false;
                default:
                    throw new IOException("Error Connection");
            }
            
		} catch (IOException e) {
			logger.error(e.getMessage());
            System.exit(0);
		} 
        return false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
