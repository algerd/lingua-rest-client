
package ru.javafx.lingua.rest.client.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;
import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.javafx.lingua.rest.client.core.Entity;
import ru.javafx.lingua.rest.client.core.datacore.RelPath;

@RelPath("users")
public class User implements Entity {
       
    private String password;        
    private final StringProperty username = new SimpleStringProperty("");
    private final ObjectProperty<LocalDateTime> created = new SimpleObjectProperty<>(LocalDateTime.now());

    public User() {}
    
    public String getUsername() {
        return username.get();
    }
    public void setUsername(String value) {
        username.set(value);
    }
    public StringProperty usernameProperty() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String value) {
        password = value;
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
