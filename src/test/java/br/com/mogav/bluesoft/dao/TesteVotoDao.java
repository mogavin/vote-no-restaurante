package br.com.mogav.bluesoft.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;

public class TesteVotoDao {
	
	private static final Usuario USUARIO = new Usuario("Joao", "joao@email.com");
	private static final List<Voto> VOTOS = ImmutableList.of(
			new Voto(USUARIO, true, Restaurante.OUTBACK),
			new Voto(USUARIO, false, Restaurante.GIRAFFAS)
	);
	
	private VotoDao dao;
	
	@Before
	public void setup(){
		this.dao = new VotoDao();
	}
	
	
	@Test
	public void salvarNovoVoto(){		
		Voto salvo = dao.salvar(VOTOS.get(0));
		assertNotNull(salvo.getId());
	}
	
	@Test
	public void salvarMaisDeUmVoto(){		
		List<Voto> salvos = Lists.newArrayList(dao.salvarVotos(VOTOS));		
		
		if(salvos.isEmpty()) fail();		
		for(Voto salvo : salvos)
			assertNotNull(salvo.getId());
	}
	
	@Test
	public void listarTodos(){
		dao.salvarVotos(VOTOS);
		
		CollectionUtils.isEqualCollection(VOTOS, dao.listarTodos());
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