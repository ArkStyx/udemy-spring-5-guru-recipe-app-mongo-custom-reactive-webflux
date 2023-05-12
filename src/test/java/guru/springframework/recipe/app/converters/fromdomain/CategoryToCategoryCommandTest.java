package guru.springframework.recipe.app.converters.fromdomain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.recipe.app.commands.CategoryCommand;
import guru.springframework.recipe.app.domain.Category;

class CategoryToCategoryCommandTest {
	
	private static final String ID = "1";
	private static final String DESCRIPTION = "DESCRIPTION";
	
	CategoryToCategoryCommand categoryToCategoryCommand;
	
	@BeforeEach
	void setUp() throws Exception {
		categoryToCategoryCommand = new CategoryToCategoryCommand();
	}

	@Test
	void testNullParameter() {
		assertNull(categoryToCategoryCommand.convert(null));
	}
	
	@Test
	void testEmptyParameter() {
		assertNotNull(categoryToCategoryCommand.convert(new Category()));
	}
	
	@Test
	void testConvert() {
		/* Given*/
		Category source = new Category();
		source.setId(ID);
		source.setDescription(DESCRIPTION);
		
		/* When*/
		CategoryCommand destination = categoryToCategoryCommand.convert(source);
		
		/* Then*/
		assertNotNull(destination);
		assertEquals(source.getId(), destination.getId());
		assertEquals(source.getDescription(), destination.getDescription());
	}

}
