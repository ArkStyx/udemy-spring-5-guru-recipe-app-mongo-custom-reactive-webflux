package guru.springframework.recipe.app.converters.fromcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.recipe.app.commands.CategoryCommand;
import guru.springframework.recipe.app.commands.IngredientCommand;
import guru.springframework.recipe.app.commands.NotesCommand;
import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.domain.enums.Difficulty;

class RecipeCommandToRecipeTest {

	private static final String RECIPE_ID = "1";
    private static final Integer COOK_TIME = 5;
    private static final Integer PREP_TIME = 7;
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String DIRECTIONS = "DIRECTIONS";
    private static final Difficulty DIFFICULTY = Difficulty.EASY;
    private static final Integer SERVING = 3;
    private static final String SOURCE = "Source";
    private static final String URL = "Some URL";
	private static final String CATEGORIE_ID_01 = "1";
	private static final String CATEGORIE_ID_02 = "2";
	private static final String INGREDIENT_ID_01 = "3";
	private static final String INGREDIENT_ID_02 = "4";
	private static final String NOTES_ID = "9";

	RecipeCommandToRecipe recipeCommandToRecipe;
	
	IngredientCommandToIngredient ingredientCommandToIngredient;
	NotesCommandToNotes notesCommandToNotes;
	CategoryCommandToCategory categoryCommandToCategory;
	
	UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
	
	@BeforeEach
	void setUp() throws Exception {
		unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
		
		ingredientCommandToIngredient = new IngredientCommandToIngredient(unitOfMeasureCommandToUnitOfMeasure);
		notesCommandToNotes = new NotesCommandToNotes();
		categoryCommandToCategory = new CategoryCommandToCategory();
		
		recipeCommandToRecipe = new RecipeCommandToRecipe(ingredientCommandToIngredient, notesCommandToNotes, categoryCommandToCategory);
	}

	@Test
	void testNullParameter() {
		assertNull(recipeCommandToRecipe.convert(null));
	}

	@Test
	void testEmptyParameter() {
		assertNotNull(recipeCommandToRecipe.convert(new RecipeCommand()));
	}
	
	@Test
	void testConvert() {
		/* Given */
        CategoryCommand categorie01 = new CategoryCommand();
        categorie01.setId(CATEGORIE_ID_01);

        CategoryCommand categorie02 = new CategoryCommand();
        categorie02.setId(CATEGORIE_ID_02);

        IngredientCommand ingredient01 = new IngredientCommand();
        ingredient01.setId(INGREDIENT_ID_01);

        IngredientCommand ingredient02 = new IngredientCommand();
        ingredient02.setId(INGREDIENT_ID_02);

        NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);
		
		RecipeCommand source = new RecipeCommand();
		source.setId(RECIPE_ID);
		source.setCookTime(COOK_TIME);
		source.setPrepTime(PREP_TIME);
		source.setDescription(DESCRIPTION);
		source.setDifficulty(DIFFICULTY);
		source.setDirections(DIRECTIONS);
        source.setServings(SERVING);
        source.setSource(SOURCE);
        source.setUrl(URL);
        source.setNotes(notes);
        source.getCategories().add(categorie01);
        source.getCategories().add(categorie02);
        source.getIngredients().add(ingredient01);
        source.getIngredients().add(ingredient02);
        
		/* When */
        Recipe destination = recipeCommandToRecipe.convert(source);
		
		/* Then */
        assertNotNull(destination);
        assertEquals(RECIPE_ID, destination.getId());
        assertEquals(COOK_TIME, destination.getCookTime());
        assertEquals(PREP_TIME, destination.getPrepTime());
        assertEquals(DESCRIPTION, destination.getDescription());
        assertEquals(DIFFICULTY, destination.getDifficulty());
        assertEquals(DIRECTIONS, destination.getDirections());
        assertEquals(SERVING, destination.getServings());
        assertEquals(SOURCE, destination.getSource());
        assertEquals(URL, destination.getUrl());
        assertEquals(NOTES_ID, destination.getNotes().getId());
        assertEquals(2, destination.getCategories().size());
        assertEquals(2, destination.getIngredients().size());
	}
}
