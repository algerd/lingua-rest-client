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
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;

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
    //private Pane pane;
    private SplitPane splitPane;
    
    private TabPane tabPane = new TabPane();
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {        
        //TabPaneDetacher.create().makeTabsDetachable(tabPane);
    }
    
    @Override
    public void showTab(BaseFxmlController controller) { 
        if (!splitPane.getItems().contains(tabPane)) {
            addItem(tabPane);
        }       
        Tab tab = new Tab();
        tab.setClosable(true); 
        tab.textProperty().bind(controller.titleProperty());
        tab.setContent(controller.getView()); 
        tabPane.getTabs().add(tab); 
        tabPane.getSelectionModel().selectLast();       
    }
    
    @Override
    public void showPane(BaseFxmlController controller) {
       addItem(controller.getView());
    }
    
    private void addItem(Parent view) {
        if (splitPane.getItems().size() > 1) {
            splitPane.getItems().set(1, view);
        } else {
           splitPane.getItems().add(view); 
        }
    }
            
}
