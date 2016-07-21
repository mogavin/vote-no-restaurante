package br.com.mogav.bluesoft.persistencia;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import br.com.mogav.bluesoft.model.Persistivel;


public class JPADao<T extends Persistivel> implements Dao<T> {
	
	protected final EntityManager em;
	protected final Class<T> persistentClass;
	protected final Logger log;
	
	public JPADao(EntityManager em, Class<T> persistentClass){
		this.em = em;
		this.persistentClass = persistentClass;
		this.log = Logger.getLogger(persistentClass);
	}
	
	@Override
	public T salvar(T entity) {
		try{
			this.em.persist(entity);			
			return entity;
		}catch(RuntimeException e){
			this.log.error(e);
			return null;
		}
	}

	@Override
	public Collection<T> listarTodos() {
		
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		
		try{
			CriteriaQuery<T> q = cb.createQuery(this.persistentClass);
			Root<T> c = q.from(this.persistentClass);
			TypedQuery<T> tq = em.createQuery(q.select(c));
			return tq.getResultList();
		}catch(RuntimeException e){
			this.log.error(e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public boolean apagarTodos() {
		
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		try{
			CriteriaDelete<T> delete = cb.createCriteriaDelete(this.persistentClass);
			delete.from(this.persistentClass);
			this.em.createQuery(delete).executeUpdate();
			return true;
		}catch(RuntimeException e){
			this.log.error(e);
			return false;
		}
	}
}