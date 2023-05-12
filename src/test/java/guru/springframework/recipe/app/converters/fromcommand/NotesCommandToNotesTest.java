package guru.springframework.recipe.app.converters.fromcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.recipe.app.commands.NotesCommand;
import guru.springframework.recipe.app.domain.Notes;

class NotesCommandToNotesTest {

	private static final String ID = "1";
	private static final String DESCRIPTION = "DESCRIPTION";
	
	NotesCommandToNotes notesCommandToNotes;
	
	@BeforeEach
	void setUp() throws Exception {
		notesCommandToNotes = new NotesCommandToNotes();
	}

	@Test
	void testNullParameter() {
		assertNull(notesCommandToNotes.convert(null));
	}

	@Test
	void testEmptyParameter() {
		assertNotNull(notesCommandToNotes.convert(new NotesCommand()));
	}
	
	@Test
	void testConvert() {
		/* Given */
		NotesCommand source = new NotesCommand();
		source.setId(ID);
		source.setRecipeNotes(DESCRIPTION);
		
		/* When */
		Notes destination = notesCommandToNotes.convert(source);
		
		/* Then */
		assertNotNull(destination);
		assertEquals(source.getId(), destination.getId());
		assertEquals(source.getRecipeNotes(), destination.getRecipeNotes());
	}
}
