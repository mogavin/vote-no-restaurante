package br.com.mogav.bluesoft.persistencia;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

import br.com.mogav.bluesoft.model.ItemRankingVotos;
import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;

public class VotacaoService {
	
	private final UsuarioDao usuarioDao;
	private final VotoDao votoDao;

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
		
		List<ItemRankingVotos> rankingGeral = Lists.newArrayList();
		
		Map<Restaurante, Map<Integer, Integer>> respostaDao = this.votoDao.listarRankingGeral();
		
		Table<Restaurante, Integer, Integer> tableRespostaDao = VotacaoService.table(respostaDao);		
		for (Cell<Restaurante, Integer, Integer> cell: tableRespostaDao.cellSet()){
			Restaurante restaurante = cell.getRowKey();
			Integer qtdVotosPositivos = cell.getColumnKey();
			Integer qtdVotosNegativos = cell.getValue();

			rankingGeral.add(new ItemRankingVotos(restaurante, qtdVotosPositivos, qtdVotosNegativos));
		}
		
		return rankingGeral;
	}
	
	public List<ItemRankingVotos> listarRankingUsuario(Usuario usuario) {
		throw new UnsupportedOperationException("Método não implementado, ainda");
	}
	
	
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
