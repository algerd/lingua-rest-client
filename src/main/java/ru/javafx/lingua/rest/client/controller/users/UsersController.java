
package ru.javafx.lingua.rest.client.controller.users;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import ru.javafx.lingua.rest.client.core.gui.paginator.PagedTableController;
import ru.javafx.lingua.rest.client.entity.User;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLController;
import ru.javafx.lingua.rest.client.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import ru.javafx.lingua.rest.client.core.datacore.operators.StringOperator;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import ru.javafx.lingua.rest.client.controller.user.UserPaneController;

@FXMLController(
    value = "/fxml/users/Users.fxml",    
    title = "Users")
public class UsersController extends PagedTableController<User> {
    
    private String searchString = "";
    
    @Autowired
    private UserRepository userRepository;
    
    @FXML
    private TableColumn<Resource<User>, String> usernameColumn;
    @FXML
    private TableColumn<Resource<User>, String> roleColumn;
    @FXML
    private TableColumn<Resource<User>, String> mailColumn;
    @FXML
    private TableColumn<Resource<User>, String> ipColumn;   
    @FXML
    private TableColumn<Resource<User>, String> createdColumn; 
    @FXML
    private TableColumn<Resource<User>, String> lastVisitedColumn;
    

    public UsersController() {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sort = "username";
        order = "Asc";
        super.initPagedTableController(userRepository);               
        initRepositoryListeners();   
    }
    
    @Override
    protected void initPagedTable() {     
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getContent().usernameProperty());  
        createdColumn.setCellValueFactory(cellData -> cellData.getValue().getContent().createdProperty().asString());
        mailColumn.setCellValueFactory(cellData -> cellData.getValue().getContent().mailProperty());  
        ipColumn.setCellValueFactory(cellData -> cellData.getValue().getContent().ipProperty()); 
        lastVisitedColumn.setCellValueFactory(cellData -> cellData.getValue().getContent().lastVisitedProperty().asString());
    }
    
    @Override
    protected String createParamString() {
        List<String> params = new ArrayList<>();       
        if (!searchString.equals("")) {
            params.add("username=" + StringOperator.STARTS_WITH);
            params.add("username=" + searchString);
        }
        params.addAll(paginatorPaneController.getPaginator().getParameterList());
        String paramStr = params.isEmpty()? "" : String.join("&", params);
        //logger.info("paramStr :{}", paramStr);
        return paramStr;
    }
    
    private void initRepositoryListeners() {
        userRepository.clearChangeListeners(this);       

        userRepository.addDeleteListener((observable, oldVal, newVal) -> filter(), this); 
        userRepository.addInsertListener((observable, oldVal, newVal) -> filter(), this);
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
                requestViewService.showTab(UserPaneController.class, selectedItem);
            }           
        }         
    }

}
