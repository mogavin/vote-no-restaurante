package br.com.mogav.bluesoft.persistencia;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.enterprise.context.RequestScoped;

import com.google.common.collect.Maps;

import br.com.mogav.bluesoft.model.Usuario;

@RequestScoped
public class UsuarioDao implements Dao<Usuario>{

	private static Long CHAVE_DISPONIVEL = 1L;
	private static final Map<Long, Usuario> TABELA = Maps.newHashMap();
										
	
	public Usuario salvar(Usuario usuario) {
		Usuario aSalvar = new Usuario(CHAVE_DISPONIVEL, usuario.getNome(), usuario.getEmail());
		TABELA.put(CHAVE_DISPONIVEL, aSalvar);
		CHAVE_DISPONIVEL++;
		
		return aSalvar;
	}

	public Collection<Usuario> listarTodos() {
		return Collections.unmodifiableCollection(TABELA.values());
	}
}
