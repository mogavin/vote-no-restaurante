package br.com.mogav.bluesoft.controller;

import static org.junit.Assert.assertArrayEquals;
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
import br.com.mogav.bluesoft.persistencia.VotacaoService;

public class TesteVotacaoController {

	private static final Usuario USUARIO = new Usuario("Joao", "joao@email.com");
	private static final Collection<Voto> VOTOS = ImmutableList.of(
			new Voto(USUARIO, true, Restaurante.OUTBACK),
			new Voto(USUARIO, false, Restaurante.WENDYS)
	);
	
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
	public void enviarRestaurantesDisponiveisParaView(){		
		//Executamos o método a ser testado
		controller.index();		
		assertArrayEquals(Restaurante.values(), (Object[])spyResult.included().get("restaurantes"));
	}
	
	@Test
	public void votar(){		
		Usuario comVotoRegistrado = mock(Usuario.class);
		
		//Necessário para ignorarmos o redirecionamento da validação
		doReturn(mock(RankingController.class)).when(spyValidator).onErrorRedirectTo(RankingController.class);
		
		when(mockService.registrarVotos(USUARIO, VOTOS)).thenReturn(comVotoRegistrado);
		
		//Executamos o método a ser testado
		this.controller.votar(USUARIO, VOTOS);		
		
		//Asseguramos que ocorra redirecionamento para a página de rankings, 
		//com os dados do usuário comVotoRegistrado  
		verify(mockRankingController).index(comVotoRegistrado);
	}
	
	@Test(expected = ValidationException.class)
	public void seOcorrerErrosNaVotacaoIncluirNaValidaco(){
		
		when(mockService.registrarVotos(USUARIO, VOTOS))
			.thenThrow(new RuntimeException());
		
		//Executamos o método a ser testado
		this.controller.votar(USUARIO, VOTOS);		
	}
}