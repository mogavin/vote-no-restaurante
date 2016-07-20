package br.com.mogav.bluesoft.controller;

import java.util.Collection;

import br.com.caelum.vraptor.Result;
import br.com.mogav.bluesoft.dao.VotoDao;
import br.com.mogav.bluesoft.model.Voto;

public class RankingController {
	
	private final Result result;
	private final VotoDao votoDao;

	public RankingController(Result result, VotoDao votoDao) {
		this.result = result;
		this.votoDao = votoDao;
	}

	public void index() {		
		Collection<Voto> rankingVotos = this.votoDao.listarRanking();
		this.result.include("rankingVotos", rankingVotos);
	}
}
