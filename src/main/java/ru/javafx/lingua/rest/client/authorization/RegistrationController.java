
package ru.javafx.lingua.rest.client.authorization;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.xml.ws.http.HTTPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.javafx.lingua.rest.client.controller.words.WordsController;
import ru.javafx.lingua.rest.client.core.gui.service.RequestViewService;
import ru.javafx.lingua.rest.client.entity.User;
import ru.javafx.lingua.rest.client.fxintegrity.BaseFxmlController;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;
import ru.javafx.lingua.rest.client.message.MessageDTO;
import ru.javafx.lingua.rest.client.repository.UserRepository;
import org.springframework.boot.json.BasicJsonParser;

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
            ResponseEntity<String> response = restTemplate.postForEntity(uri, new HttpEntity<>(user), String.class);
                                  
            logger.info("Registration ResponseCode: {}", response.getStatusCode());                 
            if (response.getStatusCode().equals(HttpStatus.OK)) {            
                logger.info("Registration ResponseBody: {}", response.getBody());

                JsonParser jsonParser = new BasicJsonParser();           
                List<Object> list = jsonParser.parseList(response.getBody());
                List<MessageDTO> messages = new ArrayList<>();
                for (Object object : list) {
                    Map<String, String> obj = (Map<String, String>) object;
                    messages.add(new MessageDTO(obj.get("type"), obj.get("message")));
                }
                                                               
                if (messages.isEmpty()) { 
                    authorizationProperties.setUsername(username);
                    authorizationProperties.setPassword(password);
                    authorizationProperties.updatePropertiesFile();
                    requestViewService.showTab(WordsController.class);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setContentText(messages.toString());
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
