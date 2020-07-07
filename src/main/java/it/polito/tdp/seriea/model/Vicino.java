package it.polito.tdp.seriea.model;

public class Vicino implements Comparable<Vicino>{
	
	private String team;
	private Double peso;
	public Vicino(String team, Double peso) {
		super();
		this.team = team;
		this.peso = peso;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vicino other = (Vicino) obj;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Vicino [team=" + team + ", peso=" + peso + "]";
	}
	@Override
	public int compareTo(Vicino o) {
		return -this.peso.compareTo(o.peso);
	}
	

}
