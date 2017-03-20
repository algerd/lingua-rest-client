
package ru.javafx.lingua.rest.client.controller.word;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.Resource;
import ru.javafx.lingua.rest.client.core.datacore.impl.WrapChangedEntity;
import ru.javafx.lingua.rest.client.core.gui.EntityController;
import static ru.javafx.lingua.rest.client.core.gui.service.ContextMenuItemType.ADD_WORD;
import static ru.javafx.lingua.rest.client.core.gui.service.ContextMenuItemType.DELETE_WORD;
import static ru.javafx.lingua.rest.client.core.gui.service.ContextMenuItemType.EDIT_WORD;
import ru.javafx.lingua.rest.client.entity.Word;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;
import ru.javafx.lingua.rest.client.repository.WordRepository;

@FXMLController(
    value = "/fxml/word/WordPane.fxml",    
    title = "Word")
@Scope("prototype")
public class WordPaneController extends EntityController<Word> {
    
    @Autowired
    private WordRepository wordRepository;
            
    @FXML
    private ImageView wordImageView;
    @FXML
    private Label wordLabel;       
    @FXML
    private Label transcriptionLabel; 
    @FXML
    private Label translationLabel; 
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    @Override
    public void show() {
        showDetails();
        initRepositoryListeners();
    }
    
    private void showDetails() {   
        wordLabel.textProperty().bind(resource.getContent().wordProperty());                                 
        transcriptionLabel.textProperty().bind(resource.getContent().transcriptionProperty());
        translationLabel.textProperty().bind(resource.getContent().translationProperty());
        titleProperty().bind(wordLabel.textProperty());
        showImage();       
    }
    
    private void showImage() {
        if (resource.hasLink("get_image")) {
            wordImageView.setImage(new Image(resource.getLink("get_image").getHref()));  
        } 
    }
    
    private void initRepositoryListeners() {
        wordRepository.clearDeleteListeners(this);
        wordRepository.clearUpdateListeners(this);
        
        wordRepository.addDeleteListener(this::deletedArtist, this);
        wordRepository.addUpdateListener(this::updatedArtist, this);
    }
    
    private void deletedArtist(ObservableValue observable, Object oldVal, Object newVal) {    
        Resource<Word> oldResource = ((WrapChangedEntity<Resource<Word>>) newVal).getOld();
        if (oldResource.getId().equals(resource.getId())) {
            view.setVisible(false);
        }    
    }
    
    private void updatedArtist(ObservableValue observable, Object oldVal, Object newVal) {       
        Resource<Word> newResource = ((WrapChangedEntity<Resource<Word>>) newVal).getNew();
        if (newResource.getId().equals(resource.getId())) {         
            showImage();
        }     
    }
    
    @FXML
    private void showContextMenu(MouseEvent mouseEvent) {
        contextMenuService.clear();
		if (mouseEvent.getButton() == MouseButton.SECONDARY) {       
            contextMenuService.add(ADD_WORD, null);
            contextMenuService.add(EDIT_WORD, resource);
            contextMenuService.add(DELETE_WORD, resource);                       
            contextMenuService.show(view, mouseEvent);
        }      
    }

}
