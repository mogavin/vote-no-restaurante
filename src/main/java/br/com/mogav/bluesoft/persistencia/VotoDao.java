package br.com.mogav.bluesoft.persistencia;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.enterprise.context.RequestScoped;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;

@RequestScoped
public class VotoDao implements Dao<Voto>{

	private static Long CHAVE_DISPONIVEL = 1L;
	private static final Map<Long, Voto> TABELA = Maps.newHashMap();
										
	
	public Voto salvar(Voto voto) {
		Voto aSalvar = new Voto(CHAVE_DISPONIVEL, voto.getUsuario(), voto.isPositivo(), voto.getRestaurante());
		TABELA.put(CHAVE_DISPONIVEL, aSalvar);
		CHAVE_DISPONIVEL++;
		
		return aSalvar;
	}
	
	public Collection<Voto> salvarVotos(Collection<Voto> votos) {		
		Collection<Voto> salvos = Sets.newHashSet();		
		for(Voto aSalvar : votos){
			Voto salvo = this.salvar(aSalvar);
			salvos.add(salvo);
		}
		
		return salvos;
	}

	public Collection<Voto> listarTodos() {
		return Collections.unmodifiableCollection(TABELA.values());
	}
		
	Map<Restaurante, Map<Integer, Integer>> listarRankingGeral() {
		throw new UnsupportedOperationException("Método não implementado, ainda");
	}
	
	Map<Restaurante, Map<Integer, Integer>> listarRankingUsuario(Usuario usuario) {
		throw new UnsupportedOperationException("Método não implementado, ainda");
	}
}