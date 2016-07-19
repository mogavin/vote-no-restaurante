package br.com.mogav.bluesoft.model;

public class Voto extends Persistivel{
	
	private final VotoType tipoVoto;
	private final Restaurante restaurante;
	
	/**
	 * Construtor padrão.
	 */
	public Voto(VotoType tipoVoto, Restaurante restaurante) {
		this(null, tipoVoto, restaurante);
	}
	
	/**
	 * Construtor para persistência.
	 */
	public Voto(Long id, VotoType tipoVoto, Restaurante restaurante) {
		super(id);
		this.tipoVoto = tipoVoto;
		this.restaurante = restaurante;
	}

	
	public VotoType getTipoVoto() {
		return this.tipoVoto;
	}

	public Restaurante getRestaurante() {
		return this.restaurante;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((restaurante == null) ? 0 : restaurante.hashCode());
		result = prime * result + ((tipoVoto == null) ? 0 : tipoVoto.hashCode());
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
		if (restaurante != other.restaurante)
			return false;
		if (tipoVoto != other.tipoVoto)
			return false;
		return true;
	}	
}