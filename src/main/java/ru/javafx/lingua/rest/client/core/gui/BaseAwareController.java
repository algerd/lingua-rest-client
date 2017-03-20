
package ru.javafx.lingua.rest.client.core.gui;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javafx.lingua.rest.client.core.gui.service.ContextMenuService;
import ru.javafx.lingua.rest.client.core.gui.service.RequestViewService;
import ru.javafx.lingua.rest.client.fxintegrity.BaseFxmlController;

public abstract class BaseAwareController extends BaseFxmlController {
        
    @Autowired
    protected RequestViewService requestViewService;
    @Autowired
    protected ContextMenuService contextMenuService;

}
