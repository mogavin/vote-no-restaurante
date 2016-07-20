package br.com.mogav.bluesoft.controller;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.google.common.collect.Sets;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.mogav.bluesoft.dao.VotoDao;
import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;

public class TesteRankingController {

	private static final Usuario USUARIO = new Usuario("Joao", "joao@email.com");
	
	private Result spyResult;
	private VotoDao mockVotoDao;
	private RankingController controller;
	
	@Before
	public void setup(){
		this.mockVotoDao = mock(VotoDao.class);
		this.spyResult = spy(new MockResult());
		this.controller = new RankingController(spyResult, mockVotoDao);
	}
	
	
	@Test
	public void exibirRankingGeralDeVotos(){
		
		Collection<Voto> rankingVotos = Sets.newHashSet(
			new Voto(USUARIO, true, Restaurante.OUTBACK),
			new Voto(USUARIO, false, Restaurante.WENDYS)
		);
		
		when(mockVotoDao.listarRankingGeral()).thenReturn(rankingVotos);
		
		//Executamos o método a ser testado
		controller.index();
		
		assertEquals(rankingVotos, spyResult.included().get("rankingVotos"));
	}
}
