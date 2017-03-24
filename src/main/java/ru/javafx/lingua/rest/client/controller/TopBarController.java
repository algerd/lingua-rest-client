package ru.javafx.lingua.rest.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javafx.lingua.rest.client.controller.users.UsersController;
import ru.javafx.lingua.rest.client.controller.words.WordsController;
import ru.javafx.lingua.rest.client.core.gui.MainController;
import ru.javafx.lingua.rest.client.core.gui.service.RequestViewService;
import ru.javafx.lingua.rest.client.fxintegrity.BaseFxmlController;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;

@FXMLController
public class TopBarController extends BaseFxmlController {
         
    @Autowired
    private MainController parentController;
    
    @Autowired
    private RequestViewService requestViewService;
    
    @FXML
    private AnchorPane topBar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.setView(topBar);
    }
    
    @FXML
    private void showWords() {
        requestViewService.show(WordsController.class);
    }
    
    @FXML
    private void showUsers() {
        requestViewService.show(UsersController.class);
    }
     
}
