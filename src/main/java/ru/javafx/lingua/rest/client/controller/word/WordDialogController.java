
package ru.javafx.lingua.rest.client.controller.word;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import ru.javafx.lingua.rest.client.core.gui.BaseDialogController;
import ru.javafx.lingua.rest.client.entity.Word;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;
import ru.javafx.lingua.rest.client.core.datacore.impl.WrapChangedEntity;
import ru.javafx.lingua.rest.client.core.gui.inputImageBox.DialogImageBoxController;
import ru.javafx.lingua.rest.client.core.gui.utils.Helper;
import ru.javafx.lingua.rest.client.message.ErrorMessage;
import ru.javafx.lingua.rest.client.message.ErrorMessageHandler;
import ru.javafx.lingua.rest.client.repository.WordRepository;

@FXMLController(
    value = "/fxml/word/WordDialog.fxml",    
    title = "Word Dialog Window")
@Scope("prototype")
public class WordDialogController extends BaseDialogController<Word> {
    
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private ErrorMessageHandler errorMessageHandler;
    @Autowired
    private MessageSource messageSource;
    
    @FXML
    private DialogImageBoxController includedDialogImageBoxController;
    
    @FXML
    private TextField wordTextField;
    @FXML
    private TextField transcriptionTextField;
    @FXML
    private TextField translationTextField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {           
        Helper.limitTextInput(wordTextField, 255);
        Helper.limitTextInput(transcriptionTextField, 255);
        Helper.limitTextInput(translationTextField, 255);
        includedDialogImageBoxController.setStage(dialogStage);
    }
       
    @FXML
    @Override
    protected void handleOkButton() {
        
        Word word = new Word();
        word.setWord(wordTextField.getText().trim());
        word.setTranscription(transcriptionTextField.getText().trim());
        word.setTranslation(translationTextField.getText().trim());
        
        List<ErrorMessage> messages = errorMessageHandler.getErrorMessages(word);
        if (messages.isEmpty()) {
            messages.addAll(validateInput());                           
        }
        if (messages.isEmpty()) { 
            try { 
                try {
                    if (edit) {
                        resource.getContent().setWord(word.getWord());
                        resource.getContent().setTranscription(word.getTranscription());
                        resource.getContent().setTranslation(word.getTranslation());
                        wordRepository.put(resource);     
                    } else {
                        ResponseEntity<String> response = wordRepository.post(word);
                        resource = wordRepository.getResource(response.getHeaders().getLocation());
                    }
                } catch (HttpClientErrorException ex) {
                    errorMessage(ErrorMessage.parseJsonResponse(ex.getResponseBodyAsString()));
                    return;
                }    
                
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
        } else {
            errorMessage(messages);
        }
    }
    
    private List<ErrorMessage> validateInput() {
        List<ErrorMessage> messages = new ArrayList<>();
        try {
            String wordText = wordTextField.getText().trim().toLowerCase();
            if ((!edit || !resource.getContent().getWord().toLowerCase().equals(wordText)) && 
                    wordRepository.getPagedResources("word=" + wordText.trim().toLowerCase()).getMetadata().getTotalElements() > 0) {
                
                ErrorMessage errorMessage = new ErrorMessage();             
                errorMessage.setProperty("word");
                errorMessage.setMessage(messageSource.getMessage("error.word.word.duplicate", null, LocaleContextHolder.getLocale()));               
                messages.add(errorMessage);
            }
        } catch (URISyntaxException ex) {
            logger.error(ex.getMessage());
        }
        return messages;
    }
    
    @Override
    protected void add() {
    }
       
    @Override
    protected void edit() { 
        edit = true;
        Word word = resource.getContent();
        oldResource = new Resource<>(word.clone(), resource.getLinks());  
        
        wordTextField.setText(word.getWord());
        transcriptionTextField.setText(word.getTranscription());
        translationTextField.setText(word.getTranslation());
        
        if (resource.hasLink("get_image")) {
            includedDialogImageBoxController.setImage(resource.getLink("get_image").getHref()); 
        }
    }

}
