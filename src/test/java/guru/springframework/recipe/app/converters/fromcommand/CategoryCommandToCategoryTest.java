package guru.springframework.recipe.app.converters.fromcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.recipe.app.commands.CategoryCommand;
import guru.springframework.recipe.app.domain.Category;

class CategoryCommandToCategoryTest {
	
	private static final String ID = "1";
	private static final String DESCRIPTION = "DESCRIPTION";
	
	CategoryCommandToCategory categoryCommandToCategory;
	
	@BeforeEach
	void setUp() throws Exception {
		categoryCommandToCategory = new CategoryCommandToCategory();
	}

	@Test
	void testNullParameter() {
		assertNull(categoryCommandToCategory.convert(null));
	}
	@Test
	void testEmptyParameter() {
		assertNotNull(categoryCommandToCategory.convert(new CategoryCommand()));
	}
	
	@Test
	void testConvert() {
		/* Given */
		CategoryCommand source = new CategoryCommand();
		source.setId(ID);
		source.setDescription(DESCRIPTION);
		
		/* When */
		Category destination = categoryCommandToCategory.convert(source);
		
		/* Then */
		assertNotNull(destination);
		assertEquals(source.getId(), destination.getId());
		assertEquals(source.getDescription(), destination.getDescription());
	}

}
