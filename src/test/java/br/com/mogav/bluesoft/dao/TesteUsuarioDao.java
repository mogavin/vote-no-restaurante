
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import br.com.mogav.bluesoft.dao.UsuarioDao;
import br.com.mogav.bluesoft.model.Usuario;

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
}