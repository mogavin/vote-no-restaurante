package br.com.mogav.bluesoft.persistencia;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

import br.com.mogav.bluesoft.model.ItemRankingVotos;
import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;

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

	
	public boolean registrarVoto(Usuario usuario, Collection<Voto> votos){		
		this.usuarioDao.salvar(usuario);
		this.votoDao.salvarVotos(votos);
		
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
	    Table<R, C, V> table = HashBasedTable.create();
	    for (R rowKey : fromTable.keySet())
	    {
	        Map<C, V> rowMap = fromTable.get(rowKey);
	        for (C columnKey : rowMap.keySet())
	        {
	            V value = rowMap.get(columnKey);
	            table.put(rowKey, columnKey, value);
	        }
	    }
	    return table;
	}
}