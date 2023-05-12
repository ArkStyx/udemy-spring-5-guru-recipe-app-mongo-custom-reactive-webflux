package guru.springframework.recipe.app.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.exceptions.NotFoundException;
import guru.springframework.recipe.app.services.RecipeReactiveService;
import reactor.core.publisher.Mono;

class RecipeControllerTest {

	@Mock
	RecipeReactiveService recipeReactiveService;
	
	@InjectMocks
	RecipeController recipeController;

	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).
				setControllerAdvice(new ControllerExceptionHandler()).
				build();
	}

	@Test
	void getRecipe() throws Exception {
		
		String idRecette = "1";
		
		Recipe recette = new Recipe();
		recette.setId(idRecette);
		
		when(recipeReactiveService.findById(anyString())).thenReturn(Mono.just(recette));

		String rootContext = "/recipe/" + idRecette + "/show/";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(rootContext);
		ResultMatcher resultMatcherStatusOk = status().isOk();
		ResultMatcher resultMatcherViewNameIndex = view().name("recipe/show");
		ResultMatcher resultMatcherModelAttributeExists = model().attributeExists("recipe");
		
		mockMvc.perform(requestBuilder)
				.andExpect(resultMatcherStatusOk)
				.andExpect(resultMatcherViewNameIndex)
				.andExpect(resultMatcherModelAttributeExists);
	}
	
	@Test
	void getNewRecipeForm() throws Exception {
		
		/* Given */

		/* When */

		/* Then */
		mockMvc.perform(
					MockMvcRequestBuilders.get("/recipe/new")
				).
				andExpect(status().isOk()).
				andExpect(view().name("recipe/recipeform")).
				andExpect(model().attributeExists("recipe"));
		
	}

	@Test
	void postNewRecipeForm() throws Exception {
		
		/* Given */
		String idRecette = "2";
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(idRecette);

		/* When */
		when(recipeReactiveService.saveRecipeCommand(any())).thenReturn(Mono.just(recipeCommand));
		
		/* Then */
		mockMvc.perform(
				MockMvcRequestBuilders.post("/recipe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some string")
                .param("directions", "some directions")
			).
			andExpect(status().is3xxRedirection()).
			andExpect(view().name("redirect:/recipe/2/show"));
	}
	
    @Test
    public void postNewRecipeFormValidationFail() throws Exception {
		String idRecette = "2";
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(idRecette);

        when(recipeReactiveService.saveRecipeCommand(any())).thenReturn(Mono.just(recipeCommand));

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")

        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));
    }

	@Test
	void updateView() throws Exception {
		
		/* Given */
		String idRecette = "2";
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(idRecette);
		
		/* When */
		when(recipeReactiveService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));
		
		/* Then */
		mockMvc.perform(
					MockMvcRequestBuilders.get("/recipe/1/update/")
				).
				andExpect(status().isOk()).
				andExpect(view().name("recipe/recipeform")).
				andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	void deleteAction() throws Exception {
		mockMvc.perform(
					MockMvcRequestBuilders.get("/recipe/1/delete")
				).
				andExpect(status().is3xxRedirection()).
				andExpect(view().name("redirect:/"));
		
		verify(recipeReactiveService, times(1)).deleteById(anyString());
	}
	
	@Test
	void handleNotFound() throws Exception {
		when(recipeReactiveService.findById(anyString())).thenThrow(NotFoundException.class);
		
		mockMvc.perform(
					get("/recipe/1/show/")
				).
				andExpect(status().isNotFound()).
				andExpect(view().name("404error"));
	}
	
}
