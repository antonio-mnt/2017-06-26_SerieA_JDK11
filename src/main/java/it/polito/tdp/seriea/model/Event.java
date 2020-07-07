package it.polito.tdp.seriea.model;

import java.time.LocalDate;

public class Event implements Comparable<Event>{
	
	
	public enum EventType{
		INIZIO_PARTITA,
		FINE_PARTITA
	}
	
	private EventType type;
	private LocalDate date;
	private Match match;
	
	public Event(EventType type, LocalDate date, Match match) {
		super();
		this.type = type;
		this.date = date;
		this.match = match;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((match == null) ? 0 : match.hashCode());
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
		Event other = (Event) obj;
		if (match == null) {
			if (other.match != null)
				return false;
		} else if (!match.equals(other.match))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [type=" + type + ", date=" + date + ", match=" + match + "]";
	}

	@Override
	public int compareTo(Event o) {
		return this.getDate().compareTo(o.getDate());
	}
	
	

}
