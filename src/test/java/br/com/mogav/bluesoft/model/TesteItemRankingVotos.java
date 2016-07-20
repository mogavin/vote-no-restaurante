package br.com.mogav.bluesoft.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class TesteItemRankingVotos {

	private final Restaurante RESTAURANTE = Restaurante.GIRAFFAS;
	private static final int QTD_VOTOS_POSITIVOS = 5;
	private static final int QTD_VOTOS_NEGATIVOS = 2;
	
	private ItemRankingVotos item;
	
	@Before
	public void setup(){
		this.item = new ItemRankingVotos(RESTAURANTE, QTD_VOTOS_POSITIVOS, QTD_VOTOS_NEGATIVOS);
	}
	
	
	@Test
	public void obterNomeRestaurante(){
		assertEquals(RESTAURANTE.getDescricao(), item.getRestaurante());
	}
	
	@Test
	public void obterQuantidadeDeVotosPositivos(){
		assertEquals(QTD_VOTOS_POSITIVOS, item.getQtdVotosPositivos());
	}
	
	@Test
	public void obterQuantidadeDeVotosNegativos(){
		assertEquals(QTD_VOTOS_NEGATIVOS, item.getQtdVotosNegativos());
	}
	
	@Test
	public void verificaEqualsHashcode(){
		EqualsVerifier.forClass(ItemRankingVotos.class).verify();
	}
}