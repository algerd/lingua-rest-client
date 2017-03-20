
package ru.javafx.lingua.rest.client.core.datacore.impl;

import ru.javafx.lingua.rest.client.core.datacore.Changeable;

public class WrapChangedEntity<T> implements Changeable<T> {

    private final T oldEntity;
    private final T newEntity;
      
    public WrapChangedEntity(T oldEntity, T newEntity) {
        this.oldEntity = oldEntity;
        this.newEntity = newEntity;
    }  
       
    @Override
    public T getOld() {
        return oldEntity;
    }

    @Override
    public T getNew() {
        return newEntity;
    }

}
