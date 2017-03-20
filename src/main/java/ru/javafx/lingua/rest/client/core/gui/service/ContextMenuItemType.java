
package ru.javafx.lingua.rest.client.core.gui.service;

import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public enum ContextMenuItemType {
        
        ADD_WORD(new MenuItem("Add Word")),
        EDIT_WORD(new MenuItem("Edit Word")),
        DELETE_WORD(new MenuItem("Delete Word")),
                             
        SEPARATOR(new SeparatorMenuItem());
        
        private final MenuItem item;
        
        private ContextMenuItemType(MenuItem item) {
            this.item = item;
        }      
        public MenuItem get() {
            return item;
        }
}
