package guru.springframework.recipe.app.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.springframework.recipe.app.domain.UnitOfMeasure;
import guru.springframework.recipe.app.repositories.reactive.UnitOfMeasureReactiveRepository;
import reactor.core.publisher.Mono;

@Disabled("Classe de tests seulement utilisable avec JPA")
@ExtendWith(SpringExtension.class)
@DataMongoTest
class UnitOfMeasureRepositoryIntegrationTest {

	@Autowired
	UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void findByDescription() throws Exception {
		String uniteDeMesureCherchee = "Teaspoon";
		Mono<UnitOfMeasure> teaspoonMono = unitOfMeasureReactiveRepository.findByDescription(uniteDeMesureCherchee);
		Optional<UnitOfMeasure> teaspoonMonoOptional = teaspoonMono.blockOptional();
		assertEquals(uniteDeMesureCherchee, teaspoonMonoOptional.get().getDescription());
	}

}
