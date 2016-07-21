package br.com.mogav.bluesoft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario extends Persistivel{

	@Column(nullable = false)
	private final String nome;
	@Column(unique=true, nullable = false)
	private final String email;

	/**
	 * Construtor padrão.
	 */
	public Usuario(String nome, String email) {
		this(null, nome, email);
	}
	
	/**
	 * Construtor para persistência.
	 */
	Usuario(Long id, String nome, String email) {
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
		return true;
	}	
}
