package br.com.mogav.bluesoft.controller;

import static org.mockito.Mockito.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

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
	private Validator spyValidator;
	private Result spyResult;
	private VotacaoService mockService;
	private VotacaoController controller;
	
	@Before
	public void setup(){
		this.mockRankingController = mock(RankingController.class);
		this.spyResult = spy(new MockResult());
		this.spyValidator = spy(new MockValidator());
		this.mockService = mock(VotacaoService.class);
		this.controller = new VotacaoController(spyResult, spyValidator, mockService);
		//Necessário para testes de redirecionamento para páginas de 'RankingController'
		doReturn(mockRankingController).when(spyResult).redirectTo(RankingController.class);
	}
	
	
	@Test
	public void votar(){
		//Necessário para ignorarmos o redirecionamento da validação
		doReturn(mock(RankingController.class)).when(spyValidator).onErrorRedirectTo(RankingController.class);
		
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
