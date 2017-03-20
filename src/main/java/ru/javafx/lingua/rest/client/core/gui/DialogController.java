package ru.javafx.lingua.rest.client.core.gui;

import javafx.stage.Stage;
import org.springframework.hateoas.Resource;
import ru.javafx.lingua.rest.client.core.Entity;

public interface DialogController<T extends Entity> {
    
    void setStage(Stage stage);
    
    void setResource(Resource<T> resource);
         
}
