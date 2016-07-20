package br.com.mogav.bluesoft.model;

public class Voto extends Persistivel{
	
	private final Usuario usuario;
	private final boolean isPositivo;
	private final Restaurante restaurante;
	
	/**
	 * Construtor padrão.
	 */
	public Voto(Usuario usuario, boolean isPositivo, Restaurante restaurante) {
		this(null, usuario, isPositivo, restaurante);
	}
	
	/**
	 * Construtor para persistência.
	 */
	public Voto(Long id, Usuario usuario, boolean isPositivo, Restaurante restaurante) {
		super(id);
		this.usuario = usuario;
		this.isPositivo = isPositivo;
		this.restaurante = restaurante;
	}

	
	public Usuario getUsuario() {
		return this.usuario;
	}
	
	public boolean isPositivo() {
		return this.isPositivo;
	}

	public Restaurante getRestaurante() {
		return this.restaurante;
	}

	
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isPositivo ? 1231 : 1237);
		result = prime * result + ((restaurante == null) ? 0 : restaurante.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Voto))
			return false;
		Voto other = (Voto) obj;
		if (isPositivo != other.isPositivo)
			return false;
		if (restaurante != other.restaurante)
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
}