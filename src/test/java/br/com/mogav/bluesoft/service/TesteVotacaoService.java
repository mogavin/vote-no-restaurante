package br.com.mogav.bluesoft.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import br.com.mogav.bluesoft.dao.UsuarioDao;
import br.com.mogav.bluesoft.dao.VotoDao;
import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;

public class TesteVotacaoService {

	private static final Usuario USUARIO = new Usuario("Joao", "joao@email.com");
	private static final Collection<Voto> VOTOS = ImmutableList.of(
			new Voto(USUARIO, true, Restaurante.OUTBACK),
			new Voto(USUARIO, false, Restaurante.WENDYS)
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
		boolean respostaObtida = this.service.registrarVoto(USUARIO, VOTOS);
		
		assertTrue(respostaObtida);
		verify(mockUsuarioDao).salvar(USUARIO);
		verify(mockVotoDao).salvarVotos(VOTOS);
	}	
}
