
package ru.javafx.lingua.rest.client.authorization;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class UpdateAuthorizationProperties {
    
    public static void update() {
        try {
            PropertiesConfiguration properties = new PropertiesConfiguration("src//main//resources//config//authorization.properties");
            properties.setProperty("authorization.username", "user");
            properties.setProperty("authorization.password", "green");
            properties.save();
            System.out.println("config.properties updated Successfully!!");
        } catch (ConfigurationException e) {
            System.out.println(e.getMessage());
        }
    }

}
