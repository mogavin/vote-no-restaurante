package br.com.mogav.bluesoft.persistencia;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.mogav.bluesoft.model.Usuario;

@RequestScoped
public class UsuarioDao extends JPADao<Usuario>{							
	
	/**
     * @deprecated CDI eyes only
     */
	UsuarioDao(){
    	this(null);
    }
	
	@Inject
	public UsuarioDao(EntityManager em){
		super(em, Usuario.class);
	}
	
	
	public Usuario buscarPorEmail(String email) {		
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		
		try{
			CriteriaQuery<Usuario> q = cb.createQuery(Usuario.class);
			Root<Usuario> from = q.from(Usuario.class);
			TypedQuery<Usuario> typedQuery = this.em.createQuery(
				    q.select(from)
				    	.where(cb.equal(from.get("email"), email))
			);			
			return typedQuery.getSingleResult();
		}catch(RuntimeException e){
			this.log.error(e);
			return null;
		}
	}
}
