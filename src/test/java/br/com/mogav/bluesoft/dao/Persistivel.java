package br.com.mogav.bluesoft.dao;

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
	//@Deprecated
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