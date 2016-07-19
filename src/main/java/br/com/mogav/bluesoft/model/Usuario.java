package br.com.mogav.bluesoft.model;



public class Usuario extends Persistivel{

	private final String nome;
	private final String email;
	

	/**
	 * Construtor padrão.
	 */
	public Usuario(String nome, String email) {
		this(null, nome, nome);
	}
	
	/**
	 * Construtor para persistência.
	 */
	public Usuario(Long id, String nome, String email) {
		super(id);
		this.nome = nome;
		this.email = email;
	}
	

	public String getNome() {
		return this.nome;
	}

	public String getEmail() {
		return this.email;
	}


	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}


	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Usuario))
			return false;
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
