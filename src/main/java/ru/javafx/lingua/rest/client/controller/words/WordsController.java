
package ru.javafx.lingua.rest.client.controller.words;

import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import ru.javafx.lingua.rest.client.controller.word.WordPaneController;
import ru.javafx.lingua.rest.client.core.datacore.operators.StringOperator;
import ru.javafx.lingua.rest.client.core.gui.paginator.PagedTableController;
import static ru.javafx.lingua.rest.client.core.gui.service.ContextMenuItemType.ADD_WORD;
import static ru.javafx.lingua.rest.client.core.gui.service.ContextMenuItemType.DELETE_WORD;
import static ru.javafx.lingua.rest.client.core.gui.service.ContextMenuItemType.EDIT_WORD;
import ru.javafx.lingua.rest.client.entity.Word;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;
import ru.javafx.lingua.rest.client.repository.WordRepository;

@FXMLController(
    value = "/fxml/words/Words.fxml",    
    title = "Words")
public class WordsController extends PagedTableController<Word> {

    private String searchString = "";
    
    @Autowired
    private WordRepository wordRepository;

    @FXML
    private TableColumn<Resource<Word>, String> wordColumn;
    @FXML
    private TableColumn<Resource<Word>, String> transcriptionColumn;
    @FXML
    private TableColumn<Resource<Word>, String> translationColumn; 

    public WordsController() {
        //logger.info("created WordsController :{}", this);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sort = "Word";
        order = "Asc";
        super.initPagedTableController(wordRepository);               
        initRepositoryListeners();   
    }
    
    @Override
    protected void initPagedTable() {     
        wordColumn.setCellValueFactory(cellData -> cellData.getValue().getContent().wordProperty());  
        transcriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getContent().transcriptionProperty());
        translationColumn.setCellValueFactory(cellData -> cellData.getValue().getContent().translationProperty()); 
    }
    
    @Override
    protected String createParamString() {
        List<String> params = new ArrayList<>();       
        if (!searchString.equals("")) {
            params.add("name=" + StringOperator.STARTS_WITH);
            params.add("name=" + searchString);
        }
        params.addAll(paginatorPaneController.getPaginator().getParameterList());
        String paramStr = params.isEmpty()? "" : String.join("&", params);
        logger.info("paramStr :{}", paramStr);
        return paramStr;
    }
    
    private void initRepositoryListeners() {
        wordRepository.clearChangeListeners(this);       

        wordRepository.addDeleteListener((observable, oldVal, newVal) -> filter(), this); 
        wordRepository.addInsertListener((observable, oldVal, newVal) -> filter(), this);
    }
    
    private void filter() {
        paginatorPaneController.getPaginator().setSort(getSort());
        setPageValue();
    }
    
    @FXML
    private void onMouseClickTable(MouseEvent mouseEvent) { 
        boolean isShowingContextMenu = contextMenuService.getContextMenu().isShowing();     
        contextMenuService.clear();        
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            // если контекстное меню выбрано, то лкм сбрасывает контекстное меню и выбор в таблице
            if (isShowingContextMenu) {
                clearSelectionTable();
            }
            // если лкм выбрана запись - показать её
            if (selectedItem != null) {
                requestViewService.show(WordPaneController.class, selectedItem);
            }           
        }      
        else if (mouseEvent.getButton() == MouseButton.SECONDARY) { 
            contextMenuService.add(ADD_WORD, null);
            contextMenuService.add(EDIT_WORD, selectedItem);
            contextMenuService.add(DELETE_WORD, selectedItem);                                  
            contextMenuService.show(view, mouseEvent);         
        }    
    }
    
    /**
     * При ПКМ по странице артиста показать контекстное меню.
     */
    @FXML
    private void showContextMenu(MouseEvent mouseEvent) {
        clearSelectionTable();
        contextMenuService.clear();
		if (mouseEvent.getButton() == MouseButton.SECONDARY) { 
            contextMenuService.add(ADD_WORD, null);
            contextMenuService.show(view, mouseEvent);
        }      
    }
    
    
}
