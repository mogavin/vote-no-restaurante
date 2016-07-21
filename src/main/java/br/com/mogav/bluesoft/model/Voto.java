package br.com.mogav.bluesoft.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="voto")
public class Voto extends Persistivel{
	
	@OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
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
}