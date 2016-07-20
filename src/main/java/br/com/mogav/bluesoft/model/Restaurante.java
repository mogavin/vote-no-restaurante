package br.com.mogav.bluesoft.model;

public enum Restaurante {
	MCDONALDS("McDonald's"),
	SUBWAY("Subway"),
	WENDYS("Wendy's"),
	OUTBACK("Outback"),
	GIRAFFAS("Giraffas");
	
	private final String descricao;
	
	private Restaurante(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
}
