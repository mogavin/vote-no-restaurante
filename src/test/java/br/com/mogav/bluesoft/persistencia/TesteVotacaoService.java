package br.com.mogav.bluesoft.persistencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import br.com.mogav.bluesoft.model.ItemRankingVotos;
import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;

public class TesteVotacaoService {

	private static final Usuario USUARIO_1 = new Usuario("Joao", "joao@email.com");
	private static final Usuario USUARIO_2 = new Usuario("Pedro", "pedro@email.com");
	
	//Ranking usuario 1: 
	//1o - OUTBACK(2 positivos, 0 negativos)
	//2o - SUBWAY(1 positivos, 0 negativos)
	//3o - GIRAFFAS(2 positivos, 1 negativos)
	//4o - MCDONALDS(1 positivos, 1 negativos)
	//5o - WENDYS(0 positivos, 1 negativos)
	private static final Collection<Voto> VOTOS_USUARIO_1 = ImmutableSet.of(
			new Voto(USUARIO_1, true, Restaurante.OUTBACK),
			new Voto(USUARIO_1, true, Restaurante.OUTBACK),
			new Voto(USUARIO_1, false, Restaurante.WENDYS),
			new Voto(USUARIO_1, false, Restaurante.MCDONALDS),
			new Voto(USUARIO_1, true, Restaurante.MCDONALDS),
			new Voto(USUARIO_1, true, Restaurante.SUBWAY),
			new Voto(USUARIO_1, false, Restaurante.GIRAFFAS),
			new Voto(USUARIO_1, true, Restaurante.GIRAFFAS),
			new Voto(USUARIO_1, true, Restaurante.GIRAFFAS)
	);	

	//Ranking usuario 2: 
	//1o - MCDONALDS(2 positivos, 0 negativos)
	//2o - OUTBACK(1 positivos, 0 negativos)
	//3o - WENDYS(2 positivos, 1 negativos)
	//4o - SUBWAY(1 positivos, 1 negativos)
	//5o - GIRAFFAS(0 positivos, 1 negativos)
	private static final Collection<Voto> VOTOS_USUARIO_2 = ImmutableSet.of(
			new Voto(USUARIO_2, true, Restaurante.OUTBACK),
			new Voto(USUARIO_2, true, Restaurante.WENDYS),
			new Voto(USUARIO_2, true, Restaurante.WENDYS),
			new Voto(USUARIO_2, false, Restaurante.WENDYS),
			new Voto(USUARIO_2, true, Restaurante.MCDONALDS),
			new Voto(USUARIO_2, true, Restaurante.MCDONALDS),
			new Voto(USUARIO_2, true, Restaurante.SUBWAY),
			new Voto(USUARIO_2, false, Restaurante.SUBWAY),
			new Voto(USUARIO_2, false, Restaurante.GIRAFFAS)
	);
	
	//Ranking geral: 
	//1o - OUTBACK(3 positivos, 0 negativos)
	//2o - MCDONALDS(3 positivos, 1 negativos)
	//3o - SUBWAY(2 positivos, 1 negativos)
	//4o - WENDYS(2 positivos, 2 negativos)
	//5o - GIRAFFAS(2 positivos, 2 negativos)
	private static final Collection<Voto> VOTOS_GERAL = 
			ImmutableSet.<Voto>builder().addAll(VOTOS_USUARIO_1).addAll(VOTOS_USUARIO_2).build();
	
	private UsuarioDao mockUsuarioDao;
	private VotoDao mockVotoDao;
	private VotacaoService service;
	
	@Before
	public void setup(){
		this.mockUsuarioDao = mock(UsuarioDao.class);
		this.mockVotoDao = mock(VotoDao.class);
		this.service = new VotacaoService(mockUsuarioDao, mockVotoDao);
	}
	
	
	@Test
	public void registrarVotoComSucesso(){
		//Executamos o método a ser testado
		boolean respostaObtida = this.service.registrarVoto(USUARIO_1, VOTOS_USUARIO_1);
		
		assertTrue(respostaObtida);
		verify(mockUsuarioDao).salvar(USUARIO_1);
		verify(mockVotoDao).salvarVotos(VOTOS_USUARIO_1);
	}
	
	@Test
	public void listarRankingGeral(){
		
		List<ItemRankingVotos> respostaEsperada = ImmutableList.of(
			new ItemRankingVotos(Restaurante.OUTBACK, 3, 5),
			new ItemRankingVotos(Restaurante.MCDONALDS, 8, 1)
		);
		
		
		Map<Restaurante, Map<Integer, Integer>> respostaDao = ImmutableMap.<Restaurante, Map<Integer, Integer>>of(
			Restaurante.OUTBACK, ImmutableMap.of(3, 5),
			Restaurante.MCDONALDS, ImmutableMap.of(8, 1)
		);		
		when(mockVotoDao.listarRankingGeral()).thenReturn(respostaDao);
		
		//Executamos o método a ser testado
		List<ItemRankingVotos> respostaObtida = service.listarRankingGeral();
		
		
		assertEquals(respostaEsperada, respostaObtida);
	}
	
	@Test
	public void listarRankingUsuario(){
		fail();
	}
}
