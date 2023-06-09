package guru.springframework.recipe.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.services.ImageReactiveService;
import guru.springframework.recipe.app.services.RecipeReactiveService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Controller
public class ImageController {

	private final ImageReactiveService imageReactiveService;
	private final RecipeReactiveService recipeReactiveService;
	
	@GetMapping(value = "recipe/{id}/image")
	public String showUploadForm(Model model, @PathVariable String id) {
		log.info("showUploadForm - id recette recherchee : " + id);
		
		Mono<RecipeCommand> monoCommand = recipeReactiveService.findCommandById(id);
		model.addAttribute("recipe", monoCommand);
		
		return "recipe/imageuploadform";
	}
	
	@PostMapping("recipe/{id}/image")
	public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {
		log.info("handleImagePost - id recette recherchee : " + id);
		
		imageReactiveService.saveImageFile(id, file).block();
		return "redirect:/recipe/" + id + "/show";
	}
	
	
	/*
	TODO FIXME
	Required MultipartFile parameter 'imagefile' is not present
	org.springframework.web.server.ServerWebInputException: 400 BAD_REQUEST "Required MultipartFile parameter 'imagefile' is not present"
	at org.springframework.web.reactive.result.method.annotation.RequestParamMethodArgumentResolver.handleMissingValue(RequestParamMethodArgumentResolver.java:114)
	 */
	
	
	// TODO FIXME CODE A MODIFIER DANS LE FUTUR
//	@GetMapping("recipe/{id}/recipeimage")
//	public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
//		log.info("renderImageFromDB - id recette recherchee : " + id);
//		RecipeCommand recipeCommand = recipeReactiveService.findCommandById(id).block();
//		if (recipeCommand.getImage() == null) {
//			log.error("No Image Found");
//		}
//		else {
//			Byte[] imageDB = recipeCommand.getImage();
//			
//			int i = 0;
//			byte[] byteArray = new byte[imageDB.length];
//			for (Byte wrappedByte : imageDB) {
//				/* Auto Unboxing */
//				byteArray[i++] = wrappedByte;
//			}
//
//			response.setContentType("image/jpeg");
//			InputStream is = new ByteArrayInputStream(byteArray);
//			IOUtils.copy(is, response.getOutputStream());
//		}
//	}
	
}
