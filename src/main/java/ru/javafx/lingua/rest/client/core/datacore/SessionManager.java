
package ru.javafx.lingua.rest.client.core.datacore;

import org.springframework.http.HttpHeaders;

public interface SessionManager {
    
    HttpHeaders createSessionHeaders();
    
    String getSessionId();
    
    String getSessionIdCookie();
    
}
