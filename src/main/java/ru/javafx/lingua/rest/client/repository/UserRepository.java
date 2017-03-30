
package ru.javafx.lingua.rest.client.repository;

import java.net.URISyntaxException;
import org.springframework.hateoas.Resource;
import ru.javafx.lingua.rest.client.core.datacore.CrudRepository;
import ru.javafx.lingua.rest.client.entity.User;

public interface UserRepository extends CrudRepository<User> {
    
    Resource<User> findByUsername(String username) throws URISyntaxException;
      
}
