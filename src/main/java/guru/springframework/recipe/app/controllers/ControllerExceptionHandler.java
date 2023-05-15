package guru.springframework.recipe.app.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

	
	// TODO FIXME CODE A MODIFIER DANS LE FUTUR
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(NumberFormatException.class)
//    public ModelAndView handleNumberFormatException(Exception exception) {
//    	log.error("Handling NumberFormatException");
//        log.error(exception.getMessage());
//    	
//    	ModelAndView modelAndView = new ModelAndView();
//    	modelAndView.setViewName("400error");
//    	modelAndView.addObject("detailException", exception);
//    	
//    	return modelAndView;
//    }
    
    
	// TODO FIXME CODE A MODIFIER DANS LE FUTUR
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NotFoundException.class)
//    public ModelAndView handleNotFound(Exception exception) {
//    	log.error("Handling NotFoundException");
//        log.error(exception.getMessage());
//    	
//    	ModelAndView modelAndView = new ModelAndView();
//    	modelAndView.setViewName("404error");
//    	modelAndView.addObject("exception", exception);
//    	modelAndView.addObject("detailException", exception);
//    	
//    	return modelAndView;
//    }
    
}
