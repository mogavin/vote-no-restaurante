package br.com.mogav.bluesoft.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Voto;
import br.com.mogav.bluesoft.model.VotoType;

import com.google.common.collect.ImmutableList;

public class TesteVotoDao {
	
	private static final List<Voto> VOTOS = ImmutableList.of(
			new Voto(VotoType.POSITIVO, Restaurante.OUTBACK),
			new Voto(VotoType.NEGATIVO, Restaurante.WENDYS)
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
	public void listarTodos(){
		dao.salvar(VOTOS.get(0));
		dao.salvar(VOTOS.get(1));
		
		CollectionUtils.isEqualCollection(VOTOS, dao.listarTodos());
	}

}