package br.com.mogav.bluesoft.persistencia;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import br.com.mogav.bluesoft.model.ItemRankingVotos;
import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;

public class TesteVotacaoService {

	private static final Usuario USUARIO_CADASTRADO = new Usuario("Joao", "joao@email.com");
	
	private static final List<Voto> VOTOS_NAO_REGISTRADOS = ImmutableList.of(
			new Voto(null, true, Restaurante.OUTBACK),
			new Voto(null, false, Restaurante.SUBWAY)
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
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void registrarNovosVotosDeUsuariosJaRegistrados(){
		
		//Mocks necessários para o teste
		when(mockUsuarioDao.buscarPorEmail(USUARIO_CADASTRADO.getEmail())).thenReturn(USUARIO_CADASTRADO);
		@SuppressWarnings("rawtypes")
		ArgumentCaptor<Collection> argumentCaptor = ArgumentCaptor.forClass(Collection.class);
		//Capturamos os votos salvos para verificação
		when(mockVotoDao.salvarVotos(argumentCaptor.capture())).thenReturn(Collections.<Voto>emptySet());
		
		//Executamos o método a ser testado
		Usuario respostaObtida = this.service.registrarVotos(USUARIO_CADASTRADO, VOTOS_NAO_REGISTRADOS);
		
		assertEquals(respostaObtida, USUARIO_CADASTRADO);
		verify(mockUsuarioDao, never()).salvar(USUARIO_CADASTRADO);
		
		//Asseguramos que todos os votos foram registrados com o usuário
		for(Object voto : argumentCaptor.getValue()){
			assertEquals(USUARIO_CADASTRADO, ((Voto)voto).getUsuario());
		}		
	}
	
	@Test
	public void seUsuarioForNovoDeveSalvarAntesDeRegistrarOsVotos(){
		Usuario novo = new Usuario("Pedro", "pedro@email.com");//Sem id
		
		when(mockUsuarioDao.buscarPorEmail(novo.getEmail())).thenReturn(null);
		
		//Executamos o método a ser testado
		service.registrarVotos(novo, VOTOS_NAO_REGISTRADOS);
		
		//Asseguramos que o novo usuario tenha sido salvo
		verify(mockUsuarioDao).salvar(novo);
	}
	
	@Test
	public void listarRankingGeral(){
		
		//Ordenação esperada
		List<ItemRankingVotos> respostaEsperada = ImmutableList.of(
			new ItemRankingVotos(Restaurante.OUTBACK, 3, 0),
			new ItemRankingVotos(Restaurante.MCDONALDS, 3, 1),
			new ItemRankingVotos(Restaurante.SUBWAY, 2, 1),
			new ItemRankingVotos(Restaurante.WENDYS, 2, 2),
			new ItemRankingVotos(Restaurante.GIRAFFAS, 2, 2)
		);
		
		//Resultado do DAO desordenado		
		Map<Restaurante, Map<Integer, Integer>> respostaDao = ImmutableMap.<Restaurante, Map<Integer, Integer>>of(
			Restaurante.OUTBACK, ImmutableMap.of(3, 0),
			Restaurante.WENDYS, ImmutableMap.of(2, 2),
			Restaurante.SUBWAY, ImmutableMap.of(2, 1),
			Restaurante.MCDONALDS, ImmutableMap.of(3, 1),
			Restaurante.GIRAFFAS, ImmutableMap.of(2, 2)
		);		
		when(mockVotoDao.obterDadosRankingGeral()).thenReturn(respostaDao);
		
		//Executamos o método a ser testado
		List<ItemRankingVotos> respostaObtida = service.listarRankingGeral();
		
		
		assertEquals(respostaEsperada, respostaObtida);
	}
	
	@Test
	public void listarRankingUsuario(){
		
		//Ordenação esperada
		List<ItemRankingVotos> respostaEsperada = ImmutableList.of(
			new ItemRankingVotos(Restaurante.OUTBACK, 2, 0),
			new ItemRankingVotos(Restaurante.GIRAFFAS, 2, 1),
			new ItemRankingVotos(Restaurante.SUBWAY, 1, 0),
			new ItemRankingVotos(Restaurante.MCDONALDS, 1, 1),
			new ItemRankingVotos(Restaurante.WENDYS, 0, 1)
		);		
		
		//Resultado do DAO desordenado
		Map<Restaurante, Map<Integer, Integer>> respostaDao = ImmutableMap.<Restaurante, Map<Integer, Integer>>of(
			Restaurante.GIRAFFAS, ImmutableMap.of(2, 1),
			Restaurante.OUTBACK, ImmutableMap.of(2, 0),
			Restaurante.MCDONALDS, ImmutableMap.of(1, 1),
			Restaurante.WENDYS, ImmutableMap.of(0, 1),
			Restaurante.SUBWAY, ImmutableMap.of(1, 0)
		);
		when(mockUsuarioDao.buscarPorEmail(USUARIO_CADASTRADO.getEmail())).thenReturn(USUARIO_CADASTRADO);
		when(mockVotoDao.obterDadosRankingUsuario(USUARIO_CADASTRADO)).thenReturn(respostaDao);
		
		//Executamos o método a ser testado
		List<ItemRankingVotos> respostaObtida = service.listarRankingUsuario(USUARIO_CADASTRADO);
		
		//Asseguramos que usuarios cadastrados nao sejam salvos novamente
		verify(mockUsuarioDao, never()).salvar(USUARIO_CADASTRADO);
		assertEquals(respostaEsperada, respostaObtida);
	}	
}