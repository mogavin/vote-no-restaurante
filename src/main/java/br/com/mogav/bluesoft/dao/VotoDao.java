package br.com.mogav.bluesoft.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import br.com.mogav.bluesoft.model.Voto;

import com.google.common.collect.Maps;

public class VotoDao implements Dao<Voto>{

	private static Long CHAVE_DISPONIVEL = 1L;
	private static final Map<Long, Voto> TABELA = Maps.newHashMap();
										
	
	public Voto salvar(Voto voto) {
		Voto aSalvar = new Voto(CHAVE_DISPONIVEL, voto.isPositivo(), voto.getRestaurante());
		TABELA.put(CHAVE_DISPONIVEL, aSalvar);
		CHAVE_DISPONIVEL++;
		
		return aSalvar;
	}

	public Collection<Voto> listarTodos() {
		return Collections.unmodifiableCollection(TABELA.values());
	}

}