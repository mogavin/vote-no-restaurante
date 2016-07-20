package br.com.mogav.bluesoft.controller;

import java.util.Collection;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.mogav.bluesoft.dao.VotacaoService;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;

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

    
	public void votar(Usuario usuario, Collection<Voto> votos) {		
		try{
			this.service.registrarVoto(usuario, votos);
		}catch(RuntimeException ex){
			this.validator.add(new SimpleMessage("erro", ex.getMessage()));
		}
		
		this.result.redirectTo(RankingController.class).index(usuario);
		this.validator.onErrorRedirectTo(RankingController.class).index(usuario);
	};
}