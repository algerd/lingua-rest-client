package ru.javafx.lingua.rest.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javafx.lingua.rest.client.authorization.AuthorizationController;
import ru.javafx.lingua.rest.client.controller.users.UsersController;
import ru.javafx.lingua.rest.client.controller.words.WordsController;
import ru.javafx.lingua.rest.client.core.gui.BaseAwareController;
import ru.javafx.lingua.rest.client.core.gui.MainController;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;

@FXMLController
public class TopBarController extends BaseAwareController {
         
    @Autowired
    private MainController parentController;
    
    @FXML
    private AnchorPane topBar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.setView(topBar);
    }
    
    @FXML
    private void showWords() {
        if (authorizationChecker.isAuthorize()) {
            requestViewService.showTab(WordsController.class);
        }    
    }
    
    @FXML
    private void showUsers() {
        if (authorizationChecker.isAuthorize()) {
            requestViewService.showTab(UsersController.class);
        }
    }
    
    @FXML
    private void logout() {
        if (authorizationChecker.isAuthorize()) {
            requestViewService.showPane(AuthorizationController.class);
        }
    }
     
}
