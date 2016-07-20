package br.com.mogav.bluesoft.model;

/**
 * Atribui um id à classe para que seja persistivel.
 * 
 * @author mogav
 *
 */
public abstract class Persistivel {

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