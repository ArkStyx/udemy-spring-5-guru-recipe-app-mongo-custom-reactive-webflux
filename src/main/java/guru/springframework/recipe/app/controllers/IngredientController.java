package guru.springframework.recipe.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import guru.springframework.recipe.app.commands.IngredientCommand;
import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.app.services.IngredientReactiveService;
import guru.springframework.recipe.app.services.RecipeReactiveService;
import guru.springframework.recipe.app.services.UnitOfMeasureReactiveService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Controller
public class IngredientController {

	private final RecipeReactiveService recipeReactiveService;
	private final IngredientReactiveService ingredientReactiveService;
	private final UnitOfMeasureReactiveService unitOfMeasureReactiveService;
	
	/*
	 * correspondance nom methode JAVA GURU - John Thompson : listIngredients()
	 */
	@GetMapping(value = "/recipe/{idRecetteDansUrl}/ingredients")
	public String recupererListeIngredients(Model model, @PathVariable("idRecetteDansUrl") String id) {
		log.debug("recupererListeIngredients - id : " + id);
		
		Mono<RecipeCommand> monoRecipeCommand = recipeReactiveService.findCommandById(id);

		model.addAttribute("recipe", monoRecipeCommand);
		return "recipe/ingredient/list";
	}
	
	/* 
	 * correspondance nom methode JAVA GURU - John Thompson : showRecipeIngredient()
	 */
	@GetMapping(value = "/recipe/{idRecette}/ingredient/{idIngredient}/show")
	public String afficherIngredientDansRecette(Model model, @PathVariable String idRecette, @PathVariable String idIngredient) {
		log.debug("afficherIngredientDansRecette - idRecette : " + idRecette);
		log.debug("afficherIngredientDansRecette - idIngredient : " + idIngredient);
		
		Mono<IngredientCommand>  monoIngredientCommand = ingredientReactiveService.recupererParIdRecetteEtIdIngredient(idRecette, idIngredient);

		model.addAttribute("ingredient", monoIngredientCommand);
		return "recipe/ingredient/show";
	}
	
	/* 
	 * correspondance nom methode JAVA GURU - John Thompson : newRecipe()
	 */
    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String creerNouvelIngredient(Model model, @PathVariable("recipeId") String idRecette) {
    	log.debug("creerNouvelIngredient - idRecette : " + idRecette);
    	
    	IngredientCommand ingredientCommand = new IngredientCommand();
    	ingredientCommand.setRecipeId(idRecette);
    	ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
    	
    	Mono<RecipeCommand> monoRecipeCommand = recipeReactiveService.findCommandById(idRecette);
    	RecipeCommand recetteTrouvee = monoRecipeCommand.block();
    	recetteTrouvee.getIngredients().add(ingredientCommand);
    	
        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/ingredientform";
    }
	
	/* 
	 * correspondance nom methode JAVA GURU - John Thompson : updateRecipeIngredient()
	 */
    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String modifierIngredientDansRecette(Model model, @PathVariable("recipeId") String idRecette, @PathVariable("id") String idIngredient) {
		log.debug("modifierIngredientDansRecette - idRecette : " + idRecette);
		log.debug("modifierIngredientDansRecette - idIngredient : " + idIngredient);
    	
		Mono<IngredientCommand>  monoIngredientCommand = ingredientReactiveService.recupererParIdRecetteEtIdIngredient(idRecette, idIngredient);
    	model.addAttribute("ingredient", monoIngredientCommand);
		return "recipe/ingredient/ingredientform";
	}

    
    // TODO FIXME [THYMELEAF][nioEventLoopGroup-6-4] Exception processing template "recipe/ingredient/ingredientform": An error happened during template parsing (template: "class path resource [templates/recipe/ingredient/ingredientform.html]")
	/* 
	 * correspondance nom methode JAVA GURU - John Thompson : saveOrUpdate()
	 */
	@PostMapping("recipe/{recipeId}/ingredient")
	public String sauvegarderOuModifierIngredientDansRecette(@Validated @ModelAttribute IngredientCommand ingredientCommand, 
															BindingResult bindingResult, @PathVariable String recipeId, Model model) {
		log.info("sauvegarderOuModifierIngredientDansRecette - ingredientCommand.getRecipeId() : " + ingredientCommand.getRecipeId());
		
		if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return "recipe/ingredient/ingredientform";
		}
		
		Mono<IngredientCommand> monoIngredientSauvegarde = ingredientReactiveService.sauvegarderIngredient(ingredientCommand);
		IngredientCommand ingredientSauvegarde = monoIngredientSauvegarde.block();
		
		String idRecette = ingredientSauvegarde.getRecipeId();
		log.debug("sauvegarderOuModifierIngredientDansRecette - idRecette : " + idRecette);

		String idIngredient = ingredientSauvegarde.getId();
		log.debug("sauvegarderOuModifierIngredientDansRecette - idIngredient : " + idIngredient);
		
		return "redirect:/recipe/" + idRecette + "/ingredient/" + idIngredient + "/show";
	}

	/*
	 * correspondance nom methode JAVA GURU - John Thompson : deleteIngredient()
	 */
    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String supprimerIngredient(@PathVariable("recipeId") String idRecette, @PathVariable("id") String idIngredient) {
		log.debug("supprimerIngredient - idRecette : " + idRecette);
		log.debug("supprimerIngredient - idIngredient : " + idIngredient);
    	
    	ingredientReactiveService.supprimerIngredientDansRecetteParId(idRecette, idIngredient).block();
    	return "redirect:/recipe/" + idRecette + "/ingredients";
    }
    
    @ModelAttribute("listeUnitesDeMesure")
    Flux<UnitOfMeasureCommand> populateUomList() {
		return unitOfMeasureReactiveService.recupererToutesLesUnitesDeMesure();
    }
  
}
