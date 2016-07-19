package br.com.mogav.bluesoft.controller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;
import br.com.caelum.vraptor.validator.Validator;
import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;
import br.com.mogav.bluesoft.service.VotacaoService;

public class TesteVotacaoController {

	private static final Collection<Voto> VOTOS = ImmutableList.of(
			new Voto(true, Restaurante.OUTBACK),
			new Voto(false, Restaurante.WENDYS)
	);
	private static final Usuario USUARIO = new Usuario("Joao", "joao@email.com");
	
	private RankingController mockRankingController;
	private Validator mockValidator;
	private Result spyResult;
	private VotacaoService mockService;
	private VotacaoController controller;
	
	@Before
	public void setup(){
		this.mockRankingController = mock(RankingController.class);
		this.spyResult = spy(new MockResult());
		this.mockValidator = new MockValidator();
		this.mockService = mock(VotacaoService.class);
		this.controller = new VotacaoController(spyResult, mockValidator, mockService);
		//Necessário para testes de redirecionamento para páginas de 'RankingController'
		doReturn(mockRankingController).when(spyResult).redirectTo(RankingController.class);
	}
	
	
	@Test
	public void votar(){		
		//Executamos o método a ser testado
		this.controller.votar(USUARIO, VOTOS);		
		
		verify(mockService).registrarVoto(USUARIO, VOTOS);
		//Asseguramos que ocorra redirecionamento para a página de rankings
		verify(mockRankingController).index();
	}
	
	@Test(expected = ValidationException.class)
	public void seOcorrerErrosNaVotacaoIncluirNaValidaco(){
		
		when(mockService.registrarVoto(USUARIO, VOTOS))
			.thenThrow(new RuntimeException());
		
		//Executamos o método a ser testado
		this.controller.votar(USUARIO, VOTOS);		
	}
}
