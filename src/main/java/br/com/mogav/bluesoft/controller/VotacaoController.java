package br.com.mogav.bluesoft.controller;

import java.util.Map;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.service.VotacaoService;

@Controller
public class VotacaoController {
	
	private final Result result;
	private final VotacaoService service;

    /**
     * @deprecated CDI eyes only
     */
    VotacaoController(){
    	this(null, null);
    }
    
    VotacaoController(Result result, VotacaoService service){
    	this.result = result;
    	this.service = service;
    }
    
    @Path({"", "/"})
    public void index(){}

    
	public void votar(Usuario usuario, Map<Restaurante, Boolean> votosPorRestaurante) {		
		this.service.registrarVoto(usuario, votosPorRestaurante);
		this.result.redirectTo(RankingController.class).index();
	};
}