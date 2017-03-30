
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.xml.ws.http.HTTPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.javafx.lingua.rest.client.controller.words.WordsController;
import ru.javafx.lingua.rest.client.entity.User;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;
import ru.javafx.lingua.rest.client.message.MessageDTO;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import ru.javafx.lingua.rest.client.core.gui.BaseAwareController;
import ru.javafx.lingua.rest.client.message.ErrorMessageHandler;
import ru.javafx.lingua.rest.client.message.MessageType;

@FXMLController(
    value = "/fxml/authorization/Registration.fxml",    
    title = "Registration")
public class RegistrationController extends BaseAwareController {
    
    private final User user = new User();
    
    @Autowired
    private AuthorizationProperties authorizationProperties;
    @Autowired
    private ErrorMessageHandler errorMessageHandler;
    @Autowired
    private MessageSource messageSource;
    
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField mailTextField;
    @FXML
    private PasswordField passwordField1;
    @FXML
    private PasswordField passwordField2;
    @FXML
    private Label loginErrorLabel;
    @FXML
    private Label mailErrorLabel;
    @FXML
    private Label password1ErrorLabel;
    @FXML
    private Label password2ErrorLabel; 
    @FXML
    private Label errorLabel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {  
    }
  
    @FXML
    private void handleOkButton() {
        clearErrorLabels();
  
        user.setUsername(usernameTextField.getText().trim());
        user.setPassword(passwordField1.getText().trim());
        user.setMail(mailTextField.getText().trim());
        
        List<MessageDTO> messages = errorMessageHandler.getErrorMessages(user);
        validateInput(messages);
        logger.info("ErrorMessages: {}", messages);
        
        if (messages.isEmpty()) {
            registr();
        } else {
            messages.forEach(this::validateFields);
        }
    }
    
    private void clearErrorLabels() {
        loginErrorLabel.setText("");
        mailErrorLabel.setText("");
        password1ErrorLabel.setText("");
        password2ErrorLabel.setText("");
    }
    
    private void validateInput(List<MessageDTO> messages) {
        if (!passwordField1.getText().trim().equals(passwordField2.getText().trim())) {
            messages.add(new MessageDTO(
                    MessageType.ERROR.toString(),
                    messageSource.getMessage("error.user.password.repeat", null, LocaleContextHolder.getLocale()),
                    "password2"
            ));
        }
    }
    
    private void registr() {
        try { 
            URI uri = new URI(authorizationProperties.getRegistrationurl());        
            RestTemplate restTemplate = new RestTemplate();       
            ResponseEntity<String> response = restTemplate.postForEntity(uri, new HttpEntity<>(user), String.class);
            //logger.info("Registration ResponseCode: {}", response.getStatusCode());
            
            if (response.getStatusCode().equals(HttpStatus.OK)) {            
                //logger.info("Registration ResponseBody: {}", response.getBody());          
                List<MessageDTO> messages = parseJsonResponse(response);
                if (messages.isEmpty()) { 
                    authorizationProperties.setUsername(user.getUsername());
                    authorizationProperties.setPassword(user.getPassword());
                    authorizationProperties.setMail(user.getMail());
                    authorizationProperties.updatePropertiesFile();
                    if (authorizationChecker.check()) {
                        requestViewService.showTab(WordsController.class);
                    }    
                } else {
                    messages.forEach(this::validateFields);
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
        switch(message.getField()) {
            case "username" :
                loginErrorLabel.setText(message.getMessage());
                break;
            case "mail" :
                mailErrorLabel.setText(message.getMessage());
                break;
            case "password" :
                password1ErrorLabel.setText(message.getMessage());
                break;
            case "password2" :
                password2ErrorLabel.setText(message.getMessage());
                break;
            default:
                errorLabel.setText(message.getMessage());
                break; 
        }
    }
     
}
