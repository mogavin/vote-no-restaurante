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
import br.com.mogav.bluesoft.dao.VotacaoService;
import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;

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
			
		Collection<Voto> rankingGeral = Sets.newHashSet(
			new Voto(USUARIO, true, Restaurante.OUTBACK),
			new Voto(USUARIO, false, Restaurante.WENDYS)
		);		
		Collection<Voto> rankingUsuario = Sets.newHashSet(
			new Voto(USUARIO, false, Restaurante.MCDONALDS),
			new Voto(USUARIO, true, Restaurante.GIRAFFAS)
		);
		
		when(mockService.listarRankingGeral()).thenReturn(rankingGeral);
		when(mockService.listarRankingUsuario(USUARIO)).thenReturn(rankingUsuario);

		
		//Executamos o método a ser testado
		controller.index(USUARIO);
		
		assertEquals(rankingGeral, spyResult.included().get("rankingGeral"));
		assertEquals(rankingGeral, spyResult.included().get("rankingUsuario"));
	}
}
