package br.com.mogav.bluesoft.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class TesteVoto {

	private final VotoType TIPO_VOTO = VotoType.NEGATIVO;
	private final Restaurante RESTAURANTE = Restaurante.MCDONALDS;
	private Voto voto;
	
	@Before
	public void setup(){
		this.voto = new Voto(TIPO_VOTO, RESTAURANTE);
	}
	
	
	@Test
	public void obterTipoDoVoto(){
		assertEquals(TIPO_VOTO, voto.getTipoVoto());
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