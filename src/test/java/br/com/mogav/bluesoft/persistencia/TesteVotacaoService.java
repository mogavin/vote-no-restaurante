package br.com.mogav.bluesoft.persistencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
		boolean respostaObtida = this.service.registrarVotos(USUARIO_CADASTRADO, VOTOS_NAO_REGISTRADOS);
		
		assertTrue(respostaObtida);
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
		
		//Deve manter a ordem dos valores retornados de 'respostaDao'
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
		
		//Deve manter a ordem dos valores retornados de 'respostaDao'
		List<ItemRankingVotos> respostaEsperada = ImmutableList.of(
			new ItemRankingVotos(Restaurante.OUTBACK, 3, 5),
			new ItemRankingVotos(Restaurante.MCDONALDS, 8, 1)
		);
		
		
		Map<Restaurante, Map<Integer, Integer>> respostaDao = ImmutableMap.<Restaurante, Map<Integer, Integer>>of(
			Restaurante.OUTBACK, ImmutableMap.of(3, 5),
			Restaurante.MCDONALDS, ImmutableMap.of(8, 1)
		);
		when(mockUsuarioDao.buscarPorEmail(USUARIO_CADASTRADO.getEmail())).thenReturn(USUARIO_CADASTRADO);
		when(mockVotoDao.listarRankingUsuario(USUARIO_CADASTRADO)).thenReturn(respostaDao);
		
		//Executamos o método a ser testado
		List<ItemRankingVotos> respostaObtida = service.listarRankingUsuario(USUARIO_CADASTRADO);
		
		//Asseguramos que usuarios cadastrados nao sejam salvos novamente
		verify(mockUsuarioDao, never()).salvar(USUARIO_CADASTRADO);
		assertEquals(respostaEsperada, respostaObtida);
	}	
}
