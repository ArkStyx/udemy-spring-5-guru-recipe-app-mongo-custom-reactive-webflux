package guru.springframework.recipe.app.converters.fromcommand;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.recipe.app.commands.NotesCommand;
import guru.springframework.recipe.app.domain.Notes;
import lombok.Synchronized;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

	@Synchronized
	@Nullable
	@Override
	public Notes convert(NotesCommand source) {
		if (source == null) {
			return null;
		}
		
		Notes destination = new Notes();
		destination.setId(source.getId());
		destination.setRecipeNotes(source.getRecipeNotes());
		return destination;
	}

}
