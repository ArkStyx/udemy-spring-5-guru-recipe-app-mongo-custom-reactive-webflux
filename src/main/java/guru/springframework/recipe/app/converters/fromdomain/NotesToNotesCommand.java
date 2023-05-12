package guru.springframework.recipe.app.converters.fromdomain;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.recipe.app.commands.NotesCommand;
import guru.springframework.recipe.app.domain.Notes;
import lombok.Synchronized;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

	@Synchronized
	@Nullable
	@Override
	public NotesCommand convert(Notes source) {
		if (source == null) {
			return null;
		}
		
		NotesCommand destination = new NotesCommand();
		destination.setId(source.getId());
		destination.setRecipeNotes(source.getRecipeNotes());
		return destination;
	}

}
