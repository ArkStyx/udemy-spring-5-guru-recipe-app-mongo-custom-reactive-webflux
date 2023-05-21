package guru.springframework.recipe.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.services.RecipeReactiveService;

@Configuration
public class WebConfig {

	@Bean
	RouterFunction<?> routes(RecipeReactiveService recipeReactiveService) {
		return RouterFunctions.route(org.springframework.web.reactive.function.server.RequestPredicates.GET("/api/recipes"), 
				serverRequest -> ServerResponse
										.ok()
										.contentType(MediaType.APPLICATION_JSON)
										.body(recipeReactiveService.getRecipes(), Recipe.class));
	}
}
