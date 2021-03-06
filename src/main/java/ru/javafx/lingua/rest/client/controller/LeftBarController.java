package ru.javafx.lingua.rest.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javafx.lingua.rest.client.core.gui.BaseAwareController;
import ru.javafx.lingua.rest.client.core.gui.MainController;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;

@FXMLController
public class LeftBarController extends BaseAwareController {
       
    @FXML
    private AnchorPane leftBar;
    
    @Autowired
    private MainController parentController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        super.setView(leftBar);
    }
    /*
    public void show(BaseFxmlController controller) {
        leftBarVBox.getChildren().add(controller.getView());
    }
    */  
}
