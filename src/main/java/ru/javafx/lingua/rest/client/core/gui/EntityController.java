
package ru.javafx.lingua.rest.client.core.gui;

import org.springframework.hateoas.Resource;
import ru.javafx.lingua.rest.client.core.Entity;

public abstract class EntityController<T extends Entity> extends BaseAwareController {
    
    protected Resource<T> resource;
    
    public void setResource(Resource<T> resource) {
        this.resource = resource;
    }

    public Resource<T> getResource() {
        return resource;
    }  
    
    public abstract void show();
    
}
