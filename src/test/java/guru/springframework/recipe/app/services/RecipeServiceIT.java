package guru.springframework.recipe.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.converters.fromcommand.RecipeCommandToRecipe;
import guru.springframework.recipe.app.converters.fromdomain.RecipeToRecipeCommand;
import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.repositories.reactive.RecipeReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Disabled
@SpringBootTest
public class RecipeServiceIT {

    private static final String NEW_DESCRIPTION = "NEW DESCRIPTION";
    
    @Autowired
    RecipeReactiveService recipeReactiveService;
    
    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;
    
    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;
    
    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;
    
    @Test
    public void saveOfDescription() {
    	
    	/* Given */
    	Flux<Recipe> fluxRecipe = recipeReactiveRepository.findAll();
    	Iterable<Recipe> recipes = fluxRecipe.toIterable();
    	Recipe testRecipe = recipes.iterator().next();
    	RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

    	/* When */
    	testRecipeCommand.setDescription(NEW_DESCRIPTION);
    	Mono<RecipeCommand> monoRecipeCommand = recipeReactiveService.saveRecipeCommand(testRecipeCommand);
    	RecipeCommand savedRecipeCommand = monoRecipeCommand.block();
    	
    	/* Then */
    	assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
    	assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
    	assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
    	assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
    
}
