package br.com.mogav.bluesoft.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class TesteVoto {

	private static final Usuario USUARIO = new Usuario("Joao", "joao@email.com");
	private final boolean TIPO_VOTO = false;
	private final Restaurante RESTAURANTE = Restaurante.MCDONALDS;
	private Voto voto;
	
	@Before
	public void setup(){
		this.voto = new Voto(USUARIO, TIPO_VOTO, RESTAURANTE);
	}
	
	
	@Test
	public void obterDonoDoVoto(){
		assertEquals(USUARIO, voto.getUsuario());
	}
	
	@Test
	public void obterTipoDoVoto(){
		assertEquals(TIPO_VOTO, voto.isPositivo());
	}
	
	@Test
	public void obterRestaurante(){
		assertEquals(RESTAURANTE, voto.getRestaurante());
	}
	
	@Test
	public void verificaEqualsHashcode(){
		EqualsVerifier.forClass(Voto.class).verify();
	}
}