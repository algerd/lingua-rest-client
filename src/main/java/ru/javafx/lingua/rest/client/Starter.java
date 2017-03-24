package ru.javafx.lingua.rest.client;

import ru.javafx.lingua.rest.client.authorization.UpdateAuthorizationProperties;
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
        //UpdateAuthorizationProperties.update();
        /*
        Вызвать контроллер, релизующий функции
            1. проверки наличия AuthorizationProperties username и password
            2. проверка правильности авторизации на сервере
            3. если нет AuthorizationProperties или не проходит авторизация - предложить пройти регистрацию
            4. после правильного заполнения полей регистрации обновить AuthorizationProperties.username и password,
                вызвать AuthorizationProperties.updatePropertiesFile()
            5. повторить п.2 (?)
        */
        
        //requestViewService.showTab(WordsController.class);
        if (!authorizationChecker.check()) {
            requestViewService.showPane(AuthorizationController.class);
        } else {
            requestViewService.showTab(WordsController.class);
        }
        
        /*
        Разделить регистрацию и авторизацию.
        При первом входе или неправильном входе - попросить авторизироваться. И там же ссылка на регистрацию.
        Регистрация - это создание аккаунта с вводом дополнительных полей. После успешной регистрации - автоматический вход.
        */
        
        primaryStage.getIcons().add(new Image("images/icon_root_layout.png"));     
    }
	
}
