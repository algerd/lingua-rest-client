
package ru.javafx.lingua.rest.client.core.datacore.impl;

import ru.javafx.lingua.rest.client.core.datacore.BaseRepository;
import ru.javafx.lingua.rest.client.core.Entity;
import ru.javafx.lingua.rest.client.core.datacore.RelPath;
import ru.javafx.lingua.rest.client.core.datacore.SessionManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


@SuppressWarnings("unchecked")
public abstract class BaseRepositoryImpl<T extends Entity> implements BaseRepository<T> {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    protected String relPath;   
    protected Class<T> entityType;
    
    @Autowired
    protected SessionManager sessionManager;
    
    @Value("${spring.data.rest.basePath}")
    protected String basePath;
      
    public BaseRepositoryImpl() {
        super();
        setEntityType();
        setRelPath();
    }
       
    private void setEntityType() {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        while (!(genericSuperclass instanceof ParameterizedType)) {
            genericSuperclass = ((Class) genericSuperclass).getGenericSuperclass();
        }
        Type[] arguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        entityType = (Class<T>) arguments[0];
    }
    
    private void setRelPath() {
        RelPath annotation = entityType.getAnnotation(RelPath.class);
        relPath = (annotation == null || annotation.value().equals("")) ?
            entityType.getSimpleName().toLowerCase() :
            annotation.value();   
    }
    
}
