package guru.springframework.recipe.app.services;

import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.recipe.app.commands.IngredientCommand;
import guru.springframework.recipe.app.converters.fromcommand.IngredientCommandToIngredient;
import guru.springframework.recipe.app.converters.fromdomain.IngredientToIngredientCommand;
import guru.springframework.recipe.app.domain.Ingredient;
import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.domain.UnitOfMeasure;
import guru.springframework.recipe.app.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.recipe.app.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class IngredientReactiveServiceImpl implements IngredientReactiveService {
	
	private final RecipeReactiveRepository recipeReactiveRepository;
	
	private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
	
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	
	// XXX correspondance nom methode JAVA GURU - John Thompson : findByRecipeIdAndIngredientId()
	@Override
	public Mono<IngredientCommand> recupererParIdRecetteEtIdIngredient(String idRecette, String idIngredient) {
		log.info("recupererParIdRecetteEtIdIngredient - idRecette : " + idRecette);
		log.info("recupererParIdRecetteEtIdIngredient - idIngredient : " + idIngredient);
		
		
		// TODO CODE PERSO
//		Mono<Recipe> monoRecipe = recipeReactiveRepository.findById(idRecette);
//		
//		Recipe recipe = monoRecipe.block();
//		Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients()
//																.stream()
//																.filter(ingredient -> ingredient.getId().equals(idIngredient))
//																.map(ingredient -> {
//																	IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
//																	command.setRecipeId(idRecette);
//																	return command;
//																})
//																.findFirst();
//
//		return Mono.just(optionalIngredientCommand.get());
		
		
		// TODO CODE JOHN THOMPSON
        return recipeReactiveRepository
                .findById(idRecette)
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(idIngredient))
                .single()
                .map(ingredient -> {
                    IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
                    command.setRecipeId(idRecette);
                    return command;
                });
	}
	
	// XXX correspondance nom methode JAVA GURU - John Thompson : saveIngredientCommand()
	@Transactional
	@Override
	public Mono<IngredientCommand> sauvegarderIngredient(IngredientCommand ingredientCommand) {

		String idRecette = ingredientCommand.getRecipeId();
		log.info("sauvegarderIngredient - idRecette : " + idRecette);
		
		Mono<Recipe> monoRecipe = recipeReactiveRepository.findById(idRecette);
		Recipe recetteTrouvee = monoRecipe.block();
		if (recetteTrouvee == null) {
			log.error("sauvegarderIngredient - creation d'un nouvel ingredient");
			return Mono.just(new IngredientCommand());
		}
		else {
			log.info("sauvegarderIngredient - recuperation d'un ingredient");
			Optional<Ingredient> optionalIngredient = recetteTrouvee.getIngredients()
																	.stream()
																	.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
																	.findFirst();
			
			if (optionalIngredient.isPresent()) {
				
				/* ON VERIFIE QUE L'UNITE DE MESURE EXISTE BIEN */
				Mono<UnitOfMeasure> monoUnitOfMeasure = unitOfMeasureReactiveRepository.findById(ingredientCommand.getUnitOfMeasure().getId());
				UnitOfMeasure uniteDeMesure = monoUnitOfMeasure.block();
				
				Ingredient ingredientTrouve = optionalIngredient.get();
				ingredientTrouve.setDescription(ingredientCommand.getDescription());
				ingredientTrouve.setAmount(ingredientCommand.getAmount());
				ingredientTrouve.setUnitOfMeasure(uniteDeMesure);
				
                if (ingredientTrouve.getUnitOfMeasure() == null){
                    new RuntimeException("Unite de mesure non trouvee");
                }
			}
			else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                recetteTrouvee.addIngredient(ingredient);
			}
			
			Mono<Recipe> monoRecipeSauvegardee = recipeReactiveRepository.save(recetteTrouvee);
			Recipe recetteSauvegardee = monoRecipeSauvegardee.block();

			Optional<Ingredient> optionalIngredientSauvegarde = recetteSauvegardee.getIngredients()
																	.stream()
																	.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
																	.findFirst();
			
            //check by description
            if (!optionalIngredientSauvegarde.isPresent()) {
                //not totally safe... But best guess
            	optionalIngredientSauvegarde = recetteSauvegardee.getIngredients().stream()
            		.filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
            		.filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
            		.filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
            		.findFirst();
            }
            
            IngredientCommand ingredientCommandSauvegardee = ingredientToIngredientCommand.convert(optionalIngredientSauvegarde.get());
            ingredientCommandSauvegardee.setRecipeId(recetteSauvegardee.getId());
            
			return Mono.just(ingredientCommandSauvegardee);
		}
	}

	// XXX correspondance nom methode JAVA GURU - John Thompson : deleteById()
	@Override
	public Mono<Void> supprimerIngredientDansRecetteParId(String idRecette, String idIngredient) {

		Mono<Recipe> monoRecipe = recipeReactiveRepository.findById(idRecette);
		
		Recipe recette = monoRecipe.block();
		if (recette == null) {
			log.error("La recette recherchee n'existe pas - idRecette : " + idRecette);
		}
		else {
			log.debug("La recette recherchee existe - idRecette : " + idRecette);

			Predicate<Ingredient> filtreIdIngredient = ingredient -> ingredient.getId().equals(idIngredient);
			Optional<Ingredient> optionalIngredient = recette.getIngredients().stream()
																			.filter(filtreIdIngredient)
																			.findFirst();
			
			if (optionalIngredient.isPresent()) {
				log.debug("L'ingredient recherche existe - idIngredient : " + idIngredient);
				
                Ingredient ingredientPourSuppression = optionalIngredient.get(); 
                recette.getIngredients().remove(ingredientPourSuppression);
                
                Mono<Recipe> monoRecetteSauvegardee = recipeReactiveRepository.save(recette);
                monoRecetteSauvegardee.block();
			}
			else {
				log.error("L'ingredient recherche n'existe pas - idIngredient : " + idIngredient);
			}
		}
		
		return Mono.empty();
	}

}
