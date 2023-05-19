package guru.springframework.recipe.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.recipe.app.commands.IngredientCommand;
import guru.springframework.recipe.app.converters.fromcommand.IngredientCommandToIngredient;
import guru.springframework.recipe.app.converters.fromcommand.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.recipe.app.converters.fromdomain.IngredientToIngredientCommand;
import guru.springframework.recipe.app.converters.fromdomain.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipe.app.domain.Ingredient;
import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.recipe.app.repositories.reactive.UnitOfMeasureReactiveRepository;
import reactor.core.publisher.Mono;

public class IngredientServiceImplTestJupiter {

	IngredientReactiveService ingredientService;
	
	@Mock
	RecipeReactiveRepository recipeReactiveRepository;
	
	@Mock
	UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
	
	IngredientToIngredientCommand ingredientToIngredientCommand;
	
	IngredientCommandToIngredient ingredientCommandToIngredient;
	
	@BeforeEach
	protected void setUp() throws Exception {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
		MockitoAnnotations.openMocks(this);
		ingredientService = new IngredientReactiveServiceImpl(recipeReactiveRepository, unitOfMeasureReactiveRepository, 
													ingredientToIngredientCommand, ingredientCommandToIngredient);
	}

	@Test
	void recupererParIdRecetteEtIdIngredient() {

		/* Given */
		String idRecette = "1";
		String idIngredient = "3";
		
		String id01 = "1";
		String id02 = "2";
		String id03 = "3";
		
        Ingredient ingredient01 = new Ingredient();
        ingredient01.setId(id01);

        Ingredient ingredient02 = new Ingredient();
        ingredient02.setId(id02);

        Ingredient ingredient03 = new Ingredient();
        ingredient03.setId(id03);
        
		Recipe recette = new Recipe();
		recette.setId(idRecette);
        recette.addIngredient(ingredient01);
        recette.addIngredient(ingredient02);
        recette.addIngredient(ingredient03);

		Mono<Recipe> monoRecipe = Mono.just(recette);
		when(recipeReactiveRepository.findById(anyString())).thenReturn(monoRecipe);
		
		/* When */
		Mono<IngredientCommand> monoIngredientCommand = ingredientService.recupererParIdRecetteEtIdIngredient(idRecette, idIngredient);
		
		/* Then */
		assertNotNull(monoIngredientCommand);
		assertEquals(idIngredient, monoIngredientCommand.block().getId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
	}
	
	/*
	 * correspondance nom methode JAVA GURU - John Thompson : testSaveRecipeCommand()
	 */
    @Test
    public void sauvegarderIngredient() throws Exception {
    	
		/* Given */
    	String idIngredient = "3";
    	String idRecette = "2";
    	
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(idIngredient);
        ingredientCommand.setRecipeId(idRecette);

        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(idRecette);
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(idIngredient);

        Mono<Recipe> monoSavedRecipe = Mono.just(savedRecipe);
        when(recipeReactiveRepository.save(any())).thenReturn(monoSavedRecipe);
    	
        Mono<Recipe> monoNewRecipe = Mono.just(new Recipe());
        when(recipeReactiveRepository.findById(anyString())).thenReturn(monoNewRecipe);

		/* When */
        Mono<IngredientCommand> monoIngredientCommand = ingredientService.sauvegarderIngredient(ingredientCommand);
        IngredientCommand ingredientCommandSauvegarde = monoIngredientCommand.block();
        
		/* Then */
        assertNotNull(ingredientCommandSauvegarde);
        assertEquals(idIngredient, ingredientCommandSauvegarde.getId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));
    }
    
	/*
	 * correspondance nom methode JAVA GURU - John Thompson : testDeleteById()
	 */
	@Test
    public void supprimerIngredientDansRecetteParId() throws Exception {
    	
		String idRecette = "1";
		String idIngredient = "3";
		
		Ingredient ingredient = new Ingredient();
		ingredient.setId(idIngredient);
		
		Recipe recette = new Recipe();
		recette.setId(idRecette);
		recette.addIngredient(ingredient);
		
		Mono<Recipe> monoRecipe = Mono.just(recette);
		when(recipeReactiveRepository.findById(anyString())).thenReturn(monoRecipe);
        when(recipeReactiveRepository.save(any())).thenReturn(monoRecipe);

		/* When */
		ingredientService.supprimerIngredientDansRecetteParId(idRecette, idIngredient);

		/* Then */
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));
    }
    
}
