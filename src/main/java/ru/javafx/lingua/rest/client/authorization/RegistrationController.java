
package ru.javafx.lingua.rest.client.authorization;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
import org.springframework.boot.json.BasicJsonParser;

@FXMLController(
    value = "/fxml/authorization/Registration.fxml",    
    title = "Registration")
public class RegistrationController extends BaseFxmlController {
    
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
    private Label loginErrorLabel;
    @FXML
    private Label mailErrorLabel;
    @FXML
    private Label password1ErrorLabel;
    @FXML
    private Label password2ErrorLabel; 
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {     
    }
  
    @FXML
    private void handleOkButton() {
        clearErrorLabels();
  
        if (isValidInput()) {
            User user = new User();
            user.setUsername(usernameTextField.getText().trim());
            user.setPassword(passwordTextField1.getText().trim());
            //user.setMail(mailTextField.getText().trim());   
            registr(user);
        }
    }
    
    private void clearErrorLabels() {
        loginErrorLabel.setText("");
        mailErrorLabel.setText("");
        password1ErrorLabel.setText("");
        password2ErrorLabel.setText("");
    }
    
    private boolean isValidInput() {
        boolean isValid = true;
        if (!passwordTextField1.getText().trim().equals(passwordTextField2.getText().trim())) {
            password2ErrorLabel.setText("Repeat Password!");
            isValid = false;
        }
        if (usernameTextField.getText().trim().equals("")) {
            loginErrorLabel.setText("Input username!");
            isValid = false;
        }
        if (mailTextField.getText().trim().equals("")) {
            mailErrorLabel.setText("Input mail!");
            isValid = false;
        }
        if (passwordTextField1.getText().trim().equals("")) {
            password1ErrorLabel.setText("Input password1!");
            isValid = false;
        }
        if (passwordTextField2.getText().trim().equals("")) {
            password2ErrorLabel.setText("Input password2!");
            isValid = false;
        }
        return isValid;
    }
    
    private void registr(User user) {
        try { 
            URI uri = new URI(authorizationProperties.getRegistrationurl());        
            RestTemplate restTemplate = new RestTemplate();       
            ResponseEntity<String> response = restTemplate.postForEntity(uri, new HttpEntity<>(user), String.class);
            logger.info("Registration ResponseCode: {}", response.getStatusCode());
            
            if (response.getStatusCode().equals(HttpStatus.OK)) {            
                //logger.info("Registration ResponseBody: {}", response.getBody());          
                List<MessageDTO> messages = parseJsonResponse(response);
                if (messages.isEmpty()) { 
                    authorizationProperties.setUsername(user.getUsername());
                    authorizationProperties.setPassword(user.getPassword());
                    //authorizationProperties.setMail(user.getMail());
                    authorizationProperties.updatePropertiesFile();
                    requestViewService.showTab(WordsController.class);
                } else {
                    messages.stream().forEach(this::validateFields);
                }
            } else {
                throw new HTTPException(response.getStatusCode().value());
            }
        }  
        catch (URISyntaxException | HTTPException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    private List<MessageDTO> parseJsonResponse(ResponseEntity<String> response) {
        JsonParser jsonParser = new BasicJsonParser();           
        List<Object> list = jsonParser.parseList(response.getBody());
        List<MessageDTO> messages = new ArrayList<>();
        for (Object object : list) {
            Map<String, String> obj = (Map<String, String>) object;
            messages.add(new MessageDTO(obj.get("type"), obj.get("message"), obj.get("field")));
        }
        return messages;
    }
    
    private void validateFields(MessageDTO message) {
        if (message.getField().equals("username")) { 
            loginErrorLabel.setText(message.getMessage());
        }
        if (message.getField().equals("mail")) { 
            mailErrorLabel.setText(message.getMessage());
        }
        if (message.getField().equals("password")) { 
            password1ErrorLabel.setText(message.getMessage());
        }  
    }
     
}
