package br.com.mogav.bluesoft.persistencia;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
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
	
	List<Restaurante> obterRanking_2(Usuario usuario){
		
		List<Restaurante> ranking = Lists.newArrayList();
		
		String jpql = "select v1.restaurante, COUNT(v1) as votosPositivos, "
							+ "(select COUNT(v2.isPositivo) from Voto v2 where v2.isPositivo = false and v2.restaurante = v1.restaurante {U2}) as votosNegativos "
						+ "from Voto v1 where v1.isPositivo = true {U1} group by restaurante, v1.isPositivo "
						+ "order by votosPositivos desc, votosNegativos asc";


		if(usuario != null){
			jpql = jpql.replace("{U1}", "and v1.usuario.id = {ID}"); 
			jpql = jpql.replace("{U2}", "and v2.usuario.id = {ID}");
			jpql = jpql.replace("{ID}", usuario.getId().toString());
		}else{
			jpql = jpql.replace("{U1}", "");
			jpql = jpql.replace("{U2}", "");
		}
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultados= this.em
						.createQuery(jpql)
				        .getResultList();
		
		for(Object object[] : resultados){
			ranking.add((Restaurante)object[0]);
		}
		
		//Necessário para incluir restaurantes com votos zerados
		@SuppressWarnings("unchecked")
		List<Restaurante> votosZerados = 
				ListUtils.subtract(Arrays.asList(Restaurante.values()), ranking);
		ranking.addAll(votosZerados);
		
		return ranking;
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