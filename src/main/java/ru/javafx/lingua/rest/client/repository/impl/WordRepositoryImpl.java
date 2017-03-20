
package ru.javafx.lingua.rest.client.repository.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.TypeReferences;
import org.springframework.stereotype.Repository;
import ru.javafx.lingua.rest.client.core.datacore.impl.CrudRepositoryImpl;
import ru.javafx.lingua.rest.client.entity.Word;
import ru.javafx.lingua.rest.client.repository.WordRepository;

@Repository
public class WordRepositoryImpl extends CrudRepositoryImpl<Word> implements WordRepository {
    
    public WordRepositoryImpl() {
        resourceParameterizedType = new ParameterizedTypeReference<Resource<Word>>() {};
        resourcesParameterizedType = new ParameterizedTypeReference<Resources<Resource<Word>>>() {};
        pagedResourcesType = new TypeReferences.PagedResourcesType<Resource<Word>>() {};
    } 

}
