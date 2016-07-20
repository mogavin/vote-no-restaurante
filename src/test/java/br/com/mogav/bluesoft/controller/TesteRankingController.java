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
import br.com.mogav.bluesoft.model.Voto;

public class TesteRankingController {

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
	public void exibirRankingDeVotos(){
		
		Collection<Voto> rankingVotos = Sets.newHashSet(
			new Voto(true, Restaurante.OUTBACK),
			new Voto(false, Restaurante.WENDYS)
		);
		
		when(mockVotoDao.listarRanking()).thenReturn(rankingVotos);
		
		//Executamos o método a ser testado
		controller.index();
		
		assertEquals(rankingVotos, spyResult.included().get("rankingVotos"));
	}
}
