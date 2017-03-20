
package ru.javafx.lingua.rest.client.core.gui.service.impl;

import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;
import ru.javafx.lingua.rest.client.controller.word.WordDialogController;
import ru.javafx.lingua.rest.client.core.Entity;
import ru.javafx.lingua.rest.client.core.gui.service.ContextMenuItemType;
import static ru.javafx.lingua.rest.client.core.gui.service.ContextMenuItemType.ADD_WORD;
import static ru.javafx.lingua.rest.client.core.gui.service.ContextMenuItemType.DELETE_WORD;
import static ru.javafx.lingua.rest.client.core.gui.service.ContextMenuItemType.EDIT_WORD;
import ru.javafx.lingua.rest.client.core.gui.service.ContextMenuService;
import ru.javafx.lingua.rest.client.core.gui.service.RequestViewService;
import ru.javafx.lingua.rest.client.repository.WordRepository;

@Service
public class ContextMenuServiceImpl implements ContextMenuService { 

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private RequestViewService requestViewService;   
    @Autowired
    private WordRepository artistRepository;

         
    private final ContextMenu contextMenu = new ContextMenu();
    private final Map<ContextMenuItemType, EventHandler<ActionEvent>> menuMap = new HashMap<>();
    private Map<ContextMenuItemType, Resource<? extends Entity>> valueMap = new HashMap<>();
        
    public ContextMenuServiceImpl() {      
        menuMap.put(ADD_WORD, e -> requestViewService.showDialog(WordDialogController.class, valueMap.get(ADD_WORD)));      
        menuMap.put(EDIT_WORD, e -> requestViewService.showDialog(WordDialogController.class, valueMap.get(EDIT_WORD)));          
        menuMap.put(DELETE_WORD, e -> artistRepository.deleteWithAlert(valueMap.get(DELETE_WORD)));             
    }
    
    @Override
    public void add(ContextMenuItemType itemType, Resource<? extends Entity> resource) {  
        // сохранить в карте переменных resource для элемента меню itemType
        valueMap.put(itemType, resource);
        // получить элемент меню
        MenuItem item = itemType.get();   
        // задать экшен элементу меню
        item.setOnAction(menuMap.get(itemType));
        // добавить в меню элемент
        contextMenu.getItems().add(item);   
    }
    
    @Override
    public void add(ContextMenuItemType itemType) {
        contextMenu.getItems().add(itemType.get());
    }
        
    @Override
    public void show(Parent parent, MouseEvent mouseEvent) {
        contextMenu.show(parent, mouseEvent.getScreenX(), mouseEvent.getScreenY());
    }
      
    @Override
    public void clear() {
        contextMenu.hide();
        contextMenu.getItems().clear();
        valueMap.clear();
    }
    
    @Override
    public ContextMenu getContextMenu() {
        return contextMenu;
    }
    
}
