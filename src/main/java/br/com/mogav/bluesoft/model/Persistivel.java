package br.com.mogav.bluesoft.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Atribui um id à classe para que seja persistivel.
 * 
 * @author mogav
 *
 */
@MappedSuperclass
public abstract class Persistivel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected final Long id;
		
	/**
	 * @deprecated JPA eyes only
	 */
	Persistivel(){
		this(null);
	}
	
	Persistivel(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
}