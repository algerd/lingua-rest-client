package ru.javafx.lingua.rest.client.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.ResponseEntity;

public class ErrorMessage {
    
    private String entity;
    private String property;   
    private Object invalidValue;
    private String message;
    
    private static final Logger logger = LoggerFactory.getLogger(ErrorMessage.class);
  
    public ErrorMessage() {
      super();
    }

    public ErrorMessage(String entity, String property, Object invalidValue, String message) {
        super();
        this.entity = entity;
        this.property = property;
        this.invalidValue = invalidValue;
        this.message = message;           
    }
    
    public static List<ErrorMessage> parseJsonResponse(String response) {
        List<ErrorMessage> messages = new ArrayList<>();
        try {
            JsonParser jsonParser = new BasicJsonParser();  
            Map<String, Object> responseMap = jsonParser.parseMap(response);
            List<Object> errorsList = (List<Object>) responseMap.get("errors");            
            for (Object object : errorsList) {
                Map<String, Object> obj = (Map<String, Object>) object;
                ErrorMessage msg = new ErrorMessage(
                        obj.get("entity").toString(), 
                        obj.get("property").toString(),
                        obj.get("invalidValue"),
                        obj.get("message").toString());                                         
                //logger.info("error: {}", msg);
                messages.add(msg);
            }
        } catch (IllegalArgumentException ex) {}       
        return messages;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Object getInvalidValue() {
        return invalidValue;
    }

    public void setInvalidValue(Object invalidValue) {
        this.invalidValue = invalidValue;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" + "entity=" + entity + ", property=" + property + ", invalidValue=" + invalidValue + ", message=" + message + '}';
    }  
            
}
