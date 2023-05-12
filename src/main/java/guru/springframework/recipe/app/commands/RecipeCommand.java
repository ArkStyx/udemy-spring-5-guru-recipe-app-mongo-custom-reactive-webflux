package guru.springframework.recipe.app.commands;

import java.util.LinkedHashSet;
import java.util.Set;

import guru.springframework.recipe.app.domain.enums.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
	
	private String id;
	
    @NotEmpty
    @Size(min = 3, max = 255)
	private String description;
    
    @Min(1)
    @Max(999)
	private Integer prepTime;
    
    @Min(1)
    @Max(999)
	private Integer cookTime;
    
    @Min(1)
    @Max(100)
	private Integer servings;
    
	private String source;
	
    @URL
	private String url;
    
    @NotEmpty
	private String directions;
    
	private Set<IngredientCommand> ingredients = new LinkedHashSet<>();
	private Byte[] image;
	private Difficulty difficulty;
	private NotesCommand notes;
	private Set<CategoryCommand> categories = new LinkedHashSet<>();
}
