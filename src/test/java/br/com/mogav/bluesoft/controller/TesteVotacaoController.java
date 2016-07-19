package br.com.mogav.bluesoft.controller;

import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.service.VotacaoService;

public class TesteVotacaoController {

	private static final Usuario USUARIO = new Usuario("Joao", "joao@email.com");
	
	private RankingController mockRankingController;
	private Result spyResult;
	private VotacaoService mockService;
	private VotacaoController controller;
	
	@Before
	public void setup(){
		this.mockRankingController = mock(RankingController.class);
		this.spyResult = spy(new MockResult());
		this.mockService = mock(VotacaoService.class);
		this.controller = new VotacaoController(spyResult, mockService);
		//Necessário para testes de redirecionamento para páginas de 'RankingController'
		doReturn(mockRankingController).when(spyResult).redirectTo(RankingController.class);
	}
	
	
	@Test
	public void votar(){		
		Map<Restaurante, Boolean> votosPorRestaurante = ImmutableMap.of(
				Restaurante.GIRAFFAS, true,
				Restaurante.SUBWAY, false
		);
		
		//Executamos o método a ser testado
		this.controller.votar(USUARIO, votosPorRestaurante);		
		
		verify(mockService).registrarVoto(USUARIO, votosPorRestaurante);
		//Asseguramos que ocorra redirecionamento para a página de rankings
		verify(mockRankingController).index();
	}
}
