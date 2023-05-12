package guru.springframework.recipe.app.services;

import guru.springframework.recipe.app.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientReactiveService {

	Mono<IngredientCommand> recupererParIdRecetteEtIdIngredient(String idRecette, String idIngredient);

	Mono<IngredientCommand> sauvegarderIngredient(IngredientCommand ingredientCommand);

	Mono<Void> supprimerIngredientDansRecetteParId(String idRecette, String idIngredient);
}
