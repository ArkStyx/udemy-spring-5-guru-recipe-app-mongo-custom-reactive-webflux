package guru.springframework.recipe.app.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.repositories.reactive.RecipeReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class ImageReactiveServiceImpl implements ImageReactiveService {

	private final RecipeReactiveRepository recipeReactiveRepository;
	
	@Override
	public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {
		log.info("Reception d'un fichier - originalFilename : " + file.getOriginalFilename());

		Mono<Recipe> monoRecipe = recipeReactiveRepository.findById(recipeId).map(recipe -> {
			try {
				byte[] byteFile = file.getBytes();
				Byte[] byteObjects = new Byte[byteFile.length];
				int i = 0;
				for (byte b : byteFile) {
					byteObjects[i++] = b;
				}

				recipe.setImage(byteObjects);
				return recipe;
			}
			catch (IOException e) {
				log.error("Erreur lors de la lecture du fichier");
				throw new RuntimeException(e);
			}
		});
		
		Recipe recipe = monoRecipe.block();
		recipeReactiveRepository.save(recipe).block();

        return Mono.empty();
	}
	
}
