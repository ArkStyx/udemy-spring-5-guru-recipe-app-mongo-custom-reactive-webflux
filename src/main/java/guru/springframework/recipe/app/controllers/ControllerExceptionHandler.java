package guru.springframework.recipe.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.thymeleaf.exceptions.TemplateInputException;

import guru.springframework.recipe.app.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NumberFormatException.class, WebExchangeBindException.class})
    public String handleNumberFormatException(Exception exception, Model model) {
    	log.error("Handling NumberFormatException");
        log.error(exception.getMessage());
    	
    	model.addAttribute("detailException", exception);
    	
    	return "400error";
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, TemplateInputException.class})
    public String handleNotFound(Exception exception, Model model) {
    	log.error("Handling NotFoundException");
        log.error(exception.getMessage());

    	model.addAttribute("exception", exception);
    	model.addAttribute("detailException", exception);
    	
    	return "404error";
    }
    
}
