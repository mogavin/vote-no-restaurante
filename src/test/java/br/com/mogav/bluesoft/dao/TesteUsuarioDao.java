package br.com.mogav.bluesoft.dao;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import br.com.mogav.bluesoft.model.Usuario;

public class TesteUsuarioDao {
	
	private static final Collection<Usuario> USUARIOS = Arrays.<Usuario>asList(
			new Usuario("Joao", "joao@email.com"),
			new Usuario("Pedro", "pedro@email.com")
	);
	private UsuarioDao dao;
	
	@Before
	public void setup(){
		this.dao = new UsuarioDao();
	}
	
	
	@Test
	public void listarTodos(){		
		dao.salvar(USUARIOS.get(0));
		dao.salvar(USUARIOS.get(1));
	}
}
