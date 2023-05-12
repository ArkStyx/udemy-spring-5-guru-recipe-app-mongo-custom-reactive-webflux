package guru.springframework.recipe.app.converters.fromdomain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.recipe.app.commands.NotesCommand;
import guru.springframework.recipe.app.domain.Notes;

class NotesToNotesCommandTest {

	private static final String ID = "1";
	private static final String DESCRIPTION = "DESCRIPTION";
	
	NotesToNotesCommand notesToNotesCommand;
	
	@BeforeEach
	void setUp() throws Exception {
		notesToNotesCommand = new NotesToNotesCommand();
	}

	@Test
	void testNullParameter() {
		assertNull(notesToNotesCommand.convert(null));
	}

	@Test
	void testEmptyParameter() {
		assertNotNull(notesToNotesCommand.convert(new Notes()));
	}
	
	@Test
	void testConvert() {
		/* Given */
		Notes source = new Notes();
		source.setId(ID);
		source.setRecipeNotes(DESCRIPTION);
		
		/* When */
		NotesCommand destination = notesToNotesCommand.convert(source);
		
		/* Then */
		assertNotNull(destination);
		assertEquals(source.getId(), destination.getId());
		assertEquals(source.getRecipeNotes(), destination.getRecipeNotes());
	}
}
