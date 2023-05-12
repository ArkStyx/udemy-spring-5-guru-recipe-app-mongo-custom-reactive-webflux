package guru.springframework.recipe.app.converters.fromcommand;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.recipe.app.commands.CategoryCommand;
import guru.springframework.recipe.app.domain.Category;
import lombok.Synchronized;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

	@Synchronized
	@Nullable
	@Override
	public Category convert(CategoryCommand source) {
		if (source == null) {
			return null;
		}
		
		Category destination = new Category();
		destination.setId(source.getId());
		destination.setDescription(source.getDescription());
		return destination;
	}

}
