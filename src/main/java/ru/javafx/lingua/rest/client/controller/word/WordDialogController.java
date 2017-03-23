
package ru.javafx.lingua.rest.client.controller.word;

import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.context.annotation.Scope;
import ru.javafx.lingua.rest.client.core.gui.BaseDialogController;
import ru.javafx.lingua.rest.client.entity.Word;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import ru.javafx.lingua.rest.client.core.datacore.impl.WrapChangedEntity;
import ru.javafx.lingua.rest.client.core.gui.inputImageBox.DialogImageBoxController;
import ru.javafx.lingua.rest.client.core.gui.utils.Helper;
import ru.javafx.lingua.rest.client.repository.WordRepository;

@FXMLController(
    value = "/fxml/word/WordDialog.fxml",    
    title = "Word Dialog Window")
@Scope("prototype")
public class WordDialogController extends BaseDialogController<Word> {
    
    private Word word; 
    
    @Autowired
    private WordRepository wordRepository;
    
    @FXML
    private DialogImageBoxController includedDialogImageBoxController;
    
    @FXML
    private AnchorPane view;
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
        if (isInputValid()) { 
            word.setWord(wordTextField.getText().trim());
            word.setTranscription(transcriptionTextField.getText().trim());
            word.setTranslation(translationTextField.getText().trim());
            //word.setCreated(LocalDateTime.now());
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
            
        }
    }
    
    @Override
    protected boolean isInputValid() {
        String errorMessage = "";
        String wordText = wordTextField.getText(); 
        String translation = translationTextField.getText();
        if (wordText == null || wordText.trim().equals("")) {
            errorMessage = "Введите слово!\n";
        } else if(translation == null || translation.trim().equals("")) {
            errorMessage = "Введите перевод!\n";
        } else { 
            wordText = wordText.trim().toLowerCase();
            try {
                if (!word.getWord().toLowerCase().equals(wordText) && 
                        wordRepository.getPagedResources("word=" + wordText.trim().toLowerCase()).getMetadata().getTotalElements() > 0) {
                    errorMessage = "Такое слово уже есть!\n";
                }
            } catch (URISyntaxException ex) {
                logger.error(ex.getMessage());
            } 
        }
        if (errorMessage.equals("")) {
            return true;
        } 
        else {
            errorMessage(errorMessage);         
            return false;
        }
    }
    
    @Override
    protected void add() {
        word = new Word();    
    }
       
    @Override
    protected void edit() { 
        edit = true;
        word = resource.getContent();
        oldResource = new Resource<>(word.clone(), resource.getLinks());  
        
        wordTextField.setText(word.getWord());
        transcriptionTextField.setText(word.getTranscription());
        translationTextField.setText(word.getTranslation());
        
        if (resource.hasLink("get_image")) {
            includedDialogImageBoxController.setImage(resource.getLink("get_image").getHref()); 
        }
    }

}
