
package ru.javafx.lingua.rest.client.repository.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.TypeReferences;
import org.springframework.stereotype.Repository;
import ru.javafx.lingua.rest.client.core.datacore.impl.CrudRepositoryImpl;
import ru.javafx.lingua.rest.client.entity.User;
import ru.javafx.lingua.rest.client.repository.UserRepository;

@Repository
public class UserRepositoryImpl extends CrudRepositoryImpl<User> implements UserRepository {
    
    public UserRepositoryImpl() {
        resourceParameterizedType = new ParameterizedTypeReference<Resource<User>>() {};
        resourcesParameterizedType = new ParameterizedTypeReference<Resources<Resource<User>>>() {};
        pagedResourcesType = new TypeReferences.PagedResourcesType<Resource<User>>() {};
    } 

}
