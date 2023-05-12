package guru.springframework.recipe.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.recipe.app.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.app.converters.fromdomain.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipe.app.domain.UnitOfMeasure;
import guru.springframework.recipe.app.repositories.reactive.UnitOfMeasureReactiveRepository;
import reactor.core.publisher.Flux;

public class UnitOfMeasureServiceImplTestJupiter {

	UnitOfMeasureReactiveServiceImpl unitOfMeasureServiceImpl;
	
	@Mock
	UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
	
	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		unitOfMeasureServiceImpl = new UnitOfMeasureReactiveServiceImpl(unitOfMeasureReactiveRepository, unitOfMeasureToUnitOfMeasureCommand);
	}
	
	@Test
	void testRecupererToutesLesUnitesDeMesure() {
		
		/* Given */
		UnitOfMeasure uniteDeMesure01 = new UnitOfMeasure();
		uniteDeMesure01.setId("1");
		
		UnitOfMeasure uniteDeMesure02 = new UnitOfMeasure();
		uniteDeMesure02.setId("2");
		
		Set<UnitOfMeasure> setUniteDeMesure = new LinkedHashSet<>();
		setUniteDeMesure.add(uniteDeMesure01);
		setUniteDeMesure.add(uniteDeMesure02);
		
		when(unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(uniteDeMesure01, uniteDeMesure02));
		
		/* When */
		List<UnitOfMeasureCommand> toutesLesUnitesDeMesure = unitOfMeasureServiceImpl.recupererToutesLesUnitesDeMesure().collectList().block();

		/* Then */
		assertNotNull(toutesLesUnitesDeMesure);
		assertEquals(2, setUniteDeMesure.size());
		assertEquals(2, toutesLesUnitesDeMesure.size());
		assertEquals(setUniteDeMesure.size(), toutesLesUnitesDeMesure.size());
		verify(unitOfMeasureReactiveRepository, times(1)).findAll();
	}
}
