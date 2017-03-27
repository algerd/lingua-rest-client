
package ru.javafx.lingua.rest.client.authorization;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.xml.ws.http.HTTPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.javafx.lingua.rest.client.controller.words.WordsController;
import ru.javafx.lingua.rest.client.core.gui.service.RequestViewService;
import ru.javafx.lingua.rest.client.entity.User;
import ru.javafx.lingua.rest.client.fxintegrity.BaseFxmlController;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;
import ru.javafx.lingua.rest.client.repository.UserRepository;

@FXMLController(
    value = "/fxml/authorization/Registration.fxml",    
    title = "Registration")
public class RegistrationController extends BaseFxmlController {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorizationProperties authorizationProperties;
    @Autowired
    private RequestViewService requestViewService;
    
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
        String username = usernameTextField.getText().trim();
        String mail = mailTextField.getText().trim();
        String password = passwordTextField1.getText().trim();
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        //user.setMail(mail);
        
        try { 
            URI uri = new URI(authorizationProperties.getRegistrationurl());        
            RestTemplate restTemplate = new RestTemplate();       
            ResponseEntity<RegistrationResponseType> response = restTemplate.postForEntity(uri, new HttpEntity<>(user), RegistrationResponseType.class);
            //logger.info("Registration Response Code: {}", response.getStatusCode());
                        
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                RegistrationResponseType responseMsg = response.getBody();
                //logger.info("Registration Response: {}", responseMsg);
                
                if (responseMsg.equals(RegistrationResponseType.OK)) { 
                    authorizationProperties.setUsername(username);
                    authorizationProperties.setPassword(password);
                    authorizationProperties.updatePropertiesFile();
                    requestViewService.showTab(WordsController.class);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setContentText(responseMsg.toString());
                    alert.showAndWait();
                }
            } else {
                throw new HTTPException(response.getStatusCode().value());
            }
        }  
        catch (URISyntaxException | HTTPException ex) {
            logger.error(ex.getMessage());
        }
        
    }

}
