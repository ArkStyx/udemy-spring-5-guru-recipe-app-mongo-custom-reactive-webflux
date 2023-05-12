package guru.springframework.recipe.app.converters.fromcommand;

import java.util.Set;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.recipe.app.commands.CategoryCommand;
import guru.springframework.recipe.app.commands.IngredientCommand;
import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Synchronized;

@AllArgsConstructor
@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final NotesCommandToNotes notesCommandToNotes;
	private final CategoryCommandToCategory categoryCommandToCategory;
		
	@Synchronized
	@Nullable
	@Override
	public Recipe convert(RecipeCommand source) {
		if (source == null) {
			return null;
		}
		
		Recipe destination = new Recipe();
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
		destination.setNotes(notesCommandToNotes.convert(source.getNotes()));
		
		Set<IngredientCommand> listeIngredientCommand = source.getIngredients();
		if (listeIngredientCommand != null && !listeIngredientCommand.isEmpty()) {
			listeIngredientCommand.stream().forEach(ingredient -> destination.getIngredients().add(ingredientCommandToIngredient.convert(ingredient)));
		}
		
		Set<CategoryCommand> listeCategoryCommand = source.getCategories();
		if (listeCategoryCommand != null && !listeCategoryCommand.isEmpty()) {
			listeCategoryCommand.stream().forEach(categorie -> destination.getCategories().add(categoryCommandToCategory.convert(categorie)));
		}

		return destination;
	}
	
}
