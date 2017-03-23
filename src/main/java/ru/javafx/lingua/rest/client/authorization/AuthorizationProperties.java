
package ru.javafx.lingua.rest.client.authorization;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "authorization")
public class AuthorizationProperties {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    public static final String AUTHORIZATION_REL_PATH = "src//main//resources//config//authorization.properties";
    public static final String AUTHORIZATION_PROPERTY_SOURCE = "classpath:config/authorization.properties";
      
    private String username;
    private String password;
    private String url;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public void updatePropertiesFile() {
        try {          
            PropertiesConfiguration properties = new PropertiesConfiguration(AUTHORIZATION_REL_PATH);
            properties.setProperty("authorization.username", getUsername());
            properties.setProperty("authorization.password", getPassword());
            properties.setProperty("authorization.url", getUrl());
            properties.save();
            logger.info("config.properties updated Successfully!!");
        } catch (ConfigurationException e) {
            System.out.println(e.getMessage());
        }
    }

}