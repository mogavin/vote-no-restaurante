package br.com.mogav.bluesoft.model;

public class Voto extends Persistivel{
	
	private final boolean isPositivo;
	private final Restaurante restaurante;
	
	/**
	 * Construtor padrão.
	 */
	public Voto(boolean isPositivo, Restaurante restaurante) {
		this(null, isPositivo, restaurante);
	}
	
	/**
	 * Construtor para persistência.
	 */
	public Voto(Long id, boolean isPositivo, Restaurante restaurante) {
		super(id);
		this.isPositivo = isPositivo;
		this.restaurante = restaurante;
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
		return true;
	}		
}