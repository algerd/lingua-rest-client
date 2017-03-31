
package ru.javafx.lingua.rest.client.authorization;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import ru.javafx.lingua.rest.client.controller.words.WordsController;
import ru.javafx.lingua.rest.client.core.gui.BaseAwareController;
import ru.javafx.lingua.rest.client.core.gui.utils.Helper;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;

@FXMLController(
    value = "/fxml/authorization/Authorization.fxml",    
    title = "Authorization")
public class AuthorizationController extends BaseAwareController {
    
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private AuthorizationProperties authorizationProperties;
    
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button okButton;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Helper.limitTextInput(usernameTextField, 64);
        Helper.limitTextInput(passwordField, 64);
    }
    
    @FXML 
    private void onLinkRegistration() {
        requestViewService.showPane(RegistrationController.class);
    }
    
    @FXML
    private void handleOkButton() {
        authorizationProperties.setUsername(usernameTextField.getText());
        authorizationProperties.setPassword(passwordField.getText());
        if (authorization.check()) {
            authorizationProperties.updatePropertiesFile();
            requestViewService.showTab(WordsController.class);
        } else {
            Locale currentLocale = LocaleContextHolder.getLocale();        
            String message = messageSource.getMessage("error.authorization", null, currentLocale);    
            errorLabel.setText(message);
        }
    }

}
