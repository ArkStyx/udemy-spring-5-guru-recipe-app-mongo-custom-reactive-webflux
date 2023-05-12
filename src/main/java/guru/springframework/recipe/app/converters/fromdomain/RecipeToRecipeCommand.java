package guru.springframework.recipe.app.converters.fromdomain;

import java.util.Set;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.domain.Category;
import guru.springframework.recipe.app.domain.Ingredient;
import guru.springframework.recipe.app.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Synchronized;

@AllArgsConstructor
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final NotesToNotesCommand notesToNotesCommand;
	private final CategoryToCategoryCommand categoryToCategoryCommand;
	
	@Synchronized
	@Nullable
	@Override
	public RecipeCommand convert(Recipe source) {
		if (source == null) {
			return null;
		}
		
		RecipeCommand destination = new RecipeCommand();
		destination.setId(source.getId());
		destination.setDescription(source.getDescription());
		destination.setPrepTime(source.getPrepTime());
		destination.setCookTime(source.getCookTime());
		destination.setServings(source.getServings());
		destination.setSource(source.getSource());
		destination.setUrl(source.getUrl());
		destination.setDirections(source.getDirections());
		destination.setImage(source.getImage());
		destination.setDifficulty(source.getDifficulty());
		destination.setNotes(notesToNotesCommand.convert(source.getNotes()));
		
		Set<Ingredient> listeIngredient = source.getIngredients();
		if (listeIngredient != null && !listeIngredient.isEmpty()) {
			listeIngredient.stream().forEach(ingredient -> destination.getIngredients().add(ingredientToIngredientCommand.convert(ingredient)));
		}
		
		Set<Category> listeCategory = source.getCategories();
		if (listeCategory != null && !listeCategory.isEmpty()) {
			listeCategory.stream().forEach(categorie -> destination.getCategories().add(categoryToCategoryCommand.convert(categorie)));
		}
		
		return destination;
	}

}
