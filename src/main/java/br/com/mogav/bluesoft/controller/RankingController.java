package br.com.mogav.bluesoft.controller;

import java.util.Collection;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.mogav.bluesoft.model.ItemRankingVotos;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.persistencia.VotacaoService;

@Controller
@Path("/ranking")
public class RankingController {
	
	private final Result result;
	private final VotacaoService service;
	
	 /**
     * @deprecated CDI eyes only
     */
	RankingController(){
    	this(null, null);
    }

	@Inject
	public RankingController(Result result, VotacaoService service) {
		this.result = result;
		this.service = service;
	}

	@Path({"", "/"})
	public void index(Usuario usuario) {
		//Disponibiliza os rankings para a view
		Collection<ItemRankingVotos> rankingUsuario = this.service.listarRankingUsuario(usuario);
		Collection<ItemRankingVotos> rankingGeral = this.service.listarRankingGeral();

		this.result.include("rankingUsuario", rankingUsuario);
		this.result.include("rankingGeral", rankingGeral);
	}
}