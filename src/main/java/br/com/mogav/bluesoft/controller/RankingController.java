package br.com.mogav.bluesoft.controller;

import java.util.Collection;

import br.com.caelum.vraptor.Result;
import br.com.mogav.bluesoft.model.ItemRankingVotos;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.persistencia.VotacaoService;

public class RankingController {
	
	private final Result result;
	private final VotacaoService service;

	public RankingController(Result result, VotacaoService service) {
		this.result = result;
		this.service = service;
	}

	public void index(Usuario usuario) {
		Collection<ItemRankingVotos> rankingUsuario = this.service.listarRankingUsuario(usuario);
		Collection<ItemRankingVotos> rankingGeral = this.service.listarRankingGeral();
		this.result.include("rankingUsuario", rankingUsuario);
		this.result.include("rankingGeral", rankingGeral);
	}
}
