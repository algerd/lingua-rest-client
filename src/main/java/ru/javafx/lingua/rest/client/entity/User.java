
package ru.javafx.lingua.rest.client.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;
import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import ru.javafx.lingua.rest.client.core.Entity;
import ru.javafx.lingua.rest.client.core.datacore.RelPath;

@RelPath("users")
public class User implements Entity {

    private final StringProperty username = new SimpleStringProperty("");
    private final StringProperty password = new SimpleStringProperty("");
    private final StringProperty mail = new SimpleStringProperty("");
    private final ObjectProperty<LocalDateTime> created = new SimpleObjectProperty<>(LocalDateTime.now());     
           
    public User() {}
    
    @Size(min = 4, max = 64, message = "error.user.username.size")
    public String getUsername() {
        return username.get();
    }   
    public void setUsername(String value) {
        username.set(value);
    }
    public StringProperty usernameProperty() {
        return username;
    }
    
    @Size(min = 4, max = 64, message = "error.user.password.size") 
    public String getPassword() {
        return password.get();
    }
    public void setPassword(String value) {
        password.set(value);
    }
    public StringProperty passwordProperty() {
        return password;
    }
    
    @Size(min = 4, max = 64, message = "error.user.mail.size")
    @Pattern(regexp = "^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
        "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
        "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
        "+(?:[a-zA-Z]){2,}\\.?)$", 
        message = "error.user.mail.invalid")
    public String getMail() {
        return mail.get();
    }
    public void setMail(String value) {
        mail.set(value);
    }
    public StringProperty mailProperty() {
        return mail;
    }
    
    public LocalDateTime getCreated() {
        return created.get();
    }   
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setCreated(LocalDateTime value) {
        created.setValue(value);
    }  
    public ObjectProperty<LocalDateTime> createdProperty() {
        return created;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(getUsername());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(getUsername(), other.getUsername())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getUsername();
    }   
    
}
