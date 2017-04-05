
package ru.javafx.lingua.rest.client.authorization;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.xml.ws.http.HTTPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.javafx.lingua.rest.client.controller.words.WordsController;
import ru.javafx.lingua.rest.client.entity.User;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.client.HttpClientErrorException;
import ru.javafx.lingua.rest.client.core.gui.BaseAwareController;
import ru.javafx.lingua.rest.client.core.gui.utils.Helper;
import ru.javafx.lingua.rest.client.message.ErrorMessage;
import ru.javafx.lingua.rest.client.message.ErrorMessageHandler;
import ru.javafx.lingua.rest.client.repository.UserRepository;

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
    @Autowired
    private UserRepository userRepository;
    
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
        Helper.limitTextInput(usernameTextField, 64);
        Helper.limitTextInput(mailTextField, 64);
        Helper.limitTextInput(passwordField1, 64);
        Helper.limitTextInput(passwordField2, 64);
    }

    @FXML
    private void handleOkButton() {
        clearErrorLabels();
  
        user.setUsername(usernameTextField.getText().trim());
        user.setPassword(passwordField1.getText().trim());
        user.setMail(mailTextField.getText().trim());
        
        List<ErrorMessage> messages = errorMessageHandler.getErrorMessages(user);
        if (messages.isEmpty()) {
            messages.addAll(validateInput());                           
        }
        //logger.info("ErrorMessages: {}", messages);
        
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
    
    private List<ErrorMessage> validateInput() {
        List<ErrorMessage> messages = new ArrayList<>();
        if (!passwordField1.getText().trim().equals(passwordField2.getText().trim())) {
            ErrorMessage errorMessage = new ErrorMessage();             
            errorMessage.setProperty("password2");
            errorMessage.setMessage(messageSource.getMessage("error.user.password.repeat", null, LocaleContextHolder.getLocale()));               
            messages.add(errorMessage);
        }
        return messages;
    }
    
    private void registr() {
        try {
            try {
                ResponseEntity<String> response = userRepository.freePost(user);
                if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                    authorizationProperties.setUsername(user.getUsername());
                    authorizationProperties.setPassword(user.getPassword());
                    authorizationProperties.setMail(user.getMail());
                    authorizationProperties.updatePropertiesFile();
                    if (authorization.check()) {
                        requestViewService.showTab(WordsController.class);
                    } 
                }    
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                    ErrorMessage.parseJsonResponse(ex.getResponseBodyAsString()).forEach(this::validateFields);
                }
            }
        } catch (URISyntaxException | HTTPException ex) {
            logger.error(ex.getMessage());
        }    
    }
      
    private void validateFields(ErrorMessage errorMessage) {
        switch(errorMessage.getProperty()) {
            case "username" :
                loginErrorLabel.setText(errorMessage.getMessage());
                break;
            case "mail" :
                mailErrorLabel.setText(errorMessage.getMessage());
                break;
            case "password" :
                password1ErrorLabel.setText(errorMessage.getMessage());
                break;
            case "password2" :
                password2ErrorLabel.setText(errorMessage.getMessage());
                break;
            default:
                errorLabel.setText(errorMessage.getMessage());
                break; 
        }
    }
     
}
