package guru.springframework.recipe.app.converters.fromdomain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.domain.Category;
import guru.springframework.recipe.app.domain.Ingredient;
import guru.springframework.recipe.app.domain.Notes;
import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.domain.enums.Difficulty;

class RecipeToRecipeCommandTest {

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

    RecipeToRecipeCommand recipeToRecipeCommand;
	
	IngredientToIngredientCommand ingredientToIngredientCommand;
	NotesToNotesCommand notesToNotesCommand;
	CategoryToCategoryCommand categoryToCategoryCommand;
	
	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
	
	@BeforeEach
	void setUp() throws Exception {
		unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
		
		ingredientToIngredientCommand = new IngredientToIngredientCommand(unitOfMeasureToUnitOfMeasureCommand);
		notesToNotesCommand = new NotesToNotesCommand();
		categoryToCategoryCommand = new CategoryToCategoryCommand();
		
		recipeToRecipeCommand = new RecipeToRecipeCommand(ingredientToIngredientCommand, notesToNotesCommand, categoryToCategoryCommand);
		
	}

	@Test
	void testNullParameter() {
		assertNull(recipeToRecipeCommand.convert(null));
	}

	@Test
	void testEmptyParameter() {
		assertNotNull(recipeToRecipeCommand.convert(new Recipe()));
	}
	
	@Test
	void testConvert() {
		/* Given */
        Category categorie01 = new Category();
        categorie01.setId(CATEGORIE_ID_01);

        Category categorie02 = new Category();
        categorie02.setId(CATEGORIE_ID_02);

        Ingredient ingredient01 = new Ingredient();
        ingredient01.setId(INGREDIENT_ID_01);

        Ingredient ingredient02 = new Ingredient();
        ingredient02.setId(INGREDIENT_ID_02);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);
		
		Recipe source = new Recipe();
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
        RecipeCommand destination = recipeToRecipeCommand.convert(source);
		
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
