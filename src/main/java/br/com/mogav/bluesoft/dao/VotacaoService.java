package br.com.mogav.bluesoft.dao;

import java.util.Collection;

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
	
	public Collection<Voto> listarRankingGeral() {
		throw new UnsupportedOperationException("Método não implementado, ainda");
	}
	
	public Collection<Voto> listarRankingUsuario(Usuario usuario) {
		throw new UnsupportedOperationException("Método não implementado, ainda");
	}
}
