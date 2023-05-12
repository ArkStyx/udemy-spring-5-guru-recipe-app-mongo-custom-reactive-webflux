package guru.springframework.recipe.app.converters.fromdomain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.recipe.app.commands.IngredientCommand;
import guru.springframework.recipe.app.domain.Ingredient;
import guru.springframework.recipe.app.domain.UnitOfMeasure;

class IngredientToIngredientCommandTest {

	private static final String ID = "1";
	private static final String DESCRIPTION = "DESCRIPTION";
	private static final BigDecimal AMOUNT = new BigDecimal(15);
	private static final String UNIT_OF_MEASURE_ID = "2";
	
	IngredientToIngredientCommand ingredientToIngredientCommand;
	
	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
	
	@BeforeEach
	void setUp() throws Exception {
		unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
		ingredientToIngredientCommand = new IngredientToIngredientCommand(unitOfMeasureToUnitOfMeasureCommand);
	}

	@Test
	void testNullParameter() {
		assertNull(ingredientToIngredientCommand.convert(null));
	}
	
	@Test
	void testEmptyParameter() {
		assertNotNull(ingredientToIngredientCommand.convert(new Ingredient()));
	}
	
	@Test
	void testConvert() {
		/* Given */
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UNIT_OF_MEASURE_ID);
		
		Ingredient source = new Ingredient();
		source.setId(ID);
		source.setDescription(DESCRIPTION);
		source.setAmount(AMOUNT);
        source.setUnitOfMeasure(unitOfMeasure);
		
		/* When */
		IngredientCommand destination = ingredientToIngredientCommand.convert(source);
		
		/* Then */
		assertNotNull(destination);
		assertEquals(ID, destination.getId());
		assertEquals(DESCRIPTION, destination.getDescription());
		assertEquals(AMOUNT, destination.getAmount());
		assertEquals(UNIT_OF_MEASURE_ID, destination.getUnitOfMeasure().getId());
	}

}
