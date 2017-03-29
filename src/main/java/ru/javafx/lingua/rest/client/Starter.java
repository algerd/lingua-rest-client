package ru.javafx.lingua.rest.client;

import javafx.scene.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.javafx.lingua.rest.client.authorization.AuthorizationChecker;
import ru.javafx.lingua.rest.client.authorization.AuthorizationController;
import ru.javafx.lingua.rest.client.controller.MainControllerImpl;
import ru.javafx.lingua.rest.client.controller.words.WordsController;
import ru.javafx.lingua.rest.client.core.gui.service.RequestViewService;
import ru.javafx.lingua.rest.client.fxintegrity.BaseSpringBootJavaFxApplication;

@SpringBootApplication
public class Starter extends BaseSpringBootJavaFxApplication {
	
	public static void main(String[] args) {
		launchApp(Starter.class, MainControllerImpl.class, args);
	}
    
    @Autowired
    private RequestViewService requestViewService;
    @Autowired
    private AuthorizationChecker authorizationChecker;

    @Override
    public void show() {
        if (!authorizationChecker.check()) {
            requestViewService.showPane(AuthorizationController.class);
        } else {
            requestViewService.showTab(WordsController.class);
        }       
        primaryStage.getIcons().add(new Image("images/icon_root_layout.png"));     
    }
	
}
