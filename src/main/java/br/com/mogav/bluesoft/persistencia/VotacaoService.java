package br.com.mogav.bluesoft.persistencia;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.mogav.bluesoft.model.ItemRankingVotos;
import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.ImmutableTable.Builder;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

@RequestScoped
public class VotacaoService {
	
	private final UsuarioDao usuarioDao;
	private final VotoDao votoDao;
	
	 /**
     * @deprecated CDI eyes only
     */
	VotacaoService(){
    	this(null, null);
    }

	@Inject
	public VotacaoService(UsuarioDao usuarioDao, VotoDao votoDao) {
		this.usuarioDao = usuarioDao;
		this.votoDao = votoDao;
	}

	
	public boolean registrarVotos(Usuario usuario, Collection<Voto> votos){
		
		//Se for usuário novo, salva
		Usuario encontrado = this.usuarioDao.buscarPorEmail(usuario.getEmail());
		encontrado = (encontrado == null) ? this.usuarioDao.salvar(usuario) : encontrado;
		
		Collection<Voto> votosRegistrados = Sets.newHashSet();
		
		for(Voto voto : votos)
			votosRegistrados.add(new Voto(encontrado, voto.isPositivo(), voto.getRestaurante()));			

		this.votoDao.salvarVotos(votosRegistrados);
		
		return true;		
	}
	
	public List<ItemRankingVotos> listarRankingGeral() {		
		Map<Restaurante, Map<Integer, Integer>> respostaDao = this.votoDao.listarRankingGeral();
		List<ItemRankingVotos> rankingGeral = VotacaoService.obterItemsRanking(respostaDao);
		
		return rankingGeral;
	}
	
	public List<ItemRankingVotos> listarRankingUsuario(Usuario usuario) {		
		Map<Restaurante, Map<Integer, Integer>> respostaDao = this.votoDao.listarRankingUsuario(usuario);
		List<ItemRankingVotos> rankingUsuario = VotacaoService.obterItemsRanking(respostaDao);
		
		return rankingUsuario;
	}
	
	
	
	/**
	 * Converte um mapa de mapas de votos por restaurante em uma lista de instências de 'ItemRankingVotos'.
	 * 
	 */
	private static List<ItemRankingVotos> obterItemsRanking
					(Map<Restaurante, Map<Integer, Integer>> mapaItemsRanking){
		
		List<ItemRankingVotos> rankingGeral = Lists.newArrayList();		
	
		Table<Restaurante, Integer, Integer> tableRespostaDao = VotacaoService.table(mapaItemsRanking);		
		for (Cell<Restaurante, Integer, Integer> cell: tableRespostaDao.cellSet()){
			Restaurante restaurante = cell.getRowKey();
			Integer qtdVotosPositivos = cell.getColumnKey();
			Integer qtdVotosNegativos = cell.getValue();

			rankingGeral.add(new ItemRankingVotos(restaurante, qtdVotosPositivos, qtdVotosNegativos));
		}
		
		return rankingGeral;
	}
	
	/**
	 * Converte um mapa de mapas em uma instância 'Table', do Guava.
	 * 
	 */
	private static <R, C, V> Table<R, C, V> table(Map<R, Map<C, V>> fromTable)
	{
		Builder<R, C, V> tableBuilder = ImmutableTable.builder();
	    for (R rowKey : fromTable.keySet())
	    {
	        Map<C, V> rowMap = fromTable.get(rowKey);
	        for (C columnKey : rowMap.keySet())
	        {
	            V value = rowMap.get(columnKey);
	            tableBuilder.put(rowKey, columnKey, value);
	        }
	    }
	    return tableBuilder.build();
	}
}