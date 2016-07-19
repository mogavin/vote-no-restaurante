package br.com.mogav.bluesoft.controllers;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;

@Controller
public class VotacaoController {

    /**
     * @deprecated CDI eyes only
     */
    VotacaoController(){}
    
    @Path({"", "/"})
    public void index(){};
}