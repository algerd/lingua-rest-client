package ru.javafx.lingua.rest.client.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.ResponseEntity;

public class MessageDTO {
    
    private String field;
    private String message;
    private String type;
    private Object invalidValue;
    
    private static final Logger logger = LoggerFactory.getLogger(MessageDTO.class);
  
    public MessageDTO() {
      super();
    }

    public MessageDTO(String type, String message, String field) {
        super();
        this.message = message;
        this.type = type;
        this.field = field;
    }
    
    public MessageDTO(String type, String message, String field, Object invalidValue) {
        super();
        this.message = message;
        this.type = type;
        this.field = field;
        this.invalidValue = invalidValue;
    }
    
    public static List<MessageDTO> parseJsonResponse(ResponseEntity<String> response) {
        List<MessageDTO> messages = new ArrayList<>();
        try {
            JsonParser jsonParser = new BasicJsonParser();           
            List<Object> list = jsonParser.parseList(response.getBody());

            for (Object object : list) {
                Map<String, String> obj = (Map<String, String>) object;
                MessageDTO msg = new MessageDTO(obj.get("type"), obj.get("message"), obj.get("field"), obj.get("invalidValue"));
                logger.info("error: {}", msg);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getInvalidValue() {
        return invalidValue;
    }

    public void setInvalidValue(Object invalidValue) {
        this.invalidValue = invalidValue;
    }

    @Override
    public String toString() {
        return "MessageDTO{" + "field=" + field + ", message=" + message + ", type=" + type + ", invalidValue=" + invalidValue + '}';
    }
            
}
