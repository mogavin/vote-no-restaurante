package br.com.mogav.bluesoft.persistencia;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;
import br.com.mogav.bluesoft.persistencia.VotoDao;

public class TesteVotoDao {
	
	private static final Usuario USUARIO_1 = new Usuario(1L, "Joao", "joao@email.com");
	private static final Usuario USUARIO_2 = new Usuario(2L, "Pedro", "pedro@email.com");
	
	//Ranking usuario 1: 
	//1o - OUTBACK(2 positivos, 0 negativos)
	//2o - SUBWAY(1 positivos, 0 negativos)
	//3o - GIRAFFAS(2 positivos, 1 negativos)
	//4o - MCDONALDS(1 positivos, 1 negativos)
	//5o - WENDYS(0 positivos, 1 negativos)
	private static final List<Voto> VOTOS_USUARIO_1 = ImmutableList.of(
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

	//Ranking usuario 2: 
	//1o - MCDONALDS(2 positivos, 0 negativos)
	//2o - OUTBACK(1 positivos, 0 negativos)
	//3o - WENDYS(2 positivos, 1 negativos)
	//4o - SUBWAY(1 positivos, 1 negativos)
	//5o - GIRAFFAS(0 positivos, 1 negativos)
	private static final Collection<Voto> VOTOS_USUARIO_2 = ImmutableSet.of(
			new Voto(USUARIO_2, true, Restaurante.OUTBACK),
			new Voto(USUARIO_2, true, Restaurante.WENDYS),
			new Voto(USUARIO_2, true, Restaurante.WENDYS),
			new Voto(USUARIO_2, false, Restaurante.WENDYS),
			new Voto(USUARIO_2, true, Restaurante.MCDONALDS),
			new Voto(USUARIO_2, true, Restaurante.MCDONALDS),
			new Voto(USUARIO_2, true, Restaurante.SUBWAY),
			new Voto(USUARIO_2, false, Restaurante.SUBWAY),
			new Voto(USUARIO_2, false, Restaurante.GIRAFFAS)
	);
	
	//Ranking geral: 
	//1o - OUTBACK(3 positivos, 0 negativos)
	//2o - MCDONALDS(3 positivos, 1 negativos)
	//3o - SUBWAY(2 positivos, 1 negativos)
	//4o - WENDYS(2 positivos, 2 negativos)
	//5o - GIRAFFAS(2 positivos, 2 negativos)
	private static final Collection<Voto> VOTOS_GERAL = 
			ImmutableSet.<Voto>builder().addAll(VOTOS_USUARIO_1).addAll(VOTOS_USUARIO_2).build();
	
	private VotoDao dao;
	
	@Before
	public void setup(){
		this.dao = new VotoDao();
	}
	
	
	@Test
	public void salvarNovoVoto(){		
		Voto salvo = dao.salvar(VOTOS_USUARIO_1.get(0));
		assertNotNull(salvo.getId());
	}
	
	@Test
	public void salvarMaisDeUmVoto(){		
		List<Voto> salvos = Lists.newArrayList(dao.salvarVotos(VOTOS_USUARIO_1));		
		
		if(salvos.isEmpty()) fail();		
		for(Voto salvo : salvos)
			assertNotNull(salvo.getId());
	}
	
	@Test
	public void listarTodos(){
		dao.salvarVotos(VOTOS_GERAL);
		
		CollectionUtils.isEqualCollection(VOTOS_GERAL, dao.listarTodos());
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