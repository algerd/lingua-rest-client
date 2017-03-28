package ru.javafx.lingua.rest.client.message;

public class MessageDTO {
    
    private String message;
    private String type;

    public MessageDTO() {
      super();
    }

    public MessageDTO(String type, String message) {
      super();
      this.message = message;
      this.type = type;
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

    @Override
    public String toString() {
        return "MessageDTO{" + "message=" + message + ", type=" + type + '}';
    }
      
}
