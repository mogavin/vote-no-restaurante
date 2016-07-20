package br.com.mogav.bluesoft.persistencia;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

	private static final Usuario USUARIO_CADASTRADO = new Usuario(1L, "Joao", "joao@email.com");
	
	private static final Collection<Voto> VOTOS = ImmutableSet.of(
			new Voto(USUARIO_CADASTRADO, true, Restaurante.OUTBACK),
			new Voto(USUARIO_CADASTRADO, false, Restaurante.SUBWAY)
	);	
	
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
		boolean respostaObtida = this.service.registrarVoto(USUARIO_CADASTRADO, VOTOS);
		
		assertTrue(respostaObtida);
		verify(mockUsuarioDao).salvar(USUARIO_CADASTRADO);
		verify(mockVotoDao).salvarVotos(VOTOS);
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
		
		List<ItemRankingVotos> respostaEsperada = ImmutableList.of(
			new ItemRankingVotos(Restaurante.OUTBACK, 3, 5),
			new ItemRankingVotos(Restaurante.MCDONALDS, 8, 1)
		);
		
		
		Map<Restaurante, Map<Integer, Integer>> respostaDao = ImmutableMap.<Restaurante, Map<Integer, Integer>>of(
			Restaurante.OUTBACK, ImmutableMap.of(3, 5),
			Restaurante.MCDONALDS, ImmutableMap.of(8, 1)
		);		
		when(mockVotoDao.listarRankingUsuario(USUARIO_CADASTRADO)).thenReturn(respostaDao);
		
		//Executamos o método a ser testado
		List<ItemRankingVotos> respostaObtida = service.listarRankingUsuario(USUARIO_CADASTRADO);
		
		//Asseguramos que usuarios cadastrados nao sejam salvos novamente
		verify(mockUsuarioDao, never()).salvar(USUARIO_CADASTRADO);
		assertEquals(respostaEsperada, respostaObtida);
	}
	
	@Test
	public void seUsuarioForNovoDeveCadastrarAntasDeListarOSeuRanking(){
		Usuario novo = new Usuario("Pedro", "pedro@email.com");//Sem id
		
		//Executamos o método a ser testado
		service.listarRankingUsuario(novo);
		
		verify(mockUsuarioDao).salvar(novo);
	}
}
