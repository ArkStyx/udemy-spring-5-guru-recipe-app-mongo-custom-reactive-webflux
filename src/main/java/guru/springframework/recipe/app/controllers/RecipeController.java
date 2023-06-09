package guru.springframework.recipe.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.services.RecipeReactiveService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Controller
public class RecipeController {

	private final RecipeReactiveService recipeReactiveService;
	
	private static final String NOM_REPERTOIRE_THYMELEAF = "recipe";
	private static final String SEPARATEUR_REPERTOIRE_ET_TEMPLATE_THYMELEAF = "/";
	
    private static final String RECIPE_RECIPEFORM_URL = NOM_REPERTOIRE_THYMELEAF + SEPARATEUR_REPERTOIRE_ET_TEMPLATE_THYMELEAF + "recipeform";
	
	private static final String NOM_ATTRIBUT_DANS_TEMPLATE_THYMELEAF = "recipe";

	private static final String NOM_ACTION_FORM_THYMELEAF_DANS_TEMPLATE = "recipe";
	
	private static final String REDIRECTION = "redirect:/";
	
    @GetMapping(value = "/recipe/{idRecupereDansUrl}/show")
	public String showById(Model model, @PathVariable("idRecupereDansUrl") String id) {
    	log.info("showById - id : " + id);
    	
    	Mono<Recipe> monoRecipe = recipeReactiveService.findById(id);
    	
		model.addAttribute(NOM_ATTRIBUT_DANS_TEMPLATE_THYMELEAF, monoRecipe);
		return NOM_REPERTOIRE_THYMELEAF + SEPARATEUR_REPERTOIRE_ET_TEMPLATE_THYMELEAF + "show";
	}
	
    @GetMapping(value = "/recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute(NOM_ATTRIBUT_DANS_TEMPLATE_THYMELEAF, new RecipeCommand());
		return RECIPE_RECIPEFORM_URL;
	}
	
    @GetMapping(value ="/recipe/{idRecupereDansUrl}/update")
	public String updateRecipe(Model model, @PathVariable("idRecupereDansUrl") String id) {
    	log.info("updateRecipe - id : " + id);
    	
    	Mono<RecipeCommand> monoRecipeCommand = recipeReactiveService.findCommandById(id);
		model.addAttribute(NOM_ATTRIBUT_DANS_TEMPLATE_THYMELEAF, monoRecipeCommand);
		
		return RECIPE_RECIPEFORM_URL;
	}

	@PostMapping(value = NOM_ACTION_FORM_THYMELEAF_DANS_TEMPLATE)
	public String saveOrUpdate(@Validated @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return RECIPE_RECIPEFORM_URL;
		}
		
		
		// TODO FIXME block()/blockFirst()/blockLast() are blocking, which is not supported in thread reactor-http-nio-2
		RecipeCommand recetteSauvegardee = recipeReactiveService.saveRecipeCommand(command).block();
		return REDIRECTION + "recipe/" + recetteSauvegardee.getId() + "/show";
		
		
		// FIXME KO
//		String idRecetteSauvegardee = recipeReactiveService.saveRecipeCommand(command).map(recipeCommand -> recipeCommand.getId()).block();
//		log.info("updateRecipe - idRecetteSauvegardee : " + idRecetteSauvegardee);
//		return REDIRECTION + "recipe/" + idRecetteSauvegardee + "/show";
		
		
		// FIXME KO
//		Mono<String> monoString = recipeReactiveService.saveRecipeCommand(command).map(recipeCommand -> recipeCommand.getId());
//		log.info("updateRecipe - monoString.toString() : " + monoString.toString());
//		String idRecetteSauvegardee =  monoString.block().toString();
//		log.info("updateRecipe - idRecetteSauvegardee : " + idRecetteSauvegardee);
//		return REDIRECTION + "recipe/" + idRecetteSauvegardee + "/show";
		
		
		// TODO FIXME KO
//		Mono<String> monoString = recipeReactiveService.saveRecipeCommand(command).flatMap(recipeCommand -> {
//			return Mono.just(recipeCommand.getId());
//		});
//		Disposable disposable = monoString.subscribe();
//		disposable.dispose();
//		String idRecupere = monoString.block();
//		log.info("updateRecipe - idRecupere : " + idRecupere);
//		return REDIRECTION + "recipe/" + idRecupere + "/show";
		
		
		
		// TODO CODE OK MAIS COMMENT L'UTILISER ????
//		recipeReactiveService.saveRecipeCommand(command).subscribe(
//            data -> System.out.println("data : " + data), // onNext
//            error -> System.out.println("ERROR: " + error),  // onError
//            () -> System.out.println("Completed!") // onComplete
//	    );
		
		// TODO FIXME KO
//		String test = "";
//		recipeReactiveService.saveRecipeCommand(command).subscribe(value -> {
//			  log.info("Consumed: " + value.getId());
//			  test = value.getId();
//		});
//		return "";
	}

    @GetMapping("recipe/{idPourSuppression}/delete")
	public String deleteById(@PathVariable("idPourSuppression") String id) {
		log.info("deleteById - id : " + id);
		recipeReactiveService.deleteById(id);
		return REDIRECTION;
	}

}
