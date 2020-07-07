package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private SerieADAO dao;
	private SimpleWeightedGraph<String, DefaultWeightedEdge> grafo;
	private List<String> vertici;
	private List<Arco> archi;
	private List<Vicino> connessi;
	private Simulator sim;
	private List<String> squadre;
	
	
	public Model() {
		dao = new SerieADAO();
		
	}
	
	
	public void creaGrafo() {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.vertici = new ArrayList<>(this.dao.listTeams());
		
		Graphs.addAllVertices(this.grafo, this.vertici);
		
		this.archi = new ArrayList<>(this.dao.listArchi());
		
		for(Arco a: this.archi) {
			if(this.grafo.containsEdge(a.getTeam1(), a.getTeam2())) {
				this.grafo.setEdgeWeight(this.grafo.getEdge(a.getTeam1(), a.getTeam2()), this.grafo.getEdgeWeight(this.grafo.getEdge(a.getTeam1(), a.getTeam2()))+a.getPeso());
			}else{
				Graphs.addEdge(this.grafo, a.getTeam1(), a.getTeam2(), a.getPeso());
			}
		}
		
		
	}
	
	
	public int getNumeroVertici() {
		return this.grafo.vertexSet().size();
	}
		
		
	public int getNumeroArchi() {
		return this.grafo.edgeSet().size();
	}


	public List<String> getVertici() {
		return vertici;
	}
	
	public void trovaConnessione(String squadra) {
		
		List<String> vicini = new ArrayList<>(Graphs.neighborListOf(this.grafo, squadra));
		this.connessi = new ArrayList<>();
		
		for(String s: vicini) {
			Vicino v = new Vicino(s,this.grafo.getEdgeWeight(this.grafo.getEdge(squadra, s)));
			this.connessi.add(v);
		}
		
		
		Collections.sort(this.connessi);
		
	}


	public List<Vicino> getConnessi() {
		return connessi;
	}
	
	public List<Integer> getSeason(){
		return this.dao.getSeasons();
	}
	
	
	public void simula(Integer anno) {
		List<Match> matches = new ArrayList<>(this.dao.getMatch(anno));
		
		this.sim = new Simulator();
		
		this.squadre = new ArrayList<>(this.dao.listSquadre(anno));
		this.sim.run(this.squadre, matches);
		
	}
	
	
	public Map<String, Integer> getTifosi() {
		return this.sim.getTifosi();
	}

	public Map<String, Integer> getClassifica() {
		return this.sim.getClassifica();
	}


	public List<String> getSquadre() {
		return squadre;
	}
	


}
