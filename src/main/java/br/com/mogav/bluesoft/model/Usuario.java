package br.com.mogav.bluesoft.model;

public class Usuario {

	private final String nome;
	private final String email;
	
	public Usuario(String nome, String email) {
		this.nome = nome;
		this.email = email;
	}
	

	public String getNome() {
		return this.nome;
	}

	public String getEmail() {
		return this.email;
	}
}
