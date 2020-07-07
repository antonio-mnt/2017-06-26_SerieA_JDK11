package it.polito.tdp.seriea.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.seriea.model.Event.EventType;

public class Simulator {
	
	
	
	private PriorityQueue<Event> queue = new PriorityQueue<>();
	
	
	private Map<String,Integer> tifosi;
	private Map<String, Integer> classifica;
	
	
	public Map<String, Integer> getTifosi() {
		return tifosi;
	}

	public Map<String, Integer> getClassifica() {
		return classifica;
	}

	
	public void run(List<String> squadre, List<Match> matches) {
		
		this.classifica = new HashMap<>();
		this.tifosi = new HashMap<>();
		
		for(String s: squadre) {
			this.classifica.put(s, 0);
			this.tifosi.put(s, 1000);
		}
		
		this.queue.clear();
		
		for(Match m: matches) {
			Event ev = new Event(EventType.INIZIO_PARTITA,m.getDate(),m);
			this.queue.add(ev);
		}
		
		while(!this.queue.isEmpty()) {   
		    Event e = this.queue.poll();
		    System.out.println(e);
		    processEvent(e);
		}
		
		
		
		
	}

	private void processEvent(Event e) {
		
		String casa = e.getMatch().getHomeTeam();
		String trasferta = e.getMatch().getAwayTeam();
		int goalCasa = e.getMatch().getFthg();
		int goalTrasferta = e.getMatch().getFtag();
		
		double tifosiA = this.tifosi.get(casa);
		double tifosiB = this.tifosi.get(trasferta);
		
		switch(e.getType()) {
		
		case INIZIO_PARTITA:
			
			
			double prob = 0.0;
			double c = Math.random();
			
			if(tifosiA!=tifosiB) {
				if(tifosiA<tifosiB) {
					prob = (1-(tifosiA/tifosiB));
					if(c<prob) {
						goalCasa = goalCasa-1;
						if(goalCasa<0) {
							goalCasa = 0;
						}
					}
				}else {
					prob = (1-(tifosiB/tifosiA));
					
					if(c<prob) {
						goalTrasferta = goalTrasferta-1;
						if(goalTrasferta<0) {
							goalTrasferta = 0;
						}
					}
				}
			}
			
			e.getMatch().setFthg(goalCasa);
			e.getMatch().setFtag(goalTrasferta);
			
			Event ev = new Event(EventType.FINE_PARTITA,e.getDate(),e.getMatch());
			this.queue.add(ev);
			
			break;
			
		case FINE_PARTITA:
			
			double differenza = 0;
			
			if(goalCasa>goalTrasferta) {
				this.classifica.put(casa, this.classifica.get(casa)+3);
				differenza  = ((double) (goalCasa-goalTrasferta))/10;
				int nuoviTifosi = (int) (differenza*tifosiB);
				
				
				tifosiA = tifosiA + nuoviTifosi;
				tifosiB = tifosiB - nuoviTifosi;
				
			}else if(goalCasa<goalTrasferta) {
				this.classifica.put(trasferta, this.classifica.get(trasferta)+3);
				differenza = ((double) (goalTrasferta-goalCasa))/10;
				int nuovi = (int) (differenza*tifosiA);
				
				tifosiB = tifosiB + nuovi;
				tifosiA = tifosiA - nuovi;
			}else {
				this.classifica.put(casa, this.classifica.get(casa)+1);
				this.classifica.put(trasferta, this.classifica.get(trasferta)+1);
			}
			
			this.tifosi.put(casa, (int) tifosiA);
			this.tifosi.put(trasferta, (int) tifosiB);
			
			break;
		
		}
		
	}
	
	

}
