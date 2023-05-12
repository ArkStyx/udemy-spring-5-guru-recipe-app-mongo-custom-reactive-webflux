package guru.springframework.recipe.app.converters.fromcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.recipe.app.commands.IngredientCommand;
import guru.springframework.recipe.app.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.app.domain.Ingredient;

class IngredientCommandToIngredientTest {

	private static final String ID = "1";
	private static final String DESCRIPTION = "DESCRIPTION";
	private static final BigDecimal AMOUNT = new BigDecimal(15);
	private static final String UNIT_OF_MEASURE_ID = "2";

	IngredientCommandToIngredient ingredientCommandToIngredient;
	
	UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
	
	@BeforeEach
	void setUp() throws Exception {
		unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
		ingredientCommandToIngredient = new IngredientCommandToIngredient(unitOfMeasureCommandToUnitOfMeasure);
	}

	@Test
	void testNullParameter() {
		assertNull(ingredientCommandToIngredient.convert(null));
	}
	
	@Test
	void testEmptyParameter() {
		assertNotNull(ingredientCommandToIngredient.convert(new IngredientCommand()));
	}
	
	@Test
	void testConvert() {
		/* Given */
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UNIT_OF_MEASURE_ID);
		
		IngredientCommand source = new IngredientCommand();
		source.setId(ID);
		source.setDescription(DESCRIPTION);
		source.setAmount(AMOUNT);
        source.setUnitOfMeasure(unitOfMeasureCommand);

		/* When */
		Ingredient destination = ingredientCommandToIngredient.convert(source);
		
		/* Then */
		assertNotNull(destination);
		assertEquals(ID, destination.getId());
		assertEquals(DESCRIPTION, destination.getDescription());
		assertEquals(AMOUNT, destination.getAmount());
		assertEquals(UNIT_OF_MEASURE_ID, destination.getUnitOfMeasure().getId());
	}

}
