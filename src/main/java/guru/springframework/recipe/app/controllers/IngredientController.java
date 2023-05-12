package guru.springframework.recipe.app.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	private final UnitOfMeasureReactiveService unitOfMeasureService;
	
	// XXX correspondance nom methode JAVA GURU - John Thompson : listIngredients()
	@GetMapping(value = "/recipe/{idRecetteDansUrl}/ingredients")
	public String recupererListeIngredients(Model model, @PathVariable("idRecetteDansUrl") String id) {
		log.debug("recupererListeIngredients - id : " + id);
		
		Mono<RecipeCommand> monoRecipeCommand = recipeReactiveService.findCommandById(id);
		RecipeCommand recipeCommand = monoRecipeCommand.block();
		
		model.addAttribute("recipe", recipeCommand);
		return "recipe/ingredient/list";
	}
	
	// XXX correspondance nom methode JAVA GURU - John Thompson : showRecipeIngredient()
	@GetMapping(value = "/recipe/{idRecette}/ingredient/{idIngredient}/show")
	public String afficherIngredientDansRecette(Model model, @PathVariable String idRecette, @PathVariable String idIngredient) {
		log.debug("afficherIngredientDansRecette - idRecette : " + idRecette);
		log.debug("afficherIngredientDansRecette - idIngredient : " + idIngredient);
		
		Mono<IngredientCommand>  monoIngredientCommand = ingredientReactiveService.recupererParIdRecetteEtIdIngredient(idRecette, idIngredient);
		IngredientCommand ingredientCommand = monoIngredientCommand.block();
		
		model.addAttribute("ingredient", ingredientCommand);
		return "recipe/ingredient/show";
	}
	
	// XXX correspondance nom methode JAVA GURU - John Thompson : updateRecipeIngredient()
    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String modifierIngredientDansRecette(Model model, @PathVariable("recipeId") String idRecette, @PathVariable("id") String idIngredient) {
		log.debug("modifierIngredientDansRecette - idRecette : " + idRecette);
		log.debug("modifierIngredientDansRecette - idIngredient : " + idIngredient);
    	
		Mono<IngredientCommand>  monoIngredientCommand = ingredientReactiveService.recupererParIdRecetteEtIdIngredient(idRecette, idIngredient);
		IngredientCommand ingredient = monoIngredientCommand.block();

		Flux<UnitOfMeasureCommand> fluxUnitOfMeasureCommand = unitOfMeasureService.recupererToutesLesUnitesDeMesure();
		List<UnitOfMeasureCommand> listeUnitesDeMesure = fluxUnitOfMeasureCommand.collectList().block();
		
    	model.addAttribute("ingredient", ingredient);
    	model.addAttribute("listeUnitesDeMesure", listeUnitesDeMesure);
		return "recipe/ingredient/ingredientform";
	}

	// XXX correspondance nom methode JAVA GURU - John Thompson : saveOrUpdate()
	@PostMapping("recipe/{recipeId}/ingredient")
	public String sauvegarderOuModifierIngredientDansRecette(@ModelAttribute IngredientCommand ingredientCommand) {
		
		
		// TODO FIXME KO !!!!!
		// TODO FIXME KO !!!!!
		// TODO FIXME KO !!!!!
		// TODO FIXME KO !!!!!
		// TODO FIXME KO !!!!!
		log.info("sauvegarderOuModifierIngredientDansRecette - ingredientCommand.getRecipeId() : " + ingredientCommand.getRecipeId());
		
		Mono<IngredientCommand> monoIngredientSauvegarde = ingredientReactiveService.sauvegarderIngredient(ingredientCommand);
		IngredientCommand ingredientSauvegarde = monoIngredientSauvegarde.block();
		
		String idRecette = ingredientSauvegarde.getRecipeId();
		log.debug("sauvegarderOuModifierIngredientDansRecette - idRecette : " + idRecette);

		String idIngredient = ingredientSauvegarde.getId();
		log.debug("sauvegarderOuModifierIngredientDansRecette - idIngredient : " + idIngredient);
		
		return "redirect:/recipe/" + idRecette + "/ingredient/" + idIngredient + "/show";
	}
	
	// XXX correspondance nom methode JAVA GURU - John Thompson : newIngredient() / newRecipe()
    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String creerNouvelIngredient(Model model, @PathVariable("recipeId") String idRecette) {
    	log.debug("creerNouvelIngredient - idRecette : " + idRecette);
    	
    	IngredientCommand ingredientCommand = new IngredientCommand();
    	ingredientCommand.setRecipeId(idRecette);
    	ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
    	
    	Mono<RecipeCommand> monoRecipeCommand = recipeReactiveService.findCommandById(idRecette);
    	RecipeCommand recetteTrouvee = monoRecipeCommand.block();
    	recetteTrouvee.getIngredients().add(ingredientCommand);
    	
    	List<UnitOfMeasureCommand> linkedHashSetUnitOfMeasureCommand = unitOfMeasureService.recupererToutesLesUnitesDeMesure().collectList().block();

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("listeUnitesDeMesure", linkedHashSetUnitOfMeasureCommand);
        
        return "recipe/ingredient/ingredientform";
    }

	// XXX correspondance nom methode JAVA GURU - John Thompson : deleteIngredient()
    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String supprimerIngredient(@PathVariable("recipeId") String idRecette, @PathVariable("id") String idIngredient) {
		log.debug("supprimerIngredient - idRecette : " + idRecette);
		log.debug("supprimerIngredient - idIngredient : " + idIngredient);
    	
    	ingredientReactiveService.supprimerIngredientDansRecetteParId(idRecette, idIngredient).block();
    	return "redirect:/recipe/" + idRecette + "/ingredients";
    }
  
}
