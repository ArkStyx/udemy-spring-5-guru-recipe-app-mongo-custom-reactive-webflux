package guru.springframework.recipe.app.converters.fromcommand;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.recipe.app.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.app.domain.UnitOfMeasure;

class UnitOfMeasureCommandToUnitOfMeasureTest {

	private static final String ID = "1";
	private static final String DESCRIPTION = "DESCRIPTION";
	
	UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureConverterTest;
	
	@BeforeEach
	void setUp() throws Exception {
		unitOfMeasureConverterTest = new UnitOfMeasureCommandToUnitOfMeasure();
	}

	@Test
	void testNullParameter() {
		assertNull(unitOfMeasureConverterTest.convert(null));
	}
	
	@Test
	void testEmptyParameter() {
		assertNotNull(unitOfMeasureConverterTest.convert(new UnitOfMeasureCommand()));
	}
	
	@Test
	void testConvert() {
		/* Given */
		UnitOfMeasureCommand source = new UnitOfMeasureCommand();
		source.setId(ID);
		source.setDescription(DESCRIPTION);
		
		/* When */
		UnitOfMeasure destination = unitOfMeasureConverterTest.convert(source);
		
		/* Then */
		assertNotNull(destination);
		assertEquals(source.getId(), destination.getId());
		assertEquals(source.getDescription(), destination.getDescription());
	}

}
