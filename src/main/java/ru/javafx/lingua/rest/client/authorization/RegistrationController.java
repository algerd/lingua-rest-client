
package ru.javafx.lingua.rest.client.authorization;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javafx.lingua.rest.client.fxintegrity.BaseFxmlController;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;
import ru.javafx.lingua.rest.client.repository.UserRepository;

@FXMLController(
    value = "/fxml/authorization/Registration.fxml",    
    title = "Registration")
public class RegistrationController extends BaseFxmlController {
    
    @Autowired
    private UserRepository userRepository;
    
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField mailTextField;
    @FXML
    private TextField passwordTextField1;
    @FXML
    private TextField passwordTextField2;
    @FXML
    private Button okButton;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    
    @FXML
    private void handleOkButton() {
        
    }

}
