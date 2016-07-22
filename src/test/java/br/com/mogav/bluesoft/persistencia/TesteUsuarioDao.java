package br.com.mogav.bluesoft.persistencia;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import br.com.mogav.bluesoft.model.Usuario;

public class TesteUsuarioDao extends BaseTesteDAO<Usuario>{

	private List<Usuario> usuarios;
	
	private UsuarioDao dao;
	
	@Before
	public void setup(){
		this.usuarios = ImmutableList.of(
			new Usuario("Joao", "joao@email.com"),
			new Usuario("Pedro", "pedro@email.com")
		);
		this.dao = new UsuarioDao(this.entityManager);
	}
	
	@Override
	protected JPADao<Usuario> obterDAO() {
		return this.dao;
	}
	
	
	@Test
	public void salvarNovoUsuario(){		
		Usuario salvo = dao.salvar(usuarios.get(0));
		assertNotNull(salvo.getId());
	}
	
	@Test
	public void listarTodos(){
		dao.salvar(usuarios.get(0));
		dao.salvar(usuarios.get(1));
		
		CollectionUtils.isEqualCollection(usuarios, dao.listarTodos());
	}
	
	@Test
	public void buscarPorEmail(){		
		Usuario aSalvar = usuarios.get(0);	
		dao.salvar(aSalvar);

		assertEquals(aSalvar, dao.buscarPorEmail(aSalvar.getEmail()));
	}
}