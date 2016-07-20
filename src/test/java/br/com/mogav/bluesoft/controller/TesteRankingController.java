package br.com.mogav.bluesoft.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.mogav.bluesoft.model.ItemRankingVotos;
import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.persistencia.VotacaoService;

public class TesteRankingController {

	private static final Usuario USUARIO = new Usuario("Joao", "joao@email.com");
	
	private Result spyResult;
	private VotacaoService mockService;
	private RankingController controller;
	
	@Before
	public void setup(){
		this.mockService = mock(VotacaoService.class);
		this.spyResult = spy(new MockResult());
		this.controller = new RankingController(spyResult, mockService);
	}
	
	
	@Test
	public void exibirRankingsDeVotos(){
			
		Collection<ItemRankingVotos> rankingGeral = Sets.newHashSet(
			new ItemRankingVotos(Restaurante.MCDONALDS, 5, 9),
			new ItemRankingVotos(Restaurante.WENDYS, 4, 0)
		);		
		Collection<ItemRankingVotos> rankingUsuario = Sets.newHashSet(
			new ItemRankingVotos(Restaurante.SUBWAY, 1, 7),
			new ItemRankingVotos(Restaurante.OUTBACK, 0, 0)
		);
		
		when(mockService.listarRankingGeral()).thenReturn(rankingGeral);
		when(mockService.listarRankingUsuario(USUARIO)).thenReturn(rankingUsuario);

		
		//Executamos o método a ser testado
		controller.index(USUARIO);
		
		assertEquals(rankingGeral, spyResult.included().get("rankingGeral"));
		assertEquals(rankingUsuario, spyResult.included().get("rankingUsuario"));
	}
}