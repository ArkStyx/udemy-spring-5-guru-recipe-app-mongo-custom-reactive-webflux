package guru.springframework.recipe.app.repositories.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.springframework.recipe.app.domain.Recipe;

@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
@DataMongoTest
public class RecipeReactiveRepositoryIntegrationTest {

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;
    
//    @Mock
//    RecipeReactiveRepository recipeReactiveRepository;

    @BeforeEach
    public void setUp() throws Exception {
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    public void saveRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setDescription("Yummy");

        recipeReactiveRepository.save(recipe).block();

        Long count = recipeReactiveRepository.count().block();
        assertEquals(Long.valueOf(1L), count);
    }

}
