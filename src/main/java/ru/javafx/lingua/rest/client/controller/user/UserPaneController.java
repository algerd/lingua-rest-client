
package ru.javafx.lingua.rest.client.controller.user;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.Resource;
import ru.javafx.lingua.rest.client.core.datacore.impl.WrapChangedEntity;
import ru.javafx.lingua.rest.client.core.gui.EntityController;
import ru.javafx.lingua.rest.client.entity.User;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;
import ru.javafx.lingua.rest.client.repository.UserRepository;

@FXMLController(
    value = "/fxml/user/UserPane.fxml",    
    title = "User")
@Scope("prototype")
public class UserPaneController extends EntityController<User> {
    
    @Autowired
    private UserRepository userRepository;
            
    @FXML
    private ImageView userImageView;
    @FXML
    private Label usernameLabel; 
    @FXML
    private Label mailLabel;    
    @FXML
    private Label roleLabel;
    @FXML
    private Label ipLabel;    
    @FXML
    private Label createdLabel; 
    @FXML
    private Label lastVisitedLabel; 
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {        
    }
    
    @Override
    public void show() {
        showDetails();
        initRepositoryListeners();      
    }
    
    private void showDetails() {   
        usernameLabel.textProperty().bind(resource.getContent().usernameProperty()); 
        mailLabel.textProperty().bind(resource.getContent().mailProperty()); 
        ipLabel.textProperty().bind(resource.getContent().ipProperty());
        createdLabel.textProperty().bind(resource.getContent().createdProperty().asString());
        lastVisitedLabel.textProperty().bind(resource.getContent().lastVisitedProperty().asString());
        titleProperty().bind(usernameLabel.textProperty());
        showImage();       
    }
    
    private void showImage() {
        if (resource.hasLink("get_image")) {
            userImageView.setImage(new Image(resource.getLink("get_image").getHref()));  
        } 
    }
    
    private void initRepositoryListeners() {
        userRepository.clearDeleteListeners(this);
        userRepository.clearUpdateListeners(this);
        
        userRepository.addDeleteListener(this::deleted, this);
        userRepository.addUpdateListener(this::updated, this);
    }
    
    private void deleted(ObservableValue observable, Object oldVal, Object newVal) {    
        Resource<User> oldResource = ((WrapChangedEntity<Resource<User>>) newVal).getOld();
        if (oldResource.getId().equals(resource.getId())) {
            view.setVisible(false);
        }    
    }
    
    private void updated(ObservableValue observable, Object oldVal, Object newVal) {       
        Resource<User> newResource = ((WrapChangedEntity<Resource<User>>) newVal).getNew();
        if (newResource.getId().equals(resource.getId())) {         
            showImage();
        }     
    }

}
