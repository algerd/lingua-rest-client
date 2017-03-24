
package ru.javafx.lingua.rest.client.core.gui.paginator;

import java.net.URISyntaxException;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import ru.javafx.lingua.rest.client.core.datacore.CrudRepository;
import ru.javafx.lingua.rest.client.core.Entity;
import ru.javafx.lingua.rest.client.core.gui.BaseAwareController;
import ru.javafx.lingua.rest.client.core.gui.utils.Helper;
import ru.javafx.lingua.rest.client.fxintegrity.FXMLControllerLoader;

public abstract class PagedTableController<T extends Entity> extends BaseAwareController {
    
    protected Resource<T> selectedItem;
    protected Resource<T> resorce;
    protected PagedResources<Resource<T>> resources; 
    protected PaginatorPaneController paginatorPaneController;
    protected CrudRepository<T> pagedTableRepository;
    protected int pagedTableSize = 5;
    protected int pagedTableHeaderSize = 1;
    protected String sort;
    protected String order = "Asc";
    
    @Autowired
    protected FXMLControllerLoader fxmlLoader;
    
    @FXML
    protected TableView<Resource<T>> pagedTable;
    @FXML
    protected Pane tableContainer;
    
    protected abstract String createParamString();
    protected abstract void initPagedTable();

    protected Sort getSort() {
        return new Sort(new Sort.Order(
           order.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
           sort.toLowerCase()
        ));
    }
    
    public void initPagedTableController(CrudRepository<T> pagedTableRepository) {
        this.pagedTableRepository = pagedTableRepository;
        initPagedTable(); 
        pagedTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedItem = pagedTable.getSelectionModel().getSelectedItem());
        initPaginatorPane();
    }
    
    private void initPaginatorPane() {
        paginatorPaneController = (PaginatorPaneController) fxmlLoader.load(PaginatorPaneController.class);
        tableContainer.getChildren().add(paginatorPaneController.getView());
        paginatorPaneController.getPaginator().setSize(pagedTableSize);    
        paginatorPaneController.getPaginator().setSort(getSort());
        paginatorPaneController.initPaginator(this);
    }
    
    public void setPageValue() {      
        clearSelectionTable();
        pagedTable.getItems().clear();                      
        try {     
            resources = pagedTableRepository.getPagedResources(createParamString());  
            //logger.info("Paged table resources: {}", resources);
            paginatorPaneController.getPaginator().setTotalElements((int) resources.getMetadata().getTotalElements());           
            pagedTable.setItems(FXCollections.observableArrayList(resources.getContent().parallelStream().collect(Collectors.toList())));           
            Helper.setHeightTable(pagedTable, paginatorPaneController.getPaginator().getSize(), pagedTableHeaderSize);        
        } catch (URISyntaxException ex) {
            logger.error(ex.getMessage());
        }      
    } 
           
    public void clearSelectionTable() {
        pagedTable.getSelectionModel().clearSelection();
        selectedItem = null;
    }

    public CrudRepository<T> getPagedTableRepository() {
        return pagedTableRepository;
    }

    public void setPagedTableRepository(CrudRepository<T> pagedTableRepository) {
        this.pagedTableRepository = pagedTableRepository;
    }

    public int getPagedTableSize() {
        return pagedTableSize;
    }

    public void setPagedTableSize(int pagedTableSize) {
        this.pagedTableSize = pagedTableSize;
    }

    public int getPagedTableHeaderSize() {
        return pagedTableHeaderSize;
    }

    public void setPagedTableHeaderSize(int pagedTableHeaderSize) {
        this.pagedTableHeaderSize = pagedTableHeaderSize;
    }

}
