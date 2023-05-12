package guru.springframework.recipe.app.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.recipe.app.commands.IngredientCommand;
import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.app.services.IngredientReactiveService;
import guru.springframework.recipe.app.services.RecipeReactiveService;
import guru.springframework.recipe.app.services.UnitOfMeasureReactiveService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class IngredientControllerTest {

	@Mock
	IngredientReactiveService ingredientService;
	
	@Mock
	UnitOfMeasureReactiveService unitOfMeasureService;
	
	@Mock
	RecipeReactiveService recipeReactiveService;
	
	@InjectMocks
	IngredientController ingredientController;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
	}
	
	// TODO correspondance nom methode JAVA GURU - John Thompson : testListIngredients()
	@Test
	void recupererListeIngredients() throws Exception {

		/* Given */
		String idRecette = "1";
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(idRecette);
		
		when(recipeReactiveService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));
		
		/* When */
		
		/* Then */
		mockMvc.perform(
					MockMvcRequestBuilders.get("/recipe/1/ingredients")
				).
				andExpect(status().isOk()).
				andExpect(view().name("recipe/ingredient/list")).
				andExpect(model().attributeExists("recipe"));
		
		verify(recipeReactiveService, times(1)).findCommandById(anyString());

	}
	
	// TODO correspondance nom methode JAVA GURU - John Thompson : testShowIngredient()
	@Test
	void afficherIngredientDansRecette() throws Exception {
		/* Given */
		String idIngredient = "1";
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(idIngredient);
		
		Mono<IngredientCommand> monoIngredientCommand = Mono.just(ingredientCommand);
		when(ingredientService.recupererParIdRecetteEtIdIngredient(anyString(), anyString())).thenReturn(monoIngredientCommand);
		
		/* When */
		
		/* Then */
		mockMvc.perform(
					MockMvcRequestBuilders.get("/recipe/1/ingredient/2/show")
				).
				andExpect(status().isOk()).
				andExpect(view().name("recipe/ingredient/show")).
				andExpect(model().attributeExists("ingredient"));
	}
	
	// TODO correspondance nom methode JAVA GURU - John Thompson : testUpdateIngredientForm()
	@Test
	void modifierIngredientDansRecette() throws Exception {
		
		/* Given */
		String idIngredient = "1";
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(idIngredient);
		
		Mono<IngredientCommand> monoIngredientCommand = Mono.just(ingredientCommand);
        when(ingredientService.recupererParIdRecetteEtIdIngredient(anyString(), anyString())).thenReturn(monoIngredientCommand);
        when(unitOfMeasureService.recupererToutesLesUnitesDeMesure()).thenReturn(Flux.just(new UnitOfMeasureCommand()));
        
		/* When */

		/* Then */
        mockMvc.perform(
        		MockMvcRequestBuilders.get("/recipe/1/ingredient/2/update")
    		).
    		andExpect(status().isOk()).
    		andExpect(view().name("recipe/ingredient/ingredientform")).
    		andExpect(model().attributeExists("ingredient")).
    		andExpect(model().attributeExists("listeUnitesDeMesure"));
	}
	
	// TODO correspondance nom methode JAVA GURU - John Thompson : testSaveOrUpdate()
	@Test
	void sauvegarderOuModifierIngredientDansRecette() throws Exception {

		String id = "3";
		String idRecette = "2";
		
		/* Given */
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(id);
		ingredientCommand.setRecipeId(idRecette);
		
		Mono<IngredientCommand> monoIngredientCommand = Mono.just(ingredientCommand);
		when(ingredientService.sauvegarderIngredient(any())).thenReturn(monoIngredientCommand);
		
		/* When */
		
		/* Then */
		mockMvc.perform(
					MockMvcRequestBuilders.post("/recipe/1/ingredient").
					contentType(MediaType.APPLICATION_FORM_URLENCODED).
	                param("id", "").
	                param("description", "some string")
				).
				andExpect(status().is3xxRedirection()).
				andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
	}
	
	// TODO correspondance nom methode JAVA GURU - John Thompson : testNewIngredientForm()
	@Test
	void creerNouvelIngredient() throws Exception {
		
		/* Given */
		String idRecette = "1";
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(idRecette);
        
        when(recipeReactiveService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));
        when(unitOfMeasureService.recupererToutesLesUnitesDeMesure()).thenReturn(Flux.just(new UnitOfMeasureCommand()));
        
		/* When */
		
		
		/* Then */
		mockMvc.perform(
					MockMvcRequestBuilders.get("/recipe/1/ingredient/new")
				).
				andExpect(status().isOk()).
				andExpect(view().name("recipe/ingredient/ingredientform")).
				andExpect(model().attributeExists("ingredient")).
				andExpect(model().attributeExists("listeUnitesDeMesure"));
	}

	// TODO correspondance nom methode JAVA GURU - John Thompson : testDeleteIngredient()
	@Test
	void supprimerIngredient() throws Exception {
		/* Given */
		
		
		/* When */
		when(ingredientService.supprimerIngredientDansRecetteParId(anyString(), anyString())).thenReturn(Mono.empty());
		
		/* Then */
		mockMvc.perform(
					MockMvcRequestBuilders.get("/recipe/2/ingredient/3/delete")
				).
				andExpect(status().is3xxRedirection()).
				andExpect(view().name("redirect:/recipe/2/ingredients"));
		
		verify(ingredientService, times(1)).supprimerIngredientDansRecetteParId(anyString(), anyString());
	}
	
}
