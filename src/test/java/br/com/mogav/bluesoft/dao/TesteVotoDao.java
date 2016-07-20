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
	
	private VotoDao dao;
	
	@Before
	public void setup(){
		this.dao = new VotoDao();
	}
	
	
	@Test
	public void salvarNovoVoto(){		
		Voto salvo = dao.salvar(VOTOS_USUARIO_INDIVIDUAL.get(0));
		assertNotNull(salvo.getId());
	}
	
	@Test
	public void salvarMaisDeUmVoto(){		
		List<Voto> salvos = Lists.newArrayList(dao.salvarVotos(VOTOS_USUARIO_INDIVIDUAL));		
		
		if(salvos.isEmpty()) fail();		
		for(Voto salvo : salvos)
			assertNotNull(salvo.getId());
	}
	
	@Test
	public void listarTodos(){
		dao.salvarVotos(VOTOS_USUARIO_INDIVIDUAL);
		
		CollectionUtils.isEqualCollection(VOTOS_USUARIO_INDIVIDUAL, dao.listarTodos());
	}
	
	@Test
	public void listarRankingUsuario(){
		fail();
	}
}