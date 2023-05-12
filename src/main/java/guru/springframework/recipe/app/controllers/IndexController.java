package guru.springframework.recipe.app.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.services.RecipeReactiveService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class IndexController {

	private final RecipeReactiveService recipeReactiveService;

	@RequestMapping({"", "/", "/index"})
	public String getIndexPage(Model model) {

		List<Recipe> listeDesRecettes = recipeReactiveService.getRecipes().collectList().block();
		log.info("getIndexPage - listeDesRecettes.size() : " + listeDesRecettes.size());
		
		model.addAttribute("toutesLesRecettes", listeDesRecettes);
		return "index";
	}
	
}
