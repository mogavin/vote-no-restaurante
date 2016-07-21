package br.com.mogav.bluesoft.persistencia;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;

@RequestScoped
public class VotoDao extends JPADao<Voto>{
	
	/**
     * @deprecated CDI eyes only
     */
	VotoDao(){
    	this(null);
    }

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
		return listarRanking();		
	}
	
	Map<Restaurante, Map<Integer, Integer>> listarRankingUsuario(Usuario usuario) {		
		return listarRanking(usuario);
	}
	
	
	private Map<Restaurante, Map<Integer, Integer>> listarRanking() {
		return this.listarRanking(null);
	}
	private Map<Restaurante, Map<Integer, Integer>> listarRanking(Usuario usuario) {
		Map<Restaurante, Integer> votosPositivos = this.obterSomaDeVotos(true, usuario);
		Map<Restaurante, Integer> votosNegativos = this.obterSomaDeVotos(false, usuario);		
		
		return montarTabelaResultados(votosPositivos, votosNegativos);
	}
	
		
	private Map<Restaurante, Integer> obterSomaDeVotos(boolean positivos, Usuario usuario){
		
		String jpql = "select v.restaurante, COUNT(v) from Voto v where v.isPositivo = :isPositivo {U} group by v.restaurante, v.isPositivo";
		jpql = (usuario != null) ? jpql.replace("{U}", "and v.usuario.id = " + usuario.getId()) 
								 : jpql.replace("{U}", "");
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultPositivos= this.em
				.createQuery(jpql)
				.setParameter("isPositivo", positivos)
		        .getResultList();
		
		Map<Restaurante, Integer> qtdVotos = Maps.newHashMap();
		for(Object object[] : resultPositivos){
			qtdVotos.put((Restaurante)object[0], ((Long)object[1]).intValue());
		}
		
		return qtdVotos;
	}
	
	private Map<Restaurante, Map<Integer, Integer>> montarTabelaResultados
														(Map<Restaurante, Integer> votosPositivos,
															Map<Restaurante, Integer> votosNegativos){
		Table<Restaurante, Integer, Integer> rankingGeral = HashBasedTable.create();
		
		Collection<Restaurante> restaurantes = Sets.newHashSet();
		restaurantes.addAll(votosPositivos.keySet()); restaurantes.addAll(votosNegativos.keySet());
		
		for(Restaurante restaurante : restaurantes){
			Integer qtdVotosPositivos = votosPositivos.get(restaurante);
			qtdVotosPositivos = (qtdVotosPositivos == null) ? 0 : qtdVotosPositivos;
			Integer qtdVotosNegativos = votosNegativos.get(restaurante);
			qtdVotosNegativos = (qtdVotosNegativos == null) ? 0 : qtdVotosNegativos;
			
			rankingGeral.put(restaurante, qtdVotosPositivos, qtdVotosNegativos);
		}
		
		return rankingGeral.rowMap();
	}
}