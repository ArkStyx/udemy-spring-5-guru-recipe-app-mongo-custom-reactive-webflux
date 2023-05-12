package guru.springframework.recipe.app.services;

import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeReactiveService {

	Flux<Recipe> getRecipes();
	
	Mono<Recipe> findById(String id);
	
	Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);
	
	Mono<RecipeCommand> findCommandById(String id);
	
	Mono<Void> deleteById(String id);
}
