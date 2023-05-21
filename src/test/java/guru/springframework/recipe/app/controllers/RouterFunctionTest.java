package guru.springframework.recipe.app.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

import guru.springframework.recipe.app.config.WebConfig;
import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.services.RecipeReactiveService;
import reactor.core.publisher.Flux;

public class RouterFunctionTest {

	WebTestClient webTestClient;
	
	@Mock
	RecipeReactiveService recipeReactiveService;
	
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		WebConfig webConfig = new WebConfig();
		RouterFunction<?> routerFunction = webConfig.routes(recipeReactiveService);
		webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
	}
	
    @Test
    public void testGetRecipes() throws Exception {

    	Mockito.when(recipeReactiveService.getRecipes()).thenReturn(Flux.just());

        webTestClient.get().uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testGetRecipesWithData() throws Exception {

        Mockito.when(recipeReactiveService.getRecipes()).thenReturn(Flux.just(new Recipe(), new Recipe()));

        webTestClient.get().uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Recipe.class);
    }
}
