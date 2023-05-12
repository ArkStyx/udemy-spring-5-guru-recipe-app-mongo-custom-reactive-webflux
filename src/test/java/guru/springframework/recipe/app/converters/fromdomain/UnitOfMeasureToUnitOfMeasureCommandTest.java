package guru.springframework.recipe.app.converters.fromdomain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.recipe.app.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.app.domain.UnitOfMeasure;

class UnitOfMeasureToUnitOfMeasureCommandTest {

	private static final String ID = "1";
	private static final String DESCRIPTION = "DESCRIPTION";
	
	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
	
	@BeforeEach
	void setUp() throws Exception {
		unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	}

	@Test
	void testNullParameter() {
		assertNull(unitOfMeasureToUnitOfMeasureCommand.convert(null));
		
	}

	@Test
	void testEmptyParameter() {
		assertNotNull(unitOfMeasureToUnitOfMeasureCommand.convert(new UnitOfMeasure()));
	}

	@Test
	void testConvert() {
		/* Given */
		UnitOfMeasure source = new UnitOfMeasure();
		source.setId(ID);
		source.setDescription(DESCRIPTION);
		
		/* When */
		UnitOfMeasureCommand destination = unitOfMeasureToUnitOfMeasureCommand.convert(source);
		
		/* Then */
		assertNotNull(destination);
		assertEquals(source.getId(), destination.getId());
		assertEquals(source.getDescription(), destination.getDescription());
		
	}

}
