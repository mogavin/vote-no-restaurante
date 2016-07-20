package br.com.mogav.bluesoft.model;

public class ItemRankingVotos {
	
	private final Restaurante restaurante;
	private final int qtdVotosPositivos;
	private final int qtdVotosNegativos;

	public ItemRankingVotos(Restaurante restaurante, int qtdVotosPositivos, int qtdVotosNegativos) {
		this.restaurante = restaurante;
		this.qtdVotosPositivos = qtdVotosPositivos;
		this.qtdVotosNegativos = qtdVotosNegativos;
	}

	
	public String getRestaurante() {
		return this.restaurante.getDescricao();
	}

	public int getQtdVotosPositivos() {
		return this.qtdVotosPositivos;
	}
	
	public int getQtdVotosNegativos() {
		return this.qtdVotosNegativos;
	}


	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + qtdVotosNegativos;
		result = prime * result + qtdVotosPositivos;
		result = prime * result + ((restaurante == null) ? 0 : restaurante.hashCode());
		return result;
	}


	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ItemRankingVotos))
			return false;
		ItemRankingVotos other = (ItemRankingVotos) obj;
		if (qtdVotosNegativos != other.qtdVotosNegativos)
			return false;
		if (qtdVotosPositivos != other.qtdVotosPositivos)
			return false;
		if (restaurante != other.restaurante)
			return false;
		return true;
	}	
}
