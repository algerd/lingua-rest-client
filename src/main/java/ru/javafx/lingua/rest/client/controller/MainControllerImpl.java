package ru.javafx.lingua.rest.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import ru.javafx.lingua.rest.client.core.gui.MainController;
import ru.javafx.lingua.rest.client.fxintegrity.BaseFxmlController;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;
import org.springframework.beans.factory.annotation.Autowired;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

@FXMLController(
    value = "/fxml/Main.fxml",
    css = {"/styles/Styles.css"},
    title = "Lingua")
public class MainControllerImpl extends BaseFxmlController implements MainController {
       
    @Autowired
    private TopBarController topBarController;
    @Autowired
    private LeftBarController leftBarController;
         
    @FXML
    private TabPane tabPane;
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {        
        //TabPaneDetacher.create().makeTabsDetachable(tabPane);
    }
    
    @Override
    public void show(BaseFxmlController controller) {      
        Tab tab = new Tab();
        tab.setClosable(true); 
        tab.textProperty().bind(controller.titleProperty());
        tab.setContent(controller.getView()); 
        tabPane.getTabs().add(tab); 
        tabPane.getSelectionModel().selectLast();       
    }
            
}
