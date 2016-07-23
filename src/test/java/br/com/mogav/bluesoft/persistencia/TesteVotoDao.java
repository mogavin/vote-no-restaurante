package br.com.mogav.bluesoft.persistencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;

//Não herda de BaseTesteDAO, pois precisa controlar mais de um DAO para efetuar os testes
public class TesteVotoDao{
	
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	
	private Usuario usuario_1;
	private Usuario usuario_2;
	
	private List<Voto> votosUsuario_1;
	private Collection<Voto> votosUsuario_2;
	private Collection<Voto> votosGeral;
	
	private UsuarioDao usuarioDao;
	private VotoDao votoDao;
	
	@Before
	public void setup(){
		
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("default", PersistenceUnitForTest.getValues());
		this.entityManager = emf.createEntityManager();
		
		//Inicializamos os DAO's		
		this.usuarioDao = new UsuarioDao(this.entityManager);
		this.votoDao = new VotoDao(this.entityManager);
		
		this.usuario_1 = new Usuario("Joao", "joao@email.com");
		this.usuario_2 = new Usuario("Pedro", "pedro@email.com");
		
		//Controle do entityManager e da transação é externo ao DAO
		this.entityTransaction = this.entityManager.getTransaction();
		//Delimitamos uma transação somente para salvar as entidades vinculadas
		this.entityTransaction.begin();
		
		this.usuarioDao.salvar(usuario_1);
		this.usuarioDao.salvar(usuario_2);
		
		//Fim da transação para salvar as entidades vinculadas
		this.entityTransaction.commit();
		
		
		this.votosUsuario_1 = ImmutableList.of(
				new Voto(usuario_1, true, Restaurante.OUTBACK),
				new Voto(usuario_1, true, Restaurante.OUTBACK),
				new Voto(usuario_1, false, Restaurante.WENDYS),
				new Voto(usuario_1, false, Restaurante.MCDONALDS),
				new Voto(usuario_1, true, Restaurante.MCDONALDS),
				new Voto(usuario_1, true, Restaurante.SUBWAY),
				new Voto(usuario_1, false, Restaurante.GIRAFFAS),
				new Voto(usuario_1, true, Restaurante.GIRAFFAS),
				new Voto(usuario_1, true, Restaurante.GIRAFFAS)
		);	

		this.votosUsuario_2 = ImmutableSet.of(
				new Voto(usuario_2, true, Restaurante.OUTBACK),
				new Voto(usuario_2, true, Restaurante.WENDYS),
				new Voto(usuario_2, true, Restaurante.WENDYS),
				new Voto(usuario_2, false, Restaurante.WENDYS),
				new Voto(usuario_2, true, Restaurante.MCDONALDS),
				new Voto(usuario_2, true, Restaurante.MCDONALDS),
				new Voto(usuario_2, true, Restaurante.SUBWAY),
				new Voto(usuario_2, false, Restaurante.SUBWAY),
				new Voto(usuario_2, false, Restaurante.GIRAFFAS)
		);
		
		this.votosGeral = ImmutableSet.<Voto>builder().addAll(votosUsuario_1).addAll(votosUsuario_2).build();
		
		//Delimitamos uma nova transação antes de cada teste
		this.entityTransaction.begin();
	}
	
	@After
	public void clean(){
		
		//Finalizamos a transação do teste
		this.entityTransaction.commit();
		
		//Delimitamos uma transação para apagar os dados das tabelas envolvidas no teste
		this.entityTransaction.begin();
		//Apagar primeiro as campanhas, pois são donas do relacionamento
		this.votoDao.apagarTodos();		
		this.usuarioDao.apagarTodos();
		
		this.entityTransaction.commit();
		
		this.entityManager.close();
	}	
	
	@Test
	public void salvarNovoVoto(){		
		Voto salvo = votoDao.salvar(votosUsuario_1.get(0));
		assertNotNull(salvo.getId());
	}
	
	@Test
	public void salvarMaisDeUmVoto(){		
		List<Voto> salvos = Lists.newArrayList(votoDao.salvarVotos(votosUsuario_1));		
		
		if(salvos.isEmpty()) fail();		
		for(Voto salvo : salvos)
			assertNotNull(salvo.getId());
	}
	
	@Test
	public void listarTodos(){
		votoDao.salvarVotos(votosGeral);
		
		CollectionUtils.isEqualCollection(votosGeral, votoDao.listarTodos());
	}
	
	@Test
	public void listarDadosRankingGeral(){
		
		Map<Restaurante, Map<Integer, Integer>> respostaEsperada = ImmutableMap.<Restaurante, Map<Integer, Integer>>of(
				Restaurante.OUTBACK, ImmutableMap.of(3, 0),
				Restaurante.MCDONALDS, ImmutableMap.of(3, 1),
				Restaurante.SUBWAY, ImmutableMap.of(2, 1),
				Restaurante.WENDYS, ImmutableMap.of(2, 2),
				Restaurante.GIRAFFAS, ImmutableMap.of(2, 2)
		);
		
		votoDao.salvarVotos(votosGeral);
		
		Map<Restaurante, Map<Integer, Integer>> respostaObtida = votoDao.obterDadosRankingGeral();
		
		assertEquals(respostaEsperada, respostaObtida);
	}
	
	@Test
	public void listarDadosRankingUsuario(){
		Map<Restaurante, Map<Integer, Integer>> respostaEsperada = ImmutableMap.<Restaurante, Map<Integer, Integer>>of(
				Restaurante.OUTBACK, ImmutableMap.of(2, 0),
				Restaurante.SUBWAY, ImmutableMap.of(1, 0),
				Restaurante.GIRAFFAS, ImmutableMap.of(2, 1),
				Restaurante.MCDONALDS, ImmutableMap.of(1, 1),
				Restaurante.WENDYS, ImmutableMap.of(0, 1)
		);
		
		votoDao.salvarVotos(votosGeral);
		
		Map<Restaurante, Map<Integer, Integer>> respostaObtida = votoDao.obterDadosRankingUsuario(usuario_1);
		
		assertEquals(respostaEsperada, respostaObtida);
	}
}