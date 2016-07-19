package br.com.mogav.bluesoft.controller;

import java.util.Map;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.service.VotacaoService;

@Controller
public class VotacaoController {
	
	private final Result result;
	private final Validator validator;
	private final VotacaoService service;

    /**
     * @deprecated CDI eyes only
     */
    VotacaoController(){
    	this(null, null, null);
    }
    
    VotacaoController(Result result, Validator validator, VotacaoService service){
    	this.result = result;
    	this.validator = validator;
    	this.service = service;
    }
    
    @Path({"", "/"})
    public void index(){}

    
	public void votar(Usuario usuario, Map<Restaurante, Boolean> votosPorRestaurante) {		
		try{
			this.service.registrarVoto(usuario, votosPorRestaurante);
		}catch(RuntimeException ex){
			this.validator.add(new SimpleMessage("erro", ex.getMessage()));
		}
		
		this.result.redirectTo(RankingController.class).index();
		this.validator.onErrorRedirectTo(RankingController.class).index();
	};
}