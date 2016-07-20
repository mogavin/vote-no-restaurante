package br.com.mogav.bluesoft.persistencia;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.persistencia.UsuarioDao;

public class TesteUsuarioDao {

	private static final List<Usuario> USUARIOS = ImmutableList.of(
			new Usuario("Joao", "joao@email.com"),
			new Usuario("Pedro", "pedro@email.com")
	);
	
	private UsuarioDao dao;
	
	@Before
	public void setup(){
		this.dao = new UsuarioDao();
	}
	
	
	@Test
	public void salvarNovoUsuario(){		
		Usuario salvo = dao.salvar(USUARIOS.get(0));
		assertNotNull(salvo.getId());
	}
	
	@Test
	public void listarTodos(){
		dao.salvar(USUARIOS.get(0));
		dao.salvar(USUARIOS.get(1));
		
		CollectionUtils.isEqualCollection(USUARIOS, dao.listarTodos());
	}
	
	@Test
	public void buscarPorEmail(){		
		Usuario aSalvar = USUARIOS.get(0);	
		dao.salvar(aSalvar);

		assertEquals(aSalvar, dao.buscarPorEmail(aSalvar.getEmail()));
	}
}