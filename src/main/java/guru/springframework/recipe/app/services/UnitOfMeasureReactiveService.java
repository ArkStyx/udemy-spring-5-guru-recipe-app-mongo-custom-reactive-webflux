package guru.springframework.recipe.app.services;

import guru.springframework.recipe.app.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureReactiveService {

	Flux<UnitOfMeasureCommand> recupererToutesLesUnitesDeMesure();
	
}
