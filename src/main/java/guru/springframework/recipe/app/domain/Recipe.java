package guru.springframework.recipe.app.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import guru.springframework.recipe.app.domain.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Recipe {

	@Id
	private String id;
	
	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;
	private String directions;
	private Set<Ingredient> ingredients = new LinkedHashSet<>();
	private Byte[] image;
	private Difficulty difficulty;
	private Notes notes;
	private Set<Category> categories = new LinkedHashSet<>();
	
	/* Setter Custom qui vont remplacer les setter définis par Lombok */
	public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
        }
	}

	/* Autres methodes custom */
	public Recipe addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
		return this;
	}
	
}
