
package ru.javafx.lingua.rest.client.core.datacore;
import ru.javafx.lingua.rest.client.core.Entity;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import javafx.scene.image.Image;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface CrudRepository<T extends Entity> extends ChangeRepository<T> {
    
    ResponseEntity<String> post(String rel, T entity) throws URISyntaxException;
    ResponseEntity<String> post(T entity) throws URISyntaxException;
    ResponseEntity<String> put(Resource<T> resource) throws URISyntaxException;
    
    ResponseEntity<String> freePost(String rel, T entity) throws URISyntaxException;
    ResponseEntity<String> freePost(T entity) throws URISyntaxException;
    
    URI save(String rel, T entity) throws URISyntaxException;    
    URI save(T entity) throws URISyntaxException;         
    Resource<T> update(Resource<T> resource) throws URISyntaxException;
    
    void delete(Resource<T> resource) throws URISyntaxException;   
    HttpStatus delete(String absRef) throws URISyntaxException;   
    void deleteWithAlert(Resource<? extends Entity> resource);
          
    void saveImage(Resource<T> resource, Image image);    
    HttpStatus postImage(Resource<T> resource, Image image);    
    void deleteImage(Resource<T> resource);
    
    Resource<T> getResource(URI uri);
    Resource<T> getResource(String path) throws URISyntaxException;    
    Resources<Resource<T>> getResources() throws URISyntaxException;
    Resources<Resource<T>> getResources(String[] rels) throws URISyntaxException;   
    Resources<Resource<T>> getResources(String path, String... rels) throws URISyntaxException;    
    Resource<T> getParameterizedResource(Map<String, Object> parameters, String... rels) throws URISyntaxException;
    Resource<T> getParameterizedResource(String path, Map<String, Object> parameters, String... rels) throws URISyntaxException;   
    Resources<Resource<T>> getParameterizedResources(Map<String, Object> parameters) throws URISyntaxException;
    Resources<Resource<T>> getParameterizedResources(Map<String, Object> parameters, String... rels) throws URISyntaxException;  
    Resources<Resource<T>> getParameterizedResources(String path, Map<String, Object> parameters, String... rels) throws URISyntaxException;
    PagedResources<Resource<T>> getPagedResources(Map<String, Object> parameters, String... rels) throws URISyntaxException;
    PagedResources<Resource<T>> getPagedResources(String path, Map<String, Object> parameters, String... rels) throws URISyntaxException;
    
    PagedResources<Resource<T>> getPagedResources(String rel) throws URISyntaxException;
    
    Long countQuery(String[] rels) throws URISyntaxException;   
    Long countQuery(String path, String... rels) throws URISyntaxException;
    
    Long countParameterizedQuery(Map<String, Object> parameters, String... rels) throws URISyntaxException;
    Long countParameterizedQuery(String path, Map<String, Object> parameters, String... rels) throws URISyntaxException;

    Boolean existsParameterizedQuery(Map<String, Object> parameters, String... rels) throws URISyntaxException;
    Boolean existsParameterizedQuery(String path, Map<String, Object> parameters, String... rels) throws URISyntaxException;

}
