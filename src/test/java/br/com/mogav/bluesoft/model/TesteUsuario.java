package br.com.mogav.bluesoft.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TesteUsuario {

	private static final String NOME = "NOME";
	private static final String EMAIL = "EMAIL";

	private Usuario usuario;
	
	@Before
	public void setup(){
		this.usuario = new Usuario(NOME, EMAIL);
	}
	
	
	@Test
	public void obterNome(){
		assertEquals(NOME, usuario.getNome());
	}
	
	@Test
	public void obterNomeEmail(){
		assertEquals(EMAIL, usuario.getEmail());
	}
}