
package ru.javafx.lingua.rest.client.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ErrorMessageHandler {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MessageSource messageSource;
    
    public List<MessageDTO> getErrorMessages(Object obj) {
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
        List<MessageDTO> messages = new ArrayList<>();
        
        constraintViolations.forEach(constraintViolation -> {           
            Locale currentLocale = LocaleContextHolder.getLocale();        
            String message = messageSource.getMessage(constraintViolation.getMessage(), null, currentLocale);
            String fieldname = constraintViolation.getPropertyPath().toString();
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR.toString(), message, fieldname);
            messages.add(messageDTO);
            //logger.info("MessageDTO: {}", messageDTO);
        });
        return messages;    
    }
    
    public void getErrorMessages(Object obj, List<MessageDTO> messages) {
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
        
        constraintViolations.forEach(constraintViolation -> {           
            Locale currentLocale = LocaleContextHolder.getLocale();        
            String message = messageSource.getMessage(constraintViolation.getMessage(), null, currentLocale);
            String fieldname = constraintViolation.getPropertyPath().toString();
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR.toString(), message, fieldname);
            messages.add(messageDTO);
            //logger.info("MessageDTO: {}", messageDTO);
        });   
    }
 
}
