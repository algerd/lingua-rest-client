
package ru.javafx.lingua.rest.client.core.gui.service;

import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseEvent;
import org.springframework.hateoas.Resource;
import ru.javafx.lingua.rest.client.core.Entity;

public interface ContextMenuService {
    /**
     * Добавить элемент меню и задать ему значение сущности entity
     * @param itemType Тип элемента меню.
     * @param entity Значение для элемента меню.
     */
    void add(ContextMenuItemType itemType, Resource<? extends Entity> entity);
    
    void add(ContextMenuItemType itemType);
    
    void show(Parent parent, MouseEvent mouseEvent);
    
    void clear();
    
    ContextMenu getContextMenu();
}
