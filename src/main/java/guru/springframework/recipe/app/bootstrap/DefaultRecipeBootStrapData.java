package guru.springframework.recipe.app.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import guru.springframework.recipe.app.domain.Category;
import guru.springframework.recipe.app.domain.Ingredient;
import guru.springframework.recipe.app.domain.Notes;
import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.domain.UnitOfMeasure;
import guru.springframework.recipe.app.domain.enums.Difficulty;
import guru.springframework.recipe.app.repositories.reactive.CategoryReactiveRepository;
import guru.springframework.recipe.app.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.recipe.app.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Created by jt on 6/13/17.
 */
@Slf4j
@Component
public class DefaultRecipeBootStrapData implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryReactiveRepository categoryReactiveRepository;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    public DefaultRecipeBootStrapData(CategoryReactiveRepository categoryReactiveRepository, RecipeReactiveRepository recipeReactiveRepository, 
    								UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository) {
        this.categoryReactiveRepository = categoryReactiveRepository;
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadCategories();
        loadUom();
        recipeReactiveRepository.saveAll(getRecipes()).collectList().block();
        log.debug("Loading Bootstrap Data");
        
        log.debug("Count categoryReactiveRepository : " + categoryReactiveRepository.count().block());
        log.debug("Count recipeReactiveRepository : " + recipeReactiveRepository.count().block());
        log.debug("Count unitOfMeasureReactiveRepository : " + unitOfMeasureReactiveRepository.count().block());
    }

    private void loadCategories(){
        Category cat1 = new Category();
        cat1.setDescription("American");
        categoryReactiveRepository.save(cat1).block();

        Category cat2 = new Category();
        cat2.setDescription("Italian");
        categoryReactiveRepository.save(cat2).block();

        Category cat3 = new Category();
        cat3.setDescription("Mexican");
        categoryReactiveRepository.save(cat3).block();

        Category cat4 = new Category();
        cat4.setDescription("Fast Food");
        categoryReactiveRepository.save(cat4).block();
    }

    private void loadUom(){
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setDescription("Teaspoon");
        unitOfMeasureReactiveRepository.save(uom1).block();

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setDescription("Tablespoon");
        unitOfMeasureReactiveRepository.save(uom2).block();

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setDescription("Cup");
        unitOfMeasureReactiveRepository.save(uom3).block();

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setDescription("Pinch");
        unitOfMeasureReactiveRepository.save(uom4).block();

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setDescription("Ounce");
        unitOfMeasureReactiveRepository.save(uom5).block();

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setDescription("Each");
        unitOfMeasureReactiveRepository.save(uom6).block();

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setDescription("Pint");
        unitOfMeasureReactiveRepository.save(uom7).block();

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setDescription("Dash");
        unitOfMeasureReactiveRepository.save(uom8).block();
    }

    private List<Recipe> getRecipes() {

        //get UOMs
        Mono<UnitOfMeasure> eachUomOptionalMono = unitOfMeasureReactiveRepository.findByDescription("Each");
        Optional<UnitOfMeasure> eachUomOptional = eachUomOptionalMono.blockOptional();
        if(!eachUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Mono<UnitOfMeasure> tableSpoonUomMono = unitOfMeasureReactiveRepository.findByDescription("Tablespoon");
        Optional<UnitOfMeasure> tableSpoonUomOptional = tableSpoonUomMono.blockOptional();
        if(!tableSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Mono<UnitOfMeasure> teaSpoonUomMono = unitOfMeasureReactiveRepository.findByDescription("Teaspoon");
        Optional<UnitOfMeasure> teaSpoonUomOptional = teaSpoonUomMono.blockOptional();
        if(!teaSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Mono<UnitOfMeasure> dashUomMono = unitOfMeasureReactiveRepository.findByDescription("Dash");
        Optional<UnitOfMeasure> dashUomOptional = dashUomMono.blockOptional();
        if(!dashUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Mono<UnitOfMeasure> pintUomMono = unitOfMeasureReactiveRepository.findByDescription("Pint");
        Optional<UnitOfMeasure> pintUomOptional = pintUomMono.blockOptional();
        if(!pintUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Mono<UnitOfMeasure> cupsUomMono = unitOfMeasureReactiveRepository.findByDescription("Cup");
        Optional<UnitOfMeasure> cupsUomOptional = cupsUomMono.blockOptional();
        if(!cupsUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        //get optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teapoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = dashUomOptional.get();
        UnitOfMeasure cupsUom = cupsUomOptional.get();

        //get Categories
        Mono<Category> americanCategoryMono = categoryReactiveRepository.findByDescription("American");
        Optional<Category> americanCategoryOptional = americanCategoryMono.blockOptional();
        if(!americanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Mono<Category> mexicanCategoryMono = categoryReactiveRepository.findByDescription("Mexican");
        Optional<Category> mexicanCategoryOptional = mexicanCategoryMono.blockOptional();
        if(!mexicanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        /*
         * Guacamole
         */
        Recipe recetteGuacamole = new Recipe();
        recetteGuacamole.setDescription("Perfect Guacamole");
        recetteGuacamole.setPrepTime(10);
        recetteGuacamole.setCookTime(0);
        recetteGuacamole.setDifficulty(Difficulty.EASY);
        recetteGuacamole.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

        Notes noteGuacamole = new Notes();
        noteGuacamole.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");
        recetteGuacamole.setNotes(noteGuacamole);

        //very redundent - could add helper method, and make this simpler
        recetteGuacamole.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
        recetteGuacamole.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teapoonUom));
        recetteGuacamole.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom));
        recetteGuacamole.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom));
        recetteGuacamole.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom));
        recetteGuacamole.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUom));
        recetteGuacamole.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom));
        recetteGuacamole.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUom));

        recetteGuacamole.getCategories().add(americanCategory);
        recetteGuacamole.getCategories().add(mexicanCategory);

        recetteGuacamole.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
        recetteGuacamole.setServings(4);
        recetteGuacamole.setSource("Simply Recipes");


        /*
         * Tacos
         */
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);
        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        Notes notesTacos = new Notes();
        notesTacos.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
        tacosRecipe.setNotes(notesTacos);
        
        tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teapoonUom));
        tacosRecipe.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teapoonUom));
        tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teapoonUom));
        tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teapoonUom));
        tacosRecipe.addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUom));
        tacosRecipe.addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUom));
        tacosRecipe.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cupsUom));
        tacosRecipe.addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUom));
        tacosRecipe.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUom));
        tacosRecipe.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUom));
        tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupsUom));
        tacosRecipe.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUom));
        
        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);
        
        tacosRecipe.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacosRecipe.setServings(4);
        tacosRecipe.setSource("Simply Recipes");

        
        //add to return list
        List<Recipe> listeRecettes = new ArrayList<>(2);
        listeRecettes.add(recetteGuacamole);
        listeRecettes.add(tacosRecipe);
        
        return listeRecettes;
    }
    
}
