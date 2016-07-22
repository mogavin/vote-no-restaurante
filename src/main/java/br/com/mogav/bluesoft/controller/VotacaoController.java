package br.com.mogav.bluesoft.controller;

import java.util.Collection;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.mogav.bluesoft.model.Restaurante;
import br.com.mogav.bluesoft.model.Usuario;
import br.com.mogav.bluesoft.model.Voto;
import br.com.mogav.bluesoft.persistencia.VotacaoService;

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
    
    @Inject
    VotacaoController(Result result, Validator validator, VotacaoService service){
    	this.result = result;
    	this.validator = validator;
    	this.service = service;
    }
    
    @Path({"", "/"})
    public void index(){
		//Disponibiliza os restaurantes para a view
		this.result.include("restaurantes", Restaurante.values());    	
    }

    @Post
	public void votar(Usuario usuario, Collection<Voto> votos) {    	
    	
    	try{
			this.service.registrarVotos(usuario, votos);
		}catch(RuntimeException ex){
			this.validator.add(new SimpleMessage("erro", ex.getMessage()));
		}
		
		this.validator.onErrorRedirectTo(RankingController.class).index(usuario);
		this.result.redirectTo(RankingController.class).index(usuario);
	};
}