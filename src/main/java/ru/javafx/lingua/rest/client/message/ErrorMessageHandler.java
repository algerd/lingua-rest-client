
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
    
    public List<ErrorMessage> getErrorMessages(Object obj) {
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
        List<ErrorMessage> messages = new ArrayList<>();
        
        constraintViolations.forEach(constraintViolation -> {                 
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setProperty(constraintViolation.getPropertyPath().toString());
            errorMessage.setMessage(messageSource.getMessage(constraintViolation.getMessage(), null, LocaleContextHolder.getLocale()));
            //errorMessage.setInvalidValue(constraintViolation.getInvalidValue());
            messages.add(errorMessage);
            //logger.info("MessageDTO: {}", messageDTO);
        });
        return messages;    
    } 
    
    public void getErrorMessages(Object obj, List<ErrorMessage> messages) {
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
        
        constraintViolations.forEach(constraintViolation -> {                 
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setProperty(constraintViolation.getPropertyPath().toString());
            errorMessage.setMessage(messageSource.getMessage(constraintViolation.getMessage(), null, LocaleContextHolder.getLocale()));
            //errorMessage.setInvalidValue(constraintViolation.getInvalidValue());
            messages.add(errorMessage);
            //logger.info("MessageDTO: {}", messageDTO);
        });   
    } 
   
}
