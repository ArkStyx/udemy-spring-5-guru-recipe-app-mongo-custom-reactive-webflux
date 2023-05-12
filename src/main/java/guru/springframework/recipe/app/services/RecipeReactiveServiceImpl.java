package guru.springframework.recipe.app.services;

import org.springframework.stereotype.Service;

import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.converters.fromcommand.RecipeCommandToRecipe;
import guru.springframework.recipe.app.converters.fromdomain.RecipeToRecipeCommand;
import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.repositories.reactive.RecipeReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class RecipeReactiveServiceImpl implements RecipeReactiveService {

	private final RecipeReactiveRepository recipeReactiveRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;
	
	@Override
	public Flux<Recipe> getRecipes() {
		return recipeReactiveRepository.findAll();
	}

	@Override
	public Mono<Recipe> findById(String id) {
		return recipeReactiveRepository.findById(id);
	}

	@Override
	public Mono<RecipeCommand> findCommandById(String id) {
		return recipeReactiveRepository.findById(id).map(recipe -> {
			RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
			recipeCommand.getIngredients().forEach(ingredient -> {
				ingredient.setRecipeId(recipeCommand.getId());
			});
			return recipeCommand;
		});
	}
	
	@Override
	public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		
		Recipe savedRecipe = recipeReactiveRepository.save(detachedRecipe).block();
		log.debug("savedRecipe.getId : " + savedRecipe.getId());
		
		return Mono.just(recipeToRecipeCommand.convert(savedRecipe));
	}
	
	@Override
	public Mono<Void> deleteById(String id) {
		recipeReactiveRepository.deleteById(id).block();
        return Mono.empty();
	}

}
