package guru.springframework.recipe.app.services;

import org.springframework.stereotype.Service;

import guru.springframework.recipe.app.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.app.converters.fromdomain.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipe.app.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@Service
public class UnitOfMeasureReactiveServiceImpl implements UnitOfMeasureReactiveService {

	private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
	
	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
	
	@Override
	public Flux<UnitOfMeasureCommand> recupererToutesLesUnitesDeMesure() {
		return unitOfMeasureReactiveRepository.findAll().map(unitOfMeasureToUnitOfMeasureCommand::convert);
	}

}
