package br.com.mogav.bluesoft.persistencia;

import java.util.Collection;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.google.common.collect.Sets;

import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;

@RequestScoped
public class VotoDao extends JPADao<Voto>{

	@Inject
	public VotoDao(EntityManager em){
		super(em, Voto.class);
	}
	
	
	public Collection<Voto> salvarVotos(Collection<Voto> votos) {		
		Collection<Voto> salvos = Sets.newHashSet();		
		for(Voto aSalvar : votos){
			Voto salvo = this.salvar(aSalvar);
			salvos.add(salvo);
		}
		
		return salvos;
	}
		
	Map<Restaurante, Map<Integer, Integer>> listarRankingGeral() {
		throw new UnsupportedOperationException("Método não implementado, ainda");
	}
	
	Map<Restaurante, Map<Integer, Integer>> listarRankingUsuario(Usuario usuario) {
		throw new UnsupportedOperationException("Método não implementado, ainda");
	}
}