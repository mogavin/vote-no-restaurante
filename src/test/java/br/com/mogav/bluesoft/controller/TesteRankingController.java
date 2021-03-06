package br.com.mogav.bluesoft.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.mogav.bluesoft.model.ItemRankingVotos;
import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.persistencia.VotacaoService;

import com.google.common.collect.Lists;

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
			
		List<ItemRankingVotos> rankingGeral = Lists.newArrayList(
			new ItemRankingVotos(Restaurante.MCDONALDS, 5, 9),
			new ItemRankingVotos(Restaurante.WENDYS, 4, 0)
		);		
		List<ItemRankingVotos> rankingUsuario = Lists.newArrayList(
			new ItemRankingVotos(Restaurante.SUBWAY, 1, 7),
			new ItemRankingVotos(Restaurante.OUTBACK, 0, 0)
		);
		
		when(mockService.listarRankingGeral()).thenReturn(rankingGeral);
		when(mockService.listarRankingUsuario(USUARIO)).thenReturn(rankingUsuario);

		
		//Executamos o método a ser testado
		controller.index(USUARIO);
		
		assertEquals(USUARIO, spyResult.included().get("usuario"));
		assertEquals(rankingGeral, spyResult.included().get("rankingGeral"));
		assertEquals(rankingUsuario, spyResult.included().get("rankingUsuario"));
	}
}