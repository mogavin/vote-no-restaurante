package br.com.mogav.bluesoft.persistencia;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;

public class TesteVotacaoService {

	private static final Usuario USUARIO_1 = new Usuario("Joao", "joao@email.com");
	private static final Usuario USUARIO_2 = new Usuario("Pedro", "pedro@email.com");
	
	//Ranking por usuario individual: 
	//1o - OUTBACK(2 positivos, 0 negativos)
	//2o - SUBWAY(1 positivos, 0 negativos)
	//3o - GIRAFFAS(2 positivos, 1 negativos)
	//4o - MCDONALDS(1 positivos, 1 negativos)
	//5o - WENDYS(0 positivos, 1 negativo)
	private static final List<Voto> VOTOS_USUARIO_INDIVIDUAL = ImmutableList.of(
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
		boolean respostaObtida = this.service.registrarVoto(USUARIO_1, VOTOS_USUARIO_INDIVIDUAL);
		
		assertTrue(respostaObtida);
		verify(mockUsuarioDao).salvar(USUARIO_1);
		verify(mockVotoDao).salvarVotos(VOTOS_USUARIO_INDIVIDUAL);
	}
	
	@Test
	public void listarRankingGeral(){
		fail();
	}
	
	@Test
	public void listarRankingUsuario(){
		fail();
	}
}
