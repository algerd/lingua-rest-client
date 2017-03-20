
package ru.javafx.lingua.rest.client.core.gui.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.javafx.lingua.rest.client.core.datacore.SessionManager;

@Service
public class ImageHelper {
    
    private final Logger logger = LogManager.getLogger(ImageUtil.class);
    
    @Autowired
    private SessionManager sessionManager;

    public HttpStatus postImage(String ref, Image image, String imageFormat) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {             
            BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);            
            String encodedImage;
            ImageIO.write(bImage, imageFormat, baos);
            baos.flush();
            encodedImage = java.net.URLEncoder.encode((new Base64(false)).encodeToString(baos.toByteArray()), "ISO-8859-1");  
                      
            URI uri = new URI(ref);  
            HttpHeaders headers = sessionManager.createSessionHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);     
            HttpEntity<String> request = new HttpEntity<>(encodedImage, headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(uri, HttpMethod.POST, request, String.class).getStatusCode();
        }  
        catch (IOException | URISyntaxException ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

}
