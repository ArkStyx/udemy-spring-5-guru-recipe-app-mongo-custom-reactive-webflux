package guru.springframework.recipe.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.converters.fromcommand.RecipeCommandToRecipe;
import guru.springframework.recipe.app.converters.fromdomain.RecipeToRecipeCommand;
import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.repositories.reactive.RecipeReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class RecipeServiceImplTestJupiter {

	private static final String ID = "1";
	
	RecipeReactiveServiceImpl recipeReactiveServiceImpl;
	
	@Mock
	RecipeReactiveRepository recipeReactiveRepository;
	
	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;
	
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		recipeReactiveServiceImpl = new RecipeReactiveServiceImpl(recipeReactiveRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}
	
	
	// XXX correspondance nom methode JAVA GURU - John Thompson : getRecipeByIdTest()
	@Test
	void getRecipes() {
		Recipe recetteGuacamole = new Recipe();
		recetteGuacamole.setDescription("Guacamole maison");
		
		Recipe recetteTacos = new Recipe();
		recetteTacos.setDescription("Tacos maison");

		when(recipeReactiveRepository.findAll()).thenReturn(Flux.just(recetteGuacamole, recetteTacos));
		
		Flux<Recipe> fluxRecipe = recipeReactiveServiceImpl.getRecipes();
		Mono<List<Recipe>> monoListRecipe = fluxRecipe.collectList();
		List<Recipe> listeDeRecettes = monoListRecipe.block();
		assertEquals(2, listeDeRecettes.size());
		verify(recipeReactiveRepository, Mockito.times(1)).findAll();
	}

	// XXX correspondance nom methode JAVA GURU - John Thompson : getRecipeCommandByIdTest()
	@Test
	void getRecipeById() {
		Recipe recette = new Recipe();
		recette.setId(ID);
		
		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recette));
		
		RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID);

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        Mono<RecipeCommand> monoRecipeCommand = recipeReactiveServiceImpl.findCommandById(ID);
        RecipeCommand commandById = monoRecipeCommand.block();
		
        assertNotNull(commandById);
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, never()).findAll();
	}
	
    @Test
    public void getRecipesTest() throws Exception {

        when(recipeReactiveRepository.findAll()).thenReturn(Flux.just(new Recipe()));

        Flux<Recipe> fluxRecipes = recipeReactiveServiceImpl.getRecipes();
        Mono<List<Recipe>> monoListRecipe = fluxRecipes.collectList();
        List<Recipe> listeDeRecettes = monoListRecipe.block();

        assertEquals(listeDeRecettes.size(), 1);
        verify(recipeReactiveRepository, times(1)).findAll();
        verify(recipeReactiveRepository, never()).findById(anyString());
    }

    @Test
    public void deleteById() throws Exception {

    	/* Given */
        String idToDelete = "2";

        when(recipeReactiveRepository.deleteById(anyString())).thenReturn(Mono.empty());

        /* When */
        recipeReactiveServiceImpl.deleteById(idToDelete);

        /* Then */
        verify(recipeReactiveRepository, times(1)).deleteById(anyString());
    }

    // TODO FIXME COMMENT ????
//    @Test
//    public void getRecipeByIdTestNotFound() throws Exception {
//
//        Optional<Recipe> recipeOptional = Optional.empty();
//
//        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
//
//        assertThrows(NotFoundException.class, () -> {
//        	recipeServiceImpl.findById(ID);
//        });
//    }
    
}
