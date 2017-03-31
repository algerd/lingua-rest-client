
package ru.javafx.lingua.rest.client.controller.user;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.Resource;
import ru.javafx.lingua.rest.client.core.datacore.impl.WrapChangedEntity;
import ru.javafx.lingua.rest.client.core.gui.BaseDialogController;
import ru.javafx.lingua.rest.client.core.gui.inputImageBox.DialogImageBoxController;
import ru.javafx.lingua.rest.client.core.gui.utils.Helper;
import ru.javafx.lingua.rest.client.entity.User;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;
import ru.javafx.lingua.rest.client.repository.UserRepository;

@FXMLController(
    value = "/fxml/user/UserDialog.fxml",    
    title = "User Dialog Window")
@Scope("prototype")
public class UserDialogController extends BaseDialogController<User> {
    
    private User user; 
    
    @Autowired
    private UserRepository userRepository;
    
    @FXML
    private DialogImageBoxController includedDialogImageBoxController;
    
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField mailTextField;
    @FXML
    private PasswordField passwordField1;
    @FXML
    private PasswordField passwordField2;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {           
        Helper.limitTextInput(usernameTextField, 64);
        Helper.limitTextInput(mailTextField, 64);
        Helper.limitTextInput(passwordField1, 64);
        Helper.limitTextInput(passwordField2, 64);
        includedDialogImageBoxController.setStage(dialogStage);
    }
    
    @Override
    protected void add() {
        user = new User();    
    }
       
    @Override
    protected void edit() { 
        edit = true;
        user = resource.getContent();
        //oldResource = new Resource<>(user.clone(), resource.getLinks());  
        
        usernameTextField.setText(user.getUsername());
        mailTextField.setText(user.getMail());
        
        if (resource.hasLink("get_image")) {
            includedDialogImageBoxController.setImage(resource.getLink("get_image").getHref()); 
        }
    }
    
    @Override
    protected boolean isInputValid() {
        return true;
    }
    
    @FXML
    @Override
    protected void handleOkButton() {
        if (isInputValid()) { 
            user.setUsername(usernameTextField.getText().trim());
            user.setMail(mailTextField.getText().trim());
            user.setPassword(passwordField1.getText().trim());
            /*
            try { 
                resource = edit ? wordRepository.update(resource) : wordRepository.saveAndGetResource(word);
                //logger.info("Saved Artist Resource: {}", resource);                
                if (includedDialogImageBoxController.isChangedImage()) {
                    wordRepository.saveImage(resource, includedDialogImageBoxController.getImage());
                    includedDialogImageBoxController.setChangedImage(false);                              
                }                
                if (edit) {
                    wordRepository.setUpdated(new WrapChangedEntity<>(oldResource, resource));
                } else {
                    wordRepository.setAdded(new WrapChangedEntity<>(null, resource));
                } 
                dialogStage.close();
                edit = false;
            } catch (URISyntaxException ex) {
                logger.error(ex.getMessage());
            }
            */
        }
    }
    
}
