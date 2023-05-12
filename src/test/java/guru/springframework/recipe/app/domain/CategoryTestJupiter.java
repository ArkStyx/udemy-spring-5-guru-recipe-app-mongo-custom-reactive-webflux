package guru.springframework.recipe.app.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * TODO On utilise Junit Jupiter (Junit 5) plutôt que Junit 4 comme dans la vidéo : https://howtodoinjava.com/junit5/junit-5-vs-junit-4/
 */
class CategoryTestJupiter {

	Category category;
	
	@BeforeEach
	public void setUp() {
		category = new Category();
	}
	
	@Test
	void testGetId() {
		String valeurId = "4";
		category.setId(valeurId);
		assertEquals(valeurId, category.getId());
	}

	@Test
	void testGetDescription() {
//		fail("Not yet implemented");
	}

	@Test
	void testGetRecipes() {
//		fail("Not yet implemented");
	}

}
