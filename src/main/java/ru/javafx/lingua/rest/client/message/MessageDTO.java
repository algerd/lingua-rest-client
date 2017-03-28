package ru.javafx.lingua.rest.client.message;

public class MessageDTO {
    
    private String field;
    private String message;
    private String type;
  
    public MessageDTO() {
      super();
    }

    public MessageDTO(String type, String message, String field) {
        super();
        this.message = message;
        this.type = type;
        this.field = field;
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

    @Override
    public String toString() {
        return "MessageDTO{" + "field=" + field + ", message=" + message + ", type=" + type + '}';
    }
          
}
