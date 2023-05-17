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
		log.info("findById - id : " + id);
		return recipeReactiveRepository.findById(id);
	}

	@Override
	public Mono<RecipeCommand> findCommandById(String id) {
		log.info("findCommandById - id : " + id);
		return recipeReactiveRepository.findById(id)
										.map(recipe -> {
				RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
				recipeCommand.getIngredients().forEach(ingredient -> {
					ingredient.setRecipeId(recipeCommand.getId());
				});
				return recipeCommand;
			}
		);
	}
	
	@Override
	public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        return recipeReactiveRepository.save(recipeCommandToRecipe.convert(command)).map(recipeToRecipeCommand::convert);
	}
	
	@Override
	public Mono<Void> deleteById(String id) {
		log.info("deleteById - id : " + id);
		recipeReactiveRepository.deleteById(id).block();
        return Mono.empty();
	}

}
