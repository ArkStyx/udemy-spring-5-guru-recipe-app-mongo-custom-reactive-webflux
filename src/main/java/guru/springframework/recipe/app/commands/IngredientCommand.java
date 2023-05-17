package guru.springframework.recipe.app.commands;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
	
	private String id;
	private String recipeId;
	
    @NotEmpty
	private String description;
	
	@NotNull
	@Min(1)
	private BigDecimal amount;
	
	@NotNull
	private UnitOfMeasureCommand unitOfMeasure;
}
