package guru.springframework.recipe.app.services;

import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

public interface ImageReactiveService {

	Mono<Void> saveImageFile(String recipeId, MultipartFile file);

}
