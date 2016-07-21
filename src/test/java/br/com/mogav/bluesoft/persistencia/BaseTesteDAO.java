package br.com.mogav.bluesoft.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import br.com.mogav.bluesoft.model.Persistivel;


public abstract class BaseTesteDAO <T extends Persistivel>{

	private static EntityManagerFactory emFactory;
	
	protected EntityManager entityManager;
	private EntityTransaction entityTransaction;
	
	@BeforeClass
	public static void openEMF(){
		if(emFactory == null)
			emFactory = Persistence.createEntityManagerFactory("default", PersistenceUnitForTest.getValues());
	}
	
	@Before
	public void iniciaSessaoETransaction(){
		
		this.entityManager = emFactory.createEntityManager();
		
		//Controle do entityManager e da transação é externo ao DAO
		this.entityTransaction = this.entityManager.getTransaction();
		//Iniciamos uma transação antes de cada teste
		this.entityTransaction.begin();
	}
	
	@After
	public void apagaDadosEFinalizaSessaoETransaction(){
		
		//Finalizamos a transação após o teste
		this.entityTransaction.commit();
		
		//Abrimos e fechamos uma transação para apagar os dados das tabelas envolvidas no teste
		this.entityTransaction.begin();		
		this.obterDAO().apagarTodos();		
		this.entityTransaction.commit();
		
		this.entityManager.close();
	}
	
	protected abstract JPADao<T> obterDAO();
}
