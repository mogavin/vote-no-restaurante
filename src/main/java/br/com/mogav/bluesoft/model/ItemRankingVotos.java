package br.com.mogav.bluesoft.model;

public class ItemRankingVotos implements Comparable<ItemRankingVotos>{
	
	private final Restaurante restaurante;
	private final Integer qtdVotosPositivos;
	private final Integer qtdVotosNegativos;

	public ItemRankingVotos(Restaurante restaurante, Integer qtdVotosPositivos, Integer qtdVotosNegativos) {
		this.restaurante = restaurante;
		this.qtdVotosPositivos = qtdVotosPositivos;
		this.qtdVotosNegativos = qtdVotosNegativos;
	}

	
	public String getRestaurante() {
		return this.restaurante.getDescricao();
	}

	public Integer getQtdVotosPositivos() {
		return this.qtdVotosPositivos;
	}
	
	public Integer getQtdVotosNegativos() {
		return this.qtdVotosNegativos;
	}
	
	public Integer getPosicao() {
		return this.qtdVotosPositivos - this.qtdVotosNegativos;
	}

	
	@Override
	public int compareTo(ItemRankingVotos that) {
		return that.getPosicao().compareTo(this.getPosicao());
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((qtdVotosNegativos == null) ? 0 : qtdVotosNegativos
						.hashCode());
		result = prime
				* result
				+ ((qtdVotosPositivos == null) ? 0 : qtdVotosPositivos
						.hashCode());
		result = prime * result
				+ ((restaurante == null) ? 0 : restaurante.hashCode());
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
		if (qtdVotosNegativos == null) {
			if (other.qtdVotosNegativos != null)
				return false;
		} else if (!qtdVotosNegativos.equals(other.qtdVotosNegativos))
			return false;
		if (qtdVotosPositivos == null) {
			if (other.qtdVotosPositivos != null)
				return false;
		} else if (!qtdVotosPositivos.equals(other.qtdVotosPositivos))
			return false;
		if (restaurante != other.restaurante)
			return false;
		return true;
	}
}