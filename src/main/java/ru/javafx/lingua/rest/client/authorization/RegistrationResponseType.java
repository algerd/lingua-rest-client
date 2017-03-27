
package ru.javafx.lingua.rest.client.authorization;

public enum RegistrationResponseType {
    
    OK("OK"),
    ERROR_USERNAME_EXIST("A User with name already exist");
    
    private final String type;
    
    private RegistrationResponseType(String value) {
        type = value;
    }

    @Override
    public String toString() {
        return type;
    }
    
}
